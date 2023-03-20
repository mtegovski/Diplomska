package com.example.diplomska.service.impl;

import com.example.diplomska.model.JwtRefreshToken;
import com.example.diplomska.model.User;
import com.example.diplomska.repository.JwtRefreshTokenRepository;
import com.example.diplomska.repository.UserRepository;
import com.example.diplomska.security.UserPrincipal;
import com.example.diplomska.security.jwt.JwtProvider;
import com.example.diplomska.service.JwtRefreshTokenService;
import com.example.diplomska.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@Service
public class JwtRefreshTokenServiceImpl implements JwtRefreshTokenService {

    @Value("${app.jwt.refresh-expiration-in-ms}")
    private Long REFRESH_EXPIRATION_IN_MS;

    private JwtRefreshTokenRepository jwtRefreshTokenRepository;

    private UserRepository userRepository;

    private JwtProvider jwtProvider;

    public JwtRefreshTokenServiceImpl(JwtRefreshTokenRepository jwtRefreshTokenRepository, UserRepository userRepository, JwtProvider jwtProvider) {
        this.jwtRefreshTokenRepository = jwtRefreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public JwtRefreshToken createRefreshToken(String email) {
        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken();

        jwtRefreshToken.setTokenId(UUID.randomUUID().toString());
        jwtRefreshToken.setEmail(email);
        jwtRefreshToken.setCreateDate(LocalDateTime.now());
        jwtRefreshToken.setExpirationDate(LocalDateTime.now().plus(REFRESH_EXPIRATION_IN_MS, ChronoUnit.MILLIS));

        return this.jwtRefreshTokenRepository.save(jwtRefreshToken);
    }

    @Override
    public User generateAccessTokenFromRefreshToken(String refreshTokenId)
    {
        JwtRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.findById(refreshTokenId).orElseThrow();

        if (jwtRefreshToken.getExpirationDate().isBefore(LocalDateTime.now()))
        {
            throw new RuntimeException("JWT refresh token is not valid.");
        }

        User user = userRepository.findUserByEmail(jwtRefreshToken.getEmail()).orElseThrow();

        UserPrincipal userPrincipal = UserPrincipal.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(Set.of(SecurityUtils.convertToAuthority(user.getRole().name())))
                .build();

        String accessToken = jwtProvider.generateToken(userPrincipal);

        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshTokenId);

        return user;
    }

}
