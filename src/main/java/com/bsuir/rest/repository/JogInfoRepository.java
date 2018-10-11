package com.bsuir.rest.repository;

import com.bsuir.rest.entity.JogInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface JogInfoRepository extends JpaRepository<JogInfoEntity, Long> {
    List<JogInfoEntity> findAllByUserEntityId(Long userId);
    JogInfoEntity findOneByIdAndUserEntityId(Long id, Long userId);
    List<JogInfoEntity> findAllByUserEntityIdOrderByDateAsc(Long userId, Pageable pageable);

    @Query(value = "SELECT *\n" +
                    "FROM jogs_info\n" +
                    "WHERE jogs_info.user_id IN (:ids)\n" +
                    "AND ((:fromDate IS NULL) OR (jogs_info.date >= to_date(CAST(:fromDate AS TEXT), 'yyyy-mm-dd')))\n" +
                    "AND ((:toDate IS NULL) OR (jogs_info.date <= to_date(CAST(:toDate AS TEXT), 'yyyy-mm-dd'))) /*--#pageable*/",
            countQuery = "SELECT count(*) FROM jogs_info \n" +
                    "WHERE jogs_info.user_id IN (:ids)\n" +
                    "AND ((:fromDate IS NULL) OR (jogs_info.date >= to_date(CAST(:fromDate AS TEXT), 'yyyy-mm-dd')))\n" +
                    "AND ((:toDate IS NULL) OR (jogs_info.date <= to_date(CAST(:toDate AS TEXT), 'yyyy-mm-dd')))",
            nativeQuery = true)
    Page<JogInfoEntity> findAllByIdsAndDates(@Param("ids") List<Long> ids,
                                             @Param("fromDate") String fromDate,
                                             @Param("toDate") String toDate,
                                             Pageable pageable);


    @Query(value = "SELECT *\n" +
                    "FROM jogs_info\n" +
                    "WHERE ((:fromDate IS NULL) OR (jogs_info.date >= to_date(CAST(:fromDate AS TEXT), 'yyyy-mm-dd')))\n" +
                    "AND ((:toDate IS NULL) OR (jogs_info.date <= to_date(CAST(:toDate AS TEXT), 'yyyy-mm-dd'))) /*--#pageable*/",
            countQuery = "SELECT count(*) FROM jogs_info \n" +
                    "WHERE ((:fromDate IS NULL) OR (jogs_info.date >= to_date(CAST(:fromDate AS TEXT), 'yyyy-mm-dd')))\n" +
                    "AND ((:toDate IS NULL) OR (jogs_info.date <= to_date(CAST(:toDate AS TEXT), 'yyyy-mm-dd')))",
            nativeQuery = true)
    Page<JogInfoEntity> findAllByDates(@Param("fromDate") String fromDate,
                                       @Param("toDate") String toDate,
                                       Pageable pageable);

    @Transactional
    void deleteOneById(Long id);

    @Transactional
    void deleteAllByUserEntityId(Long userId);
}
