package com.apigateway.apigateway.config;

import com.fasterxml.jackson.databind.ser.std.ToEmptyObjectSerializer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class GateWayJwtService {

    private String SECRET_KEY = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpirationTime(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public List<String> extractUserRoles(String token) {

        Object listOfRoles = extractAllClaims(token).get("roles");

        if(listOfRoles instanceof List<?>) {
            return ((List<?>) listOfRoles)
                    .stream()
                    .filter(role -> role instanceof String)
                    .map(role -> (String)role)
                    .toList();
        }

        return List.of();
    }

    public <T> T extractClaims(String token , Function<Claims, T> claims) {
        final Claims claims1 = extractAllClaims(token);
        return claims.apply(claims1);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public Key getSignInKey() {
        byte[] keys = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keys);
    }
}
