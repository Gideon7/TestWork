package com.bsuir.rest.controllers;

import com.bsuir.rest.entities.JogInfo;
import com.bsuir.rest.forms.JogInfoForm;
import com.bsuir.rest.services.JogInfoService;
import com.bsuir.rest.transfer.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JogController {

    @Autowired
    private JogInfoService jogInfoService;

    @GetMapping("/jogs")
    public List<JogInfo> getJogsInfo(@RequestHeader(value = "tokenValue") String tokenValue) {
        return jogInfoService.findAllByTokenDto(new TokenDto(tokenValue));
    }

    @PostMapping("/jogs")
    public void createJogInfo(@RequestBody JogInfoForm jogInfoForm,
                              @RequestHeader(value = "tokenValue") String tokenValue) {
        jogInfoService.createJogInfo(jogInfoForm, new TokenDto(tokenValue));
    }

    @PutMapping("/jogs/{id}")
    public void updateJogInfo(@RequestBody JogInfoForm jogInfoForm,
                              @RequestHeader(value = "tokenValue") String tokenValue,
                              @PathVariable Long id) {
        jogInfoService.updateJogInfo(jogInfoForm, new TokenDto(tokenValue), id);
    }

    @DeleteMapping("/jogs/{id}")
    public void deleteJogInfo(@RequestHeader(value = "tokenValue") String tokenValue,
                              @PathVariable Long id) {
        jogInfoService.deleteJogInfo(new TokenDto(tokenValue), id);
    }
}
