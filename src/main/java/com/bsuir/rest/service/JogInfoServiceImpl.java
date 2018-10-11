package com.bsuir.rest.service;

import com.bsuir.rest.entity.JogInfoEntity;
import com.bsuir.rest.entity.UserEntity;
import com.bsuir.rest.exception.NotFoundException;
import com.bsuir.rest.model.JogInfoForm;
import com.bsuir.rest.repository.JogInfoRepository;
import com.bsuir.rest.repository.UserRepository;
import com.bsuir.rest.utility.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class JogInfoServiceImpl implements JogInfoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JogInfoRepository jogInfoRepository;

    @Override
    @PreAuthorize("@securityUtil.isAdminOrResourceOwner(#userId)")
    public List<JogInfoForm> findAllByUserId(Long userId) {

        if (userRepository.findOneById(userId) == null) {
            throw new NotFoundException();
        }


        return JogInfoForm.from(jogInfoRepository.findAllByUserEntityId(userId));
    }

    @Override
    public List<JogInfoForm> findAllByIdsAndDate(int page, int pageSize, List<Long> ids,
                                                 String fromDateString, String toDateString, String sortDir) {

        if(fromDateString != null) {
            DateValidator.validateDateString(fromDateString);
        }

        if(toDateString != null) {
            DateValidator.validateDateString(toDateString);
        }

        Pageable pageable = new PageRequest(page - 1, pageSize, new Sort(sortDir.equals("ASC")
                ? Sort.Direction.ASC : Sort.Direction.DESC, "date"));

        if(ids == null) {
            return JogInfoForm.from(jogInfoRepository.findAllByDates(fromDateString, toDateString, pageable).getContent());
        }

        return JogInfoForm.from(jogInfoRepository.findAllByIdsAndDates(ids, fromDateString, toDateString, pageable).getContent());
    }

    @Override
    @PreAuthorize("@securityUtil.isAdminOrResourceOwner(#userId)")
    public Long createJogInfo(JogInfoForm jogInfoForm, Long userId) {

        UserEntity userEntity = userRepository.findOneById(userId);

        if (userEntity == null) {
            throw new NotFoundException();
        }

        JogInfoEntity jogInfoEntity = JogInfoEntity.builder()
                .userEntity(userEntity)
                .distance(jogInfoForm.getDistance())
                .time(LocalTime.parse(jogInfoForm.getTime()))
                .date(LocalDate.parse(jogInfoForm.getDate()))
                .build();

        return jogInfoRepository.save(jogInfoEntity).getId();
    }

    @Override
    @PreAuthorize("@securityUtil.isAdminOrResourceOwner(#userId)")
    public void updateJogInfo(JogInfoForm jogInfoForm, Long userId, Long jogId) {

        JogInfoEntity jogInfoEntity = jogInfoRepository.findOneByIdAndUserEntityId(jogId, userId);

        if (jogInfoEntity == null) {
            throw new NotFoundException();
        }

        if (!jogInfoForm.getDistance().equals(jogInfoEntity.getDistance())) {
            jogInfoEntity.setDistance(jogInfoForm.getDistance());
        }

        if (!jogInfoForm.getDate().equals(jogInfoEntity.getDate().toString())) {
            jogInfoEntity.setDate(LocalDate.parse(jogInfoForm.getDate()));
        }

        if (!jogInfoForm.getTime().equals(jogInfoEntity.getTime().toString())) {
            jogInfoEntity.setTime(LocalTime.parse(jogInfoForm.getTime()));
        }

        jogInfoRepository.save(jogInfoEntity);
    }

    @Override
    @PreAuthorize("@securityUtil.isAdminOrResourceOwner(#userId)")
    public void deleteJogInfo(Long userId, Long jogId) {

        JogInfoEntity jogInfoEntity = jogInfoRepository.findOneByIdAndUserEntityId(jogId, userId);

        if (jogInfoEntity == null) {
            throw new NotFoundException();
        }

        jogInfoRepository.deleteOneById(jogId);
    }
}
