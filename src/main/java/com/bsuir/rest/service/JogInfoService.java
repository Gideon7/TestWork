package com.bsuir.rest.service;

import com.bsuir.rest.model.JogInfoForm;

import java.util.List;

public interface JogInfoService {
    List<JogInfoForm> findAllByUserId(Long userId);
    List<JogInfoForm> findAllByIdsAndDate(int page, List<Long> ids, String fromDateString, String toDateString);
    Long createJogInfo(JogInfoForm jogInfoForm, Long userId);
    void updateJogInfo(JogInfoForm jogInfoForm, Long userId, Long jogId);
    void deleteJogInfo(Long userId, Long jogId);

}
