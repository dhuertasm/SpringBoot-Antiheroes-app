package com.dahuertasm.springbootsuperheroes.antiHero.service;

import java.security.Key;
import java.sql.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtService {

    static final long EXPIRATIONTIME = 86400000;
    static final String PREFIX = "Bearer ";

    private final SecretKey key = Jwts.SIG.HS256.key().build();

    // generate signed token
    public String genToken(String username) {
        String token = Jwts.builder()
                .subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(key)
                .compact();

        return token;
    }

    // get token from request Authorization header, verify the token and get
    // username

    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
        if (token != null) {
            String user = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token.replace(PREFIX, ""))
                    .getPayload()
                    .getSubject();

            if (user != null) {
                return user;
            }

        }

        return null;

    }

}
