package com.leozambrana.MatchUp.controller;

import com.leozambrana.MatchUp.config.TokenConfig;
import com.leozambrana.MatchUp.dto.request.LoginRequest;
import com.leozambrana.MatchUp.dto.request.RegisterRequest;
import com.leozambrana.MatchUp.dto.response.LoginResponse;
import com.leozambrana.MatchUp.dto.response.RegisterUserResponse;
import com.leozambrana.MatchUp.entity.User;
import com.leozambrana.MatchUp.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenConfig tokenConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        User user = (User) authentication.getPrincipal();
        assert user != null;
        String token = tokenConfig.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterRequest request) {
       User user = new User();
       user.setName(request.getName());
       user.setEmail(request.getEmail());
       user.setPassword(passwordEncoder.encode(request.getPassword()));

         userRepository.save(user);

         return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterUserResponse(user.getName(), user.getEmail()));
    }
}

