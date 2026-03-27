package com.leozambrana.MatchUp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record GameRequest(
        @NotBlank(message = "O título do jogo não pode estar vazio") String title,
        String description,
        String location,
        @NotNull(message = "A data e hora do jogo são obrigatórias") LocalDateTime dateTime
) {
}
