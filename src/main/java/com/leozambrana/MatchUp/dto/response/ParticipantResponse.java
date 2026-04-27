package com.leozambrana.MatchUp.dto.response;
import com.leozambrana.MatchUp.enums.ParticipantStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ParticipantResponse(
        UUID id,
        UUID gameId,
        String userEmail,
        com.leozambrana.MatchUp.enums.ParticipantStatus status,
        LocalDateTime joinedAt
) {}