package com.example.diplomska.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jwt_refresh_token")
public class JwtRefreshToken
{
    @Id
    @Column(name = "token_id", nullable = false)
    private String tokenId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;
}
