package com.bsuir.rest.repository;

import com.bsuir.rest.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    TokenEntity findOneByValue(String value);
}
