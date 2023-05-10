package de.icp.match.security.service;

import de.icp.match.user.model.Employee;
import de.icp.match.user.repository.EmployeeRepository;
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

    private final EmployeeRepository employeeRepository;
    @Value("${jwt.validityInHours}")
    private int tokenValidityInHours;
    private final JwtSecretProvider jwtSecretProvider;

    public JwtGenerator(JwtSecretProvider jwtSecretProvider, EmployeeRepository employeeRepository) {
        this.jwtSecretProvider = jwtSecretProvider;
        this.employeeRepository = employeeRepository;
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
        Employee employee = employeeRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(""));
        return employee.getClass().getSimpleName().toUpperCase();
    }

}
