package com.rdi.offweb3.config.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;



@Component
public class JwtService {

    public String generateAccessToken(String username) {
        return JWT.create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(864000L, ChronoUnit.SECONDS))
                .withIssuer("OffWeb3 inc.")
                .withSubject(username)
                .sign(Algorithm.HMAC256("wealth"));
    }

    public String extractUsernameFrom(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("wealth"))
                .withIssuer("OffWeb3 inc.")
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

}
