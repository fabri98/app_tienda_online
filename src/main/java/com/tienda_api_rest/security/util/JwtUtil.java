package com.tienda_api_rest.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private static final long EXPIRATION = 1000 * 60 * 60;
    private static final String SECRET_KEY = "adec95f592f5b4ca2555f897e334f4fa9b0d35e3ac3faf3a3521fedbc75dd813";

    private Key getKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        var authorities = authentication.getAuthorities();

        String role = authorities.stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");

        return Jwts.builder()
                .setSubject(username)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().before(new Date());
    }

    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    public String extractAuthorities(String token){
        return getClaims(token).get("role").toString();
    }

    public boolean isTokenValid(String token, String username){
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }
}
