package com.bsuir.rest.repository;

import com.bsuir.rest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findOneByUsername(String username);
    UserEntity findOneById(Long Id);
}
