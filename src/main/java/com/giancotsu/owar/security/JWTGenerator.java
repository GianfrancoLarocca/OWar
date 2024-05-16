package com.giancotsu.owar.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

@Component
public class JWTGenerator {

    private final long JWT_EXPIRATION = 1000 * 60 * 60 * 24L; // 24 hours
    private final long JWT_EXPIRATION_LONG = 1000 * 60 * 60 * 24 * 7L; // 1 week
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication, String expTime) {

        LocalDateTime exp;

        if(expTime.equals("week")) {
            exp = LocalDateTime.now().plus(JWT_EXPIRATION_LONG, ChronoUnit.MILLIS).plusHours(2);
        } else {
            exp = LocalDateTime.now().plus(JWT_EXPIRATION, ChronoUnit.MILLIS).plusHours(2);
        }


        return Jwts
                .builder()
                .subject(authentication.getName())
                .issuedAt(new Date(System.currentTimeMillis()))
                //.expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .expiration(Date.from(exp.atZone(ZoneId.of("Europe/Rome")).toInstant()))
                .signWith(getSigninKey())
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigninKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
        }
    }

}