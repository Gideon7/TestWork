package com.bsuir.rest.service;

import com.bsuir.rest.entity.JogInfoEntity;
import com.bsuir.rest.entity.UserEntity;
import com.bsuir.rest.exception.NotFoundException;
import com.bsuir.rest.model.JogInfoForm;
import com.bsuir.rest.repository.JogInfoRepository;
import com.bsuir.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogInfoServiceImpl implements JogInfoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JogInfoRepository jogInfoRepository;

    @Override
    @PreAuthorize("@securityUtil.isAdminOrResourceOwner(#userId)")
    public List<JogInfoEntity> findAllByUserId(Long userId) {
        if(userRepository.findOneById(userId) == null) {
            throw new NotFoundException();
        } else {
            return jogInfoRepository.findAllByUserEntityId(userId);
        }
    }

    @Override
    @PreAuthorize("@securityUtil.isAdminOrResourceOwner(#userId)")
    public void createJogInfo(JogInfoForm jogInfoForm, Long userId) {
        UserEntity userEntity = userRepository.findOneById(userId);

        if(userEntity == null) {
            throw new NotFoundException();
        }

        JogInfoEntity jogInfoEntity = JogInfoEntity.builder()
                .userEntity(userEntity)
                .distance(jogInfoForm.getDistance())
                .time(jogInfoForm.getTime())
                .date(jogInfoForm.getDate())
                .build();

        jogInfoRepository.save(jogInfoEntity);
    }

    @Override
    @PreAuthorize("@securityUtil.isAdminOrResourceOwner(#userId)")
    public void updateJogInfo(JogInfoForm jogInfoForm, Long userId, Long jogId) {
        JogInfoEntity jogInfoEntity = jogInfoRepository.findOneByIdAndUserEntityId(jogId, userId);

        if (jogInfoEntity == null) throw new NotFoundException();

        if (!jogInfoForm.getDistance().equals(jogInfoEntity.getDistance())) {
            jogInfoEntity.setDistance(jogInfoForm.getDistance());
        }

        if (!jogInfoForm.getDate().equals(jogInfoEntity.getDate())) {
            jogInfoEntity.setDate(jogInfoForm.getDate());
        }

        if (!jogInfoForm.getTime().equals(jogInfoEntity.getTime())) {
            jogInfoEntity.setTime(jogInfoForm.getTime());
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
