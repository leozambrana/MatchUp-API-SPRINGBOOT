package com.leozambrana.MatchUp.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leozambrana.MatchUp.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

@Component
public class TokenConfig {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim("userId", String.valueOf(user.getId()))
                .withSubject(user.getEmail())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 86400000)) // Token expires in 24 hours
                .withIssuedAt(new java.util.Date())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(JWTUserData.builder().
                    userId(decodedJWT.getClaim("userId").asString()).
                    email(decodedJWT.getSubject()).build()
            );
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
}
