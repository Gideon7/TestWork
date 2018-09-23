package com.bsuir.rest.repositories;

import com.bsuir.rest.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findOneByValue(String value);
}
