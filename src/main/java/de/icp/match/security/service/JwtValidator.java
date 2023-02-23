package de.icp.match.security.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtValidator {

    private final JwtExtractor jwtExtractor;
    private final JwtSecretProvider jwtSecretProvider;

    public JwtValidator(JwtExtractor jwtExtractor, JwtSecretProvider jwtSecretProvider) {
        this.jwtExtractor = jwtExtractor;
        this.jwtSecretProvider = jwtSecretProvider;
    }


    public boolean isTokenValid(String token) {
        return canTokenBeDecoded(token) && isTokenNotExpired(token);
    }

    private boolean canTokenBeDecoded(String token) {

        SecretKey signingKey = jwtSecretProvider.getSigningKey();

        try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        }

        catch (JwtException e) {
            log.error("Invalid JWT: {}", e.getMessage());
        }

        return false;
    }

    private boolean isTokenNotExpired(String token) {

        Date expirationDate = jwtExtractor.getExpirationDate(token);
        Date currentDate = Date.from(Instant.now());

        return currentDate.before(expirationDate);
    }

}
