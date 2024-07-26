package com.triconinfotech.WealthWise.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;

/**
 * The DecodeJwt class provides methods for decoding JSON Web Tokens (JWTs) and extracting specific claims.
 * It uses the secret key provided in the application properties for JWT validation.
 */
@Service
public class DecodeJwt {

    @Value("${secret.key}")
    private String SECRET_KEY;

    /**
     * Extracts the user ID from the JWT token.
     *
     * @param token The JWT token from which to extract the user ID.
     * @return The extracted user ID or null if extraction fails.
     */
    public Long extractUserIdFromToken(String token) {
        return extractClaimFromToken(token, "userId", Long.class);
    }

    /**
     * Extracts the customer ID from the JWT token.
     *
     * @param token The JWT token from which to extract the customer ID.
     * @return The extracted customer ID or null if extraction fails.
     */
    public Long extractOrganizationIdFromToken(String token) {
        return extractClaimFromToken(token, "orgId", Long.class);
    }

    /**
     * Extracts the role from the JWT token.
     *
     * @param token The JWT token from which to extract the customer ID.
     * @return The extracted customer ID or null if extraction fails.
     */
    public String extractRoleFromToken(String token) {
        return extractClaimFromToken(token, "role", String.class);
    }

    /**
     * Generic method to extract a specific claim from the JWT token.
     *
     * @param token      The JWT token from which to extract the claim.
     * @param claimName  The name of the claim to extract.
     * @param valueType  The class type of the value to return.
     * @param <T>        The type of the value to return.
     * @return The extracted claim value or null if extraction fails.
     */
    private <T> T extractClaimFromToken(String token, String claimName, Class<T> valueType) {
        try {
            // Parses the JWT token and retrieves the specified claim
            Jws<Claims> claimsJws = parseJwt(token);
            return claimsJws.getBody().get(claimName, valueType);
        } catch (Exception e) {
            // Returns null if there is an exception (e.g., token parsing error)
            return null;
        }
    }

    /**
     * Parses the JWT token and returns the Claims object.
     *
     * @param bearerToken The JWT token to be parsed.
     * @return The Jws object containing the Claims if parsing is successful.
     * @throws JwtException If there is an issue with JWT parsing.
     */
    public Jws<Claims> parseJwt(String bearerToken) throws JwtException {

        String token = bearerToken.replace("Bearer ", "");

        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}

