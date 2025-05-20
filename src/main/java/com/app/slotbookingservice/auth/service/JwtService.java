package com.app.slotbookingservice.auth.service;

import com.app.slotbookingservice.auth.dto.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtService {

    Set<String> blacklist = ConcurrentHashMap.newKeySet();

    private final JwtProperties jwtProperties;

    private Key key;

    @PostConstruct
    public void init() {
        System.out.println("Secret key length in bytes: " + jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8).length);
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) throws Exception {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("user", userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(key)
                .compact();
    }

    public void blackListToken(final String token) {
        blacklist.add(token);
        log.info("Token added to blacklist...");
    }

    public String extractUsername(final String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(final String token, final String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(final String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public boolean isBlackListedToken(final String token) {
        return blacklist.contains(token);
    }

    public Claims validateToken(final String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret().getBytes())
                    .build().parseClaimsJws(token);

            Claims claims = jws.getBody();

            if (claims.getExpiration().before(new Date())) {
                throw new RuntimeException("Token expired");
            }

            return claims;

        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired");
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported JWT");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Malformed JWT");
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Empty or null JWT");
        }
    }

}
