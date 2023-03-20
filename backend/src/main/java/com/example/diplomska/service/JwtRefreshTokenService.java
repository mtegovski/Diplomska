package com.example.diplomska.service;

import com.example.diplomska.model.JwtRefreshToken;
import com.example.diplomska.model.User;

public interface JwtRefreshTokenService {
    JwtRefreshToken createRefreshToken(String email);

    User generateAccessTokenFromRefreshToken(String refreshTokenId);
}
