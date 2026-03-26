package com.leozambrana.MatchUp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

        @NotEmpty(message = "Nome é obrigatório")
        private String name;

        @NotEmpty(message = "Email é obrigatório")
        @Email(message = "Email deve ser válido")
        private String email;

        @NotEmpty(message = "Senha é obrigatória")
        @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
        private String password;
}

