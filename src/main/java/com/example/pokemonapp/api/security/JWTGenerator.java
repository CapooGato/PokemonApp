package com.example.pokemonapp.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Klasa odpowiedzialna za generowanie JWT, ustawianie jego danych jak np expiration date i hashing algorytm
 * */
@Component
public class JWTGenerator {

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
                .compact();

        return token;
    }

    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()   // parser for decoding json
                .setSigningKey(SecurityConstants.JWT_SECRET)    // secret key to decode
                .parseClaimsJws(token)  // checks if token is valid
                .getBody(); // Gets the json token
        return claims.getSubject(); // returns "sub" object in claims which is often username
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser() // robimy parser
                    .setSigningKey(SecurityConstants.JWT_SECRET) // ustawiamy klczu którym został zaszyfrowany
                    .parseClaimsJws(token); // próba weryfikacji i parsowania tokenu, sprawdza czy jest expired, podpis  itd
            return true;
        } catch (Exception ex){
            throw new AuthenticationCredentialsNotFoundException("JWT was either expired or incorect");
        }
    }
}


