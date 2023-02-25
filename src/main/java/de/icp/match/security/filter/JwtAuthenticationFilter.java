package de.icp.match.security.filter;

import de.icp.match.security.service.JwtExtractor;
import de.icp.match.security.service.JwtValidator;
import de.icp.match.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String JWT_PREFIX = "Bearer ";
    private final JwtValidator jwtValidator;
    private final JwtExtractor jwtExtractor;
    private final UserDetailsServiceImpl userDetailsServiceImpl;


    public JwtAuthenticationFilter(JwtValidator jwtValidator, JwtExtractor jwtExtractor, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtValidator = jwtValidator;
        this.jwtExtractor = jwtExtractor;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {

        try {
            attemptJwtAuthentication(request, response, filterChain);
        }

        catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private void attemptJwtAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (doesNotContainJwt(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = getJwtFromAuthHeader(authHeader);

        if (jwtValidator.isTokenValid(jwt)) authenticateUserWithJwt(jwt, request);

        filterChain.doFilter(request, response);
    }


    private boolean doesNotContainJwt(String authHeader) {
        return authHeader == null || !authHeader.startsWith(JWT_PREFIX);
    }

    private String getJwtFromAuthHeader(String authHeader) {
        return authHeader.substring(JWT_PREFIX.length());
    }

    private void authenticateUserWithJwt(String token, HttpServletRequest request) {

        String usernameFromToken = jwtExtractor.getUsername(token);

        if (isAlreadySignedIn()) return;

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(usernameFromToken);

        UsernamePasswordAuthenticationToken authToken = createAuthToken(request, userDetails);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private boolean isAlreadySignedIn() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    private UsernamePasswordAuthenticationToken createAuthToken(HttpServletRequest request, UserDetails userDetails) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authToken;
    }
}
