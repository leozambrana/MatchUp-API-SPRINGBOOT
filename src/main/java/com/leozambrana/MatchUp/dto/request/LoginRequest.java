package com.leozambrana.MatchUp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

        @NotEmpty(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        private String email;

        @NotEmpty(message = "Senha é obrigatória")
        private String password;
}