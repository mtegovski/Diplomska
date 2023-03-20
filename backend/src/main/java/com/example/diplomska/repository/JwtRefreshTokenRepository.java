package com.example.diplomska.repository;

import com.example.diplomska.model.JwtRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, String> {

}
