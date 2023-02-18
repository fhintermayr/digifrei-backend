package de.icp.match.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtExtractor {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String getUsername(String token) {
        return getBody(token).getSubject();
    }

    public Date getExpirationDate(String token) {
        return getBody(token).getExpiration();
    }

    private Claims getBody(String token) {
        // FIXME: setSigningKey() is deprecated
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
    }

}
