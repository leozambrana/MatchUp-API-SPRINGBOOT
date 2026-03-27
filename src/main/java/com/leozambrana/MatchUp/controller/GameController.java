package com.leozambrana.MatchUp.controller;

import com.leozambrana.MatchUp.dto.request.GameRequest;
import com.leozambrana.MatchUp.dto.response.GameResponse;
import com.leozambrana.MatchUp.entity.User;
import com.leozambrana.MatchUp.repository.UserRepository;
import com.leozambrana.MatchUp.service.GameService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;
    private final UserRepository userRepository;

    public GameController(GameService gameService, UserRepository userRepository) {
        this.gameService = gameService;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUser(Principal principal) {
        var authToken = (org.springframework.security.authentication.UsernamePasswordAuthenticationToken) principal;
        com.leozambrana.MatchUp.config.JWTUserData userData = (com.leozambrana.MatchUp.config.JWTUserData) authToken.getPrincipal();
        return (User) userRepository.findUserByEmail(userData.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário requisitante não encontrado!"));
    }

    @PostMapping
    public ResponseEntity<GameResponse> createGame(@Valid @RequestBody GameRequest request, Principal principal) {
        User creator = getAuthenticatedUser(principal);
        GameResponse response = gameService.createGame(request, creator);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GameResponse>> getAllGames() {
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> getGameById(@PathVariable UUID id) {
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameResponse> updateGame(@PathVariable UUID id, @Valid @RequestBody GameRequest request, Principal principal) {
        User currentUser = getAuthenticatedUser(principal);
        return ResponseEntity.ok(gameService.updateGame(id, request, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable UUID id, Principal principal) {
        User currentUser = getAuthenticatedUser(principal);
        gameService.deleteGame(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
