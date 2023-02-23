package de.icp.match.security.service;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtGenerator {

    @Value("${jwt.validityInHours}")
    private int tokenValidityInHours;
    private final JwtSecretProvider jwtSecretProvider;

    public JwtGenerator(JwtSecretProvider jwtSecretProvider) {
        this.jwtSecretProvider = jwtSecretProvider;
    }

    public String generateTokenForUser(String username) {

        Instant creationTimestamp = Instant.now();
        Instant expirationTimestamp = creationTimestamp.plus(tokenValidityInHours, ChronoUnit.HOURS);

        SecretKey signingKey = jwtSecretProvider.getSigningKey();

        return Jwts.builder()
                .setSubject(username)
                .setIssuer("Stiftung ICP München")
                .setIssuedAt(Date.from(creationTimestamp))
                .setExpiration(Date.from(expirationTimestamp))
                .signWith(signingKey)
                .compact();
    }

}
