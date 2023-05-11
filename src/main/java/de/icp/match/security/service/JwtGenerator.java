package de.icp.match.security.service;

import de.icp.match.user.model.User;
import de.icp.match.user.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final UserRepository userRepository;

    public JwtGenerator(JwtSecretProvider jwtSecretProvider,
                        UserRepository userRepository) {
        this.jwtSecretProvider = jwtSecretProvider;
        this.userRepository = userRepository;
    }

    public String generateTokenForUser(String username) {

        String usersAccessRole = getAccessRoleOfUser(username);

        Instant creationTimestamp = Instant.now();
        Instant expirationTimestamp = creationTimestamp.plus(tokenValidityInHours, ChronoUnit.HOURS);

        SecretKey signingKey = jwtSecretProvider.getSigningKey();

        return Jwts.builder()
                .setSubject(username)
                .claim("role", usersAccessRole)
                .setIssuer("Stiftung ICP München")
                .setIssuedAt(Date.from(creationTimestamp))
                .setExpiration(Date.from(expirationTimestamp))
                .signWith(signingKey)
                .compact();
    }

    private String getAccessRoleOfUser(String username) {
        //TODO: Replace with findEmployeeService
        User employee = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(""));
        return employee.getClass().getSimpleName().toUpperCase();
    }

}
