package com.example.authenticationservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class Jwtutil {

    @Value("${jwt.secret}")
    private final String secret = "7407b30044c5e5f06420689f9c5f167d3cf8a066cb379512ab6897923e21da1c0dcdffd9bbc7b724b7a69d4ab4d3357cda2ee3753e1460d6cf6cf7b94386248870e4d67129cce8d2b84b0bde184a8f83774e74578eabc430f86eacebb3a0afcb8cb0a2a071674e9815a9a9b316a774e85b0170dc750d3f63950f9aff26ddcc4c";
    @Value("${jwt.expiration}")
    private final String expiration = "84600";

    private Key key;

 
    public Jwtutil() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody();
    }

    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    public String generate(String email, String role, String tokenType) {
        Map<String, String> claims = Map.of("email", email, "role", role);
        long expMillis = "ACCESS".equalsIgnoreCase(tokenType)
                ? Long.parseLong(expiration) * 1000
                : Long.parseLong(expiration) * 1000 * 5;

        final Date now = new Date();
        final Date exp = new Date(now.getTime() + expMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(claims.get("id"))
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }

    private boolean isExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }
}