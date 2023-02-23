package de.icp.match.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtExtractor {

    private final JwtSecretProvider jwtSecretProvider;

    public JwtExtractor(JwtSecretProvider jwtSecretProvider) {
        this.jwtSecretProvider = jwtSecretProvider;
    }

    public String getUsername(String token) {
        return getBody(token).getSubject();
    }

    public Date getExpirationDate(String token) {
        return getBody(token).getExpiration();
    }

    private Claims getBody(String token) {

        SecretKey signingKey = jwtSecretProvider.getSigningKey();

        return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody();
    }

}
