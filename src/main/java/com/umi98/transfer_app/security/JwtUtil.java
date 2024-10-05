package com.umi98.transfer_app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.umi98.transfer_app.entity.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {
    private final String jwtSecret = "secret";
    private final String issuer = "transfer-app";

    public String generateToken(AppUser appUser) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(appUser.getId())
                    .withExpiresAt(Instant.now().plusSeconds(3600))
                    .withIssuedAt(Instant.now())
                    .withClaim("role", appUser.getRole().getRole().name())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            log.error("Error when creating token: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Boolean verifyJwtToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuer().equals(issuer);
        } catch (JWTVerificationException e) {
            log.error("Invalid JWT {}", e.getMessage());
            return false;
        }
    }

    public Map<String, String> getUserInfoByToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("userId", decodedJWT.getSubject());
            userInfo.put("role", decodedJWT.getClaim("role").asString());
            return userInfo;
        } catch (JWTVerificationException e) {
            log.error("Invalid JWT {}", e.getMessage());
            return null;
        }
    }
}
