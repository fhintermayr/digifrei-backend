package de.icp.match.security;

import de.icp.match.security.service.JwtExtractor;
import de.icp.match.security.service.JwtValidator;
import de.icp.match.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtValidator jwtValidator;
    @Mock private JwtExtractor jwtExtractor;
    @Mock private UserDetailsServiceImpl userDetailsServiceImpl;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;
    private FilterChain mockFilterChain;

    @BeforeEach
    public void setup() {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtValidator, jwtExtractor, userDetailsServiceImpl);

        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        mockFilterChain = (request, response) -> {};

        SecurityContextHolder.clearContext();
    }

    @Test
    public void attemptJwtAuthentication_withNoAuthHeader_shouldNotAuthenticateUser() throws IOException {

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void attemptJwtAuthentication_withInvalidJwt_shouldNotAuthenticateUser() throws IOException {

        mockRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer invalid-jwt");
        when(jwtValidator.isTokenValid(anyString())).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void attemptJwtAuthentication_withValidJwt_shouldAuthenticateUser() throws IOException {

        String jwt = "valid-jwt";
        mockRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        String username = "testuser";
        when(jwtValidator.isTokenValid(jwt)).thenReturn(true);
        when(jwtExtractor.getUsername(jwt)).thenReturn(username);

        UserDetails userDetails = new User(username, "", Set.of(new SimpleGrantedAuthority("ADMIN")));
        when(userDetailsServiceImpl.loadUserByUsername(username)).thenReturn(userDetails);


        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}