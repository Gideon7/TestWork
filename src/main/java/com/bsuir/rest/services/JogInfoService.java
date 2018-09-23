package com.bsuir.rest.services;

import com.bsuir.rest.entities.JogInfo;
import com.bsuir.rest.forms.JogInfoForm;
import com.bsuir.rest.transfer.TokenDto;

import java.util.List;

public interface JogInfoService {
    public List<JogInfo> findAllByTokenDto(TokenDto tokenDto);
    public void createJogInfo(JogInfoForm jogInfoForm, TokenDto tokenDto);
    public void updateJogInfo(JogInfoForm jogInfoForm, TokenDto tokenDto, Long id);
    public void deleteJogInfo(TokenDto tokenDto, Long id);
}
