package com.demo.auth.service;

import com.demo.auth.model.Role;
import com.demo.auth.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author <a href="https://github.com/ClaudiaDthOrNot">Claudia</a>
 */
@Service
public class JwtService {
    public String generateTokenWithUserInfo(@NonNull User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getId());
        claims.put("role", user.getRole().toString());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 7 * 60 * 60 * 10))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, @NonNull User user) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 7 * 60 * 60 * 10))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    public User obtainUserFromToken(final String token) {
        if (token == null) return null;
        Claims claims = extractAllClaims(token);

        return User.builder()
                .id((String) claims.get("id"))
                .name((String) claims.get("name"))
                .role(Role.valueOf(claims.get("roles").toString()))
                .email((String) claims.get("email"))
                .build();
    }

    public boolean isTokenValid(String token, @NonNull UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @NonNull
    private SecretKey getSignInKey() {
        byte[] key = Decoders.BASE64.decode("my_secret");
        return Keys.hmacShaKeyFor(key);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, @NonNull Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}
