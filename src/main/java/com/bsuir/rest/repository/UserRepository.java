package com.bsuir.rest.repository;

import com.bsuir.rest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findOneByUsername(String username);
    UserEntity findOneById(Long Id);

    @Query(value = "SELECT id FROM users", nativeQuery = true)
    List<Long> findAllId();

    @Transactional
    void deleteOneByUsername(String username);
}
