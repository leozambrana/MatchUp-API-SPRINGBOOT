package com.leozambrana.MatchUp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ResponseEntity<String> welcomeToRoot(Principal principal) {
        // Se a requisição chega até esta linha, significa que o filtro JWT deixou passar!
        String userEmail = principal != null ? principal.getName() : "Anônimo";
        
        return ResponseEntity.ok("Acesso Autorizado! O Token funciona e pertence ao usuário: " + userEmail);
    }
}
