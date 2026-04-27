package com.leozambrana.MatchUp.mapper;

import com.leozambrana.MatchUp.dto.response.ParticipantResponse;
import com.leozambrana.MatchUp.entity.Participant;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {

    public ParticipantResponse toResponse(Participant participant) {
        return new ParticipantResponse(
                participant.getId(),
                participant.getGame().getId(),
                participant.getUser().getEmail(),
                participant.getStatus(),
                participant.getJoinedAt()
        );
    }
}