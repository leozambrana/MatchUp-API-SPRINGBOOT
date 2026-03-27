package com.leozambrana.MatchUp.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameResponse(
        UUID id,
        String title,
        String description,
        String location,
        LocalDateTime dateTime,
        String creatorEmail
) {
}
