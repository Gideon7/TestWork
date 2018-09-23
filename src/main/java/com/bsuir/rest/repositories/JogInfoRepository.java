package com.bsuir.rest.repositories;

import com.bsuir.rest.entities.JogInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface JogInfoRepository extends JpaRepository<JogInfo, Long> {
    List<JogInfo> findAllByUser_Username(String username);
    JogInfo findOneByIdAndUser_Id(Long id, Long userId);

    @Transactional
    void deleteOneById(Long id);
}
