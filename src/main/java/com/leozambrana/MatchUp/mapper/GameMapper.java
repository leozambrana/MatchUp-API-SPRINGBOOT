package com.leozambrana.MatchUp.mapper;

import com.leozambrana.MatchUp.dto.response.GameResponse;
import com.leozambrana.MatchUp.entity.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameResponse toResponse(Game game) {
        return new GameResponse(
                game.getId(),
                game.getTitle(),
                game.getDescription(),
                game.getLocation(),
                game.getDateTime(),
                game.getCourtType(),
                game.getMatchFormat(),
                game.getGender(),
                game.getSlots(),
                game.getDuration(),
                game.getRecurrence(),
                game.getInviteCode(),
                game.getCreatedBy().getEmail()
        );
    }
}