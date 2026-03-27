package com.leozambrana.MatchUp.service;

import com.leozambrana.MatchUp.dto.request.GameRequest;
import com.leozambrana.MatchUp.dto.response.GameResponse;
import com.leozambrana.MatchUp.entity.Game;
import com.leozambrana.MatchUp.entity.User;
import com.leozambrana.MatchUp.mapper.GameMapper;
import com.leozambrana.MatchUp.repository.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public GameService(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    public GameResponse createGame(GameRequest request, User creator) {
        Game game = new Game();
        game.setTitle(request.title());
        game.setDescription(request.description());
        game.setLocation(request.location());
        game.setDateTime(request.dateTime());
        game.setCreatedBy(creator);

        Game savedGame = gameRepository.save(game);
        return gameMapper.toResponse(savedGame);
    }

    public List<GameResponse> getAllGames() {
        return gameRepository.findAll().stream()
                .map(gameMapper::toResponse)
                .collect(Collectors.toList());
    }

    public GameResponse getGameById(UUID id) {
        Game game = findGameOrThrow(id);
        return gameMapper.toResponse(game);
    }

    public GameResponse updateGame(UUID id, GameRequest request, User currentUser) {
        Game game = findGameOrThrow(id);
        
        validateOwner(game, currentUser, "editar");

        game.setTitle(request.title());
        game.setDescription(request.description());
        game.setLocation(request.location());
        game.setDateTime(request.dateTime());

        Game updatedGame = gameRepository.save(game);
        return gameMapper.toResponse(updatedGame);
    }

    public void deleteGame(UUID id, User currentUser) {
        Game game = findGameOrThrow(id);
        validateOwner(game, currentUser, "cancelar e deletar");
        gameRepository.delete(game);
    }

    private Game findGameOrThrow(UUID id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Partida não encontrada"));
    }

    private void validateOwner(Game game, User currentUser, String acao) {
        if (!game.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para " + acao + " esta partida");
        }
    }
}
