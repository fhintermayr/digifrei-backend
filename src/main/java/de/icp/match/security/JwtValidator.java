package de.icp.match.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtValidator {

    private final JwtExtractor jwtExtractor;
    @Value("${jwt.secret}")
    private String jwtSecret;


    public JwtValidator(JwtExtractor jwtExtractor) {
        this.jwtExtractor = jwtExtractor;
    }


    public boolean isTokenValid(String token) {
        return canTokenBeDecoded(token) && isTokenNotExpired(token);
    }

    private boolean canTokenBeDecoded(String token) {
        try {
            // FIXME: Deprecated
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
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
