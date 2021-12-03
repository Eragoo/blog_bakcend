package com.Eragoo.Blog.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class TokenProvider {
    private static final String USERNAME_CLAIM = "username";
    private static final String PERMISSIONS_CLAIM = "permissions";

    private SecurityProps securityProps;

    public String createToken(String username, List<String> permissions) {
        return JWT.create()
                .withClaim(USERNAME_CLAIM, username)
                .withClaim(PERMISSIONS_CLAIM, permissions)
                .withExpiresAt(Date.from(Instant.now().plus(securityProps.getLifetime())))
                .sign(Algorithm.HMAC512(securityProps.getSignature().getBytes()));
    }

    public Optional<AuthenticatedUser> parseUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(securityProps.getSignature().getBytes())
                .parseClaimsJws(token)
                .getBody();

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(
                claims.get(USERNAME_CLAIM, String.class),
                claims.get(PERMISSIONS_CLAIM, List.class)
        );
        return Optional.of(authenticatedUser);
    }
}
