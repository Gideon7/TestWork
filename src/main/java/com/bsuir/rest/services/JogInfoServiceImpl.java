package com.bsuir.rest.services;

import com.bsuir.rest.entities.JogInfo;
import com.bsuir.rest.entities.Token;
import com.bsuir.rest.entities.User;
import com.bsuir.rest.exceptions.NotFoundException;
import com.bsuir.rest.forms.JogInfoForm;
import com.bsuir.rest.repositories.JogInfoRepository;
import com.bsuir.rest.repositories.TokenRepository;
import com.bsuir.rest.repositories.UserRepository;
import com.bsuir.rest.transfer.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogInfoServiceImpl implements JogInfoService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JogInfoRepository jogInfoRepository;

    @Override
    public List<JogInfo> findAllByTokenDto(TokenDto tokenDto) {
        Token token = tokenRepository.findOneByValue(tokenDto.getValue());
        if(token == null) throw new NotFoundException();

        return jogInfoRepository.findAllByUser_Username(token.getUser().getUsername());
    }

    public void createJogInfo(JogInfoForm jogInfoForm, TokenDto tokenDto) {
        Token token = tokenRepository.findOneByValue(tokenDto.getValue());
        if(token == null) throw new NotFoundException();

        User user = token.getUser();
        JogInfo jogInfo = JogInfo.builder()
                                    .user(user)
                                    .distance(jogInfoForm.getDistance())
                                    .time(jogInfoForm.getTime())
                                    .date(jogInfoForm.getDate())
                                    .build();

        jogInfoRepository.save(jogInfo);
    }

    @Override
    public void updateJogInfo(JogInfoForm jogInfoForm, TokenDto tokenDto, Long id) {
        Token token = tokenRepository.findOneByValue(tokenDto.getValue());
        if(token == null) throw new NotFoundException();

        JogInfo jogInfo = jogInfoRepository.findOneByIdAndUser_Id(id, token.getUser().getId());
        if(jogInfo == null) throw new NotFoundException();

        if(jogInfoForm.getDistance() != jogInfo.getDistance()) jogInfo.setDistance(jogInfoForm.getDistance());
        if(!jogInfoForm.getDate().equals(jogInfo.getDate())) jogInfo.setDate(jogInfoForm.getDate());
        if(!jogInfoForm.getTime().equals(jogInfo.getTime())) jogInfo.setTime(jogInfoForm.getTime());

        jogInfoRepository.save(jogInfo);
    }

    @Override
    public void deleteJogInfo(TokenDto tokenDto, Long id) {
        Token token = tokenRepository.findOneByValue(tokenDto.getValue());
        if(token == null) throw new NotFoundException();

        JogInfo jogInfo = jogInfoRepository.findOneByIdAndUser_Id(id, token.getUser().getId());
        if(jogInfo == null) throw new NotFoundException();

        jogInfoRepository.deleteOneById(id);
    }
}
