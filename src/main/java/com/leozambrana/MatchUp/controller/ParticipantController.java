package com.leozambrana.MatchUp.controller;

import com.leozambrana.MatchUp.dto.request.ParticipantStatusRequest;
import com.leozambrana.MatchUp.dto.response.ParticipantResponse;
import com.leozambrana.MatchUp.entity.User;
import com.leozambrana.MatchUp.repository.UserRepository;
import com.leozambrana.MatchUp.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/games/{gameId}/participants")
public class ParticipantController {

    private final ParticipantService participantService;
    private final UserRepository userRepository;

    public ParticipantController(ParticipantService participantService, UserRepository userRepository) {
        this.participantService = participantService;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUser(Principal principal) {
        var authToken = (org.springframework.security.authentication.UsernamePasswordAuthenticationToken) principal;
        com.leozambrana.MatchUp.config.JWTUserData userData = (com.leozambrana.MatchUp.config.JWTUserData) authToken.getPrincipal();
        return (User) userRepository.findUserByEmail(userData.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    // Usuário se inscreve no jogo
    @PostMapping("/join")
    public ResponseEntity<ParticipantResponse> joinGame(@PathVariable UUID gameId, Principal principal) {
        User currentUser = getAuthenticatedUser(principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(participantService.joinGame(gameId, currentUser));
    }

    // Criador adiciona alguém diretamente
    @PostMapping("/add/{userId}")
    public ResponseEntity<ParticipantResponse> addParticipant(@PathVariable UUID gameId,
                                                              @PathVariable UUID userId,
                                                              Principal principal) {
        User currentUser = getAuthenticatedUser(principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(participantService.addParticipant(gameId, userId, currentUser));
    }

    // Lista participantes
    @GetMapping
    public ResponseEntity<List<ParticipantResponse>> listParticipants(@PathVariable UUID gameId) {
        return ResponseEntity.ok(participantService.listParticipants(gameId));
    }

    // Criador atualiza status
    @PatchMapping("/{participantId}/status")
    public ResponseEntity<ParticipantResponse> updateStatus(@PathVariable UUID gameId,
                                                            @PathVariable UUID participantId,
                                                            @Valid @RequestBody ParticipantStatusRequest request,
                                                            Principal principal) {
        User currentUser = getAuthenticatedUser(principal);
        return ResponseEntity.ok(participantService.updateStatus(gameId, participantId, request, currentUser));
    }

    // Remove participante
    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> removeParticipant(@PathVariable UUID gameId,
                                                  @PathVariable UUID participantId,
                                                  Principal principal) {
        User currentUser = getAuthenticatedUser(principal);
        participantService.removeParticipant(gameId, participantId, currentUser);
        return ResponseEntity.noContent().build();
    }
}