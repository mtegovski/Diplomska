package com.example.diplomska.security.jwt;

import com.example.diplomska.security.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtProvider {
    String generateToken(UserPrincipal auth);

    boolean isTokenValid(HttpServletRequest request);

    Authentication getAuthentication(HttpServletRequest request);
}
