package de.icp.match.security.service;

import de.icp.match.user.model.AccessRole;
import de.icp.match.user.model.User;
import de.icp.match.user.service.UserQueryService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtGenerator {

    private final UserQueryService userQueryService;
    @Value("${jwt.validityInHours}")
    private int tokenValidityInHours;
    private final JwtSecretProvider jwtSecretProvider;

    public JwtGenerator(JwtSecretProvider jwtSecretProvider, UserQueryService userQueryService) {
        this.jwtSecretProvider = jwtSecretProvider;
        this.userQueryService = userQueryService;
    }

    public String generateTokenForUser(String username) {

        AccessRole usersAccessRole = getAccessRoleOfUser(username);

        Instant creationTimestamp = Instant.now();
        Instant expirationTimestamp = creationTimestamp.plus(tokenValidityInHours, ChronoUnit.HOURS);

        SecretKey signingKey = jwtSecretProvider.getSigningKey();

        return Jwts.builder()
                .setSubject(username)
                .claim("role", usersAccessRole.toString())
                .setIssuer("Stiftung ICP München")
                .setIssuedAt(Date.from(creationTimestamp))
                .setExpiration(Date.from(expirationTimestamp))
                .signWith(signingKey)
                .compact();
    }

    private AccessRole getAccessRoleOfUser(String username) {
        return userQueryService.loadUserByUsername(username).getAccessRole();
    }

}
