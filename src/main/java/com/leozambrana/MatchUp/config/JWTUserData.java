package com.leozambrana.MatchUp.config;

import lombok.Builder;

@Builder
public record JWTUserData(Long userId, String email) {

}
