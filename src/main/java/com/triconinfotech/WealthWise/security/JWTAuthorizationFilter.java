package com.triconinfotech.WealthWise.security;

import java.io.IOException;
import java.util.List;
import java.util.Collections;

import io.jsonwebtoken.*;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The JWTAuthorizationFilter class is a filter responsible for handling JWT-based authorization.
 * It validates and processes JWT tokens from the Authorization header and sets up Spring Security authentication.
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final String HEADER = "Authorization";
    private final String secretKey;
    private final DecodeJwt decodeJwt;

    /**
     * Constructs a JWTAuthorizationFilter with the provided secret key.
     *
     * @param secretKey The secret key used for JWT validation.
     */
    @Autowired
    public JWTAuthorizationFilter(String secretKey, DecodeJwt decodeJwt) {
        this.secretKey = secretKey;
        this.decodeJwt = decodeJwt;
    }

    /**
     * Performs the actual filter logic for each incoming HTTP request.
     *
     * @param request  The HTTP request.
     * @param response The HTTP response.
     * @param chain    The filter chain.
     * @throws ServletException If an error occurs during the filter process.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        try {
            if (checkJWTToken(request, response)) {
                Claims claims = validateToken(request);
                if (claims.get("email") != null) {
                    setUpSpringAuthentication(claims);
                    chain.doFilter(request, response);
                } else {
                    handleInvalidToken(response);
                }
            } else {
                handleInvalidToken(response);
            }
        } catch (Exception e) {
            handleInvalidToken(response);
        }
    }

    /**
     * Handles exceptions that may occur during the filtering process for each incoming HTTP request.
     * If an exception occurs, it returns a response indicating unauthorized access.
     *
     * @param response The HTTP response.
     * @throws ServletException If an error occurs during the filter process.
     * @throws IOException      If an I/O error occurs.
     */
    private void handleInvalidToken(HttpServletResponse response) throws IOException{
        response.getWriter().write(ErrorConstants.UNAUTHORIZED_ACCESS);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    /**
     * Validates the JWT token extracted from the request.
     *
     * @param request The HTTP request containing the JWT token.
     * @return The validated Claims object extracted from the JWT token.
     */
    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER);
        return decodeJwt.parseJwt(jwtToken).getBody();
    }

    /**
     * Sets up Spring Security authentication based on the claims from the JWT token.
     *
     * @param claims The Claims object extracted from the JWT token.
     */
    private void setUpSpringAuthentication(Claims claims) {
        String uniqueName = (String) claims.get("email");
        Long userId = claims.get("userId", Long.class);
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                uniqueName,
                null,
                authorities
        );
        // Set userId as a detail in the Authentication object
        auth.setDetails(userId);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    /**
     * Checks if the JWT token is present in the Authorization header of the request.
     *
     * @param request The HTTP request.
     * @param response     The HTTP response.
     * @return True if the JWT token is present, otherwise false.
     * @throws ServletException If an error occurs during the check.
     */
    private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && !StringUtils.isEmpty(authenticationHeader);

    }
}
