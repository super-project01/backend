package com.example.beproject.repository.token;

import com.example.beproject.domain.jwt.token.Token;
import com.example.beproject.entity.token.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {

    TokenEntity findByRefreshToken(String refreshToken);

    Optional<TokenEntity> findByMemberEmail(String memberEmail);

    TokenEntity findByAccessToken(String accessToken);
}
