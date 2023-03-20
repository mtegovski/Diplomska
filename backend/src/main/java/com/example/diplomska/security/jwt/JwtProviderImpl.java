package com.example.diplomska.security.jwt;

import com.example.diplomska.security.UserPrincipal;
import com.example.diplomska.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtProviderImpl implements JwtProvider {

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.expiration-in-ms}")
    private String JWT_EXPIRATION_IN_MS;

    @Override
    public String generateToken(UserPrincipal auth) {
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(auth.getEmail())
                .claim("roles", authorities)
                .claim("email", auth.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(JWT_EXPIRATION_IN_MS)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public boolean isTokenValid(HttpServletRequest request) {
        Claims claims = extractClaims(request);

        if (claims == null) {
            return false;
        }

        return !claims.getExpiration().before(new Date());
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        Claims claims = extractClaims(request);

        if (claims == null) {
            return null;
        }

        String email = claims.getSubject();
        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(Collectors.toSet());
        UserDetails userDetails = UserPrincipal.builder()
                .email(email)
                .authorities(authorities)
                .build();

        if (email == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    private Claims extractClaims(HttpServletRequest request) {
        String token = SecurityUtils.extractAuthTokenFromRequest(request);

        if (token == null) {
            return null;
        }

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
