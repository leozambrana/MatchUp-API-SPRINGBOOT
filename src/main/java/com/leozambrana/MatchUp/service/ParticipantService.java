package com.leozambrana.MatchUp.service;

import com.leozambrana.MatchUp.dto.request.ParticipantStatusRequest;
import com.leozambrana.MatchUp.dto.response.ParticipantResponse;
import com.leozambrana.MatchUp.entity.*;
import com.leozambrana.MatchUp.enums.ParticipantStatus;
import com.leozambrana.MatchUp.mapper.ParticipantMapper;
import com.leozambrana.MatchUp.repository.GameRepository;
import com.leozambrana.MatchUp.repository.ParticipantRepository;
import com.leozambrana.MatchUp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ParticipantMapper participantMapper;

    public ParticipantService(ParticipantRepository participantRepository,
                              GameRepository gameRepository,
                              UserRepository userRepository,
                              ParticipantMapper participantMapper) {
        this.participantRepository = participantRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.participantMapper = participantMapper;
    }

    public ParticipantResponse joinGame(UUID gameId, User currentUser) {
        Game game = findGameOrThrow(gameId);

        if (participantRepository.existsByGameIdAndUserId(gameId, currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Você já está inscrito nesta partida");
        }

        Participant participant = Participant.builder()
                .game(game)
                .user(currentUser)
                .status(ParticipantStatus.PENDING)
                .joinedAt(LocalDateTime.now())
                .build();

        return participantMapper.toResponse(participantRepository.save(participant));
    }

    public ParticipantResponse addParticipant(UUID gameId, UUID userId, User currentUser) {
        Game game = findGameOrThrow(gameId);
        validateCreator(game, currentUser);

        User userToAdd = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (participantRepository.existsByGameIdAndUserId(gameId, userId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuário já está inscrito nesta partida");
        }

        Participant participant = Participant.builder()
                .game(game)
                .user(userToAdd)
                .status(ParticipantStatus.CONFIRMED)
                .joinedAt(LocalDateTime.now())
                .build();

        return participantMapper.toResponse(participantRepository.save(participant));
    }

    public ParticipantResponse updateStatus(UUID gameId, UUID participantId,
                                            ParticipantStatusRequest request, User currentUser) {
        Game game = findGameOrThrow(gameId);
        validateCreator(game, currentUser);

        Participant participant = findParticipantOrThrow(participantId);
        participant.setStatus(request.status());

        return participantMapper.toResponse(participantRepository.save(participant));
    }

    public List<ParticipantResponse> listParticipants(UUID gameId) {
        findGameOrThrow(gameId);
        return participantRepository.findByGameId(gameId).stream()
                .map(participantMapper::toResponse)
                .collect(Collectors.toList());
    }

    public void removeParticipant(UUID gameId, UUID participantId, User currentUser) {
        Game game = findGameOrThrow(gameId);
        Participant participant = findParticipantOrThrow(participantId);

        boolean isCreator = game.getCreatedBy().getId().equals(currentUser.getId());
        boolean isSelf = participant.getUser().getId().equals(currentUser.getId());

        if (!isCreator && !isSelf) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para remover este participante");
        }

        participantRepository.delete(participant);
    }

    private Game findGameOrThrow(UUID gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada"));
    }

    private Participant findParticipantOrThrow(UUID participantId) {
        return participantRepository.findById(participantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante não encontrado"));
    }

    private void validateCreator(Game game, User currentUser) {
        if (!game.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas o criador pode realizar esta ação");
        }
    }
}