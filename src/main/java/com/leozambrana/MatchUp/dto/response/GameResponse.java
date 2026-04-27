package com.leozambrana.MatchUp.dto.response;

import com.leozambrana.MatchUp.entity.*;
import com.leozambrana.MatchUp.enums.*;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameResponse(
        UUID id,
        String title,
        String description,
        String location,
        LocalDateTime dateTime,
        CourtType courtType,
        MatchFormat matchFormat,
        MatchGender gender,
        Integer slots,
        MatchDuration duration,
        RecurrenceType recurrence,
        String inviteCode,
        String creatorEmail
) {}