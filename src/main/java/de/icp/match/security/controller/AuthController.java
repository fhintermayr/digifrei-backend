package de.icp.match.security.controller;

import de.icp.match.api.AuthApi;
import de.icp.match.dto.LoginCredentialsDto;
import de.icp.match.security.service.JwtGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController implements AuthApi {

    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;

    public AuthController(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public ResponseEntity<String> login(LoginCredentialsDto loginCredentialsDto) {

        String username = loginCredentialsDto.getUsername();
        String password = loginCredentialsDto.getPassword();

        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authToken);

            String generatedToken = jwtGenerator.generateTokenForUser(username);

            return ResponseEntity.ok(generatedToken);
        }

        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

}
