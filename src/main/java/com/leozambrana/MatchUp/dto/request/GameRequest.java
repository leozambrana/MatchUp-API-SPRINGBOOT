package com.leozambrana.MatchUp.dto.request;

import com.leozambrana.MatchUp.enums.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record GameRequest(
        @NotBlank(message = "O título do jogo não pode estar vazio") String title,
        String description,
        String location,
        @NotNull(message = "A data e hora do jogo são obrigatórias") LocalDateTime dateTime,
        CourtType courtType,
        MatchFormat matchFormat,
        MatchGender gender,
        @Min(value = 1, message = "O número de vagas deve ser pelo menos 1") Integer slots,
        MatchDuration duration,
        RecurrenceType recurrence
) {
}
