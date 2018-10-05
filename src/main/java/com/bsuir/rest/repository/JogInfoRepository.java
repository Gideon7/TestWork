package com.bsuir.rest.repository;

import com.bsuir.rest.entity.JogInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface JogInfoRepository extends JpaRepository<JogInfoEntity, Long> {
    List<JogInfoEntity> findAllByUserEntityId(Long userId);
    JogInfoEntity findOneByIdAndUserEntityId(Long id, Long userId);
    List<JogInfoEntity> findAllByUserEntityIdOrderByDateAsc(Long userId);

    @Transactional
    void deleteOneById(Long id);

    @Transactional
    void deleteAllByUserEntityId(Long userId);
}
