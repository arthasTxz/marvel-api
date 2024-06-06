package com.yabeto.marvel.marvel_api.services;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_IN_MINUTES;

    private SecretKey secretKey= Jwts.SIG.HS256.key().build();

    public String generateToken(UserDetails user, Map<String, Object> extraClaims) {

        Date issueAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(issueAt.getTime() + (EXPIRATION_IN_MINUTES*60*1000));
        return Jwts
        .builder()
        .claims(extraClaims)
        .subject(user.getUsername())
        .issuedAt(issueAt)
        .expiration(expiration)
        .header().add("typ", "JWT").and()
        .signWith(this.secretKey)
        .compact();
    }

    private SecretKey generateKey() {
        return Jwts.SIG.HS256.key().build();
    }

    public String extractSubject(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }


    private Claims extractAllClaims(String jwt){
        return Jwts.parser().verifyWith(this.secretKey).build().parseSignedClaims(jwt).getPayload();
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }


    

}
