package de.icp.match.security.controller;

import de.icp.match.api.AuthApi;
import de.icp.match.dto.JwtResponseDto;
import de.icp.match.dto.LoginCredentialsDto;
import de.icp.match.dto.UserDto;
import de.icp.match.security.service.CurrentUserService;
import de.icp.match.security.service.JwtGenerator;
import de.icp.match.user.mapper.UserMapperImpl;
import de.icp.match.user.model.User;
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
    private final CurrentUserService currentUserService;
    private final UserMapperImpl userMapperImpl;

    public AuthController(AuthenticationManager authenticationManager, JwtGenerator jwtGenerator, CurrentUserService currentUserService, UserMapperImpl userMapperImpl) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.currentUserService = currentUserService;
        this.userMapperImpl = userMapperImpl;
    }

    @Override
    public ResponseEntity<JwtResponseDto> login(LoginCredentialsDto loginCredentialsDto) {

        String username = loginCredentialsDto.getUsername();
        String password = loginCredentialsDto.getPassword();

        try {
            attemptLoginWithCredentials(username, password);
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String generatedToken = jwtGenerator.generateTokenForUser(username);
        JwtResponseDto jwtResponseDto = new JwtResponseDto().token(generatedToken);

        return ResponseEntity.ok(jwtResponseDto);
    }

    @Override
    public ResponseEntity<UserDto> getCurrentUser() {
        try {
            User currentlyAuthenticatedUser = currentUserService.getCurrentlyAuthenticatedUser();
            UserDto userDto = userMapperImpl.toDto(currentlyAuthenticatedUser);

            return ResponseEntity.ok(userDto);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private void attemptLoginWithCredentials(String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authToken);
    }

}