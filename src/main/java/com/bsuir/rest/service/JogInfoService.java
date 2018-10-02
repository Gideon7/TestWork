package com.bsuir.rest.service;

import com.bsuir.rest.entity.JogInfoEntity;
import com.bsuir.rest.model.JogInfoForm;
import com.bsuir.rest.transfer.TokenDto;

import java.util.List;

public interface JogInfoService {
    List<JogInfoEntity> findAllByUserId(Long userId);
    void createJogInfo(JogInfoForm jogInfoForm, Long userId);
    void updateJogInfo(JogInfoForm jogInfoForm, Long userId, Long jogId);
    void deleteJogInfo(Long userId, Long jogId);
}
