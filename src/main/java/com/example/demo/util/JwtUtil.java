package com.example.demo.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;

import org.springframework.stereotype.Component;



import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final Key key;
    private final long accessTokenValidityMillis;
    private final long refreshTokenValidityMillis;
    private final String issuer;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-exp-min:15}") long accessMin,
            @Value("${jwt.refresh-token-exp-days:7}") long refreshDays,
            @Value("${jwt.issuer:example-app}") String issuer) {
        // secret:base64 or plain keys for.hmacshakey requires bytes length>=32 for
        // hs256
        // this.Key=Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenValidityMillis = accessMin * 60 * 1000;
        this.refreshTokenValidityMillis = refreshDays * 24 * 60 * 60 * 1000;
        this.issuer = issuer;
    }

    public String generateAccessToken(String username, Object roles) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + accessTokenValidityMillis);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .claim("roles", roles)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    public String generateRefreshToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + refreshTokenValidityMillis);

        return Jwts.builder()
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(exp)
                .claim("type", "refresh")
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<String> getUsernameFromToken(String token) {
        try {
            return Optional.ofNullable(getClaims(token).getSubject());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

     public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaims(token);
        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof Collection) {
            return ((Collection<?>) rolesObj).stream().map(Object::toString).collect(Collectors.toList());
        } else if (rolesObj != null) {
            return Arrays.asList(rolesObj.toString());
        } else {
            return Collections.emptyList();
        }
    }

}
