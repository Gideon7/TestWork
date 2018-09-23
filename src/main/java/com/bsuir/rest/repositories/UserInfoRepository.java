package com.bsuir.rest.repositories;

import com.bsuir.rest.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    List<UserInfo> findAllByUser_Username(String username);
    UserInfo findOneById(Long id);

    @Transactional
    void deleteOneById(Long id);
}
