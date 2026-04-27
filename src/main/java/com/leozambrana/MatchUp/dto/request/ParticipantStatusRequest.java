package com.leozambrana.MatchUp.dto.request;

import com.leozambrana.MatchUp.enums.ParticipantStatus;
import jakarta.validation.constraints.NotNull;

public record ParticipantStatusRequest(
        @NotNull(message = "O status não pode ser nulo") com.leozambrana.MatchUp.enums.ParticipantStatus status
) {}