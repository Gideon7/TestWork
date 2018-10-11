package com.bsuir.rest.controller;

import com.bsuir.rest.model.JogInfoForm;
import com.bsuir.rest.service.JogInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JogController {

    @Autowired
    private JogInfoService jogInfoService;

    @GetMapping("/jogs")
    public List<JogInfoForm> getJogsInfo(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                                         @RequestParam(value = "userId", required = false) List<Long> userId,
                                         @RequestParam(value = "fromDate", required = false) String fromDateString,
                                         @RequestParam(value = "toDate", required = false) String toDateString,
                                         @RequestParam(value = "sortDir", required = false, defaultValue = "ASC") String sortDir) {
        return jogInfoService.findAllByIdsAndDate(page, pageSize, userId, fromDateString, toDateString, sortDir);
    }

    @PostMapping("/jogs")
    public void createJogInfo(@RequestBody JogInfoForm jogInfoForm,
                              @RequestParam("userId") Long userId) {
        jogInfoService.createJogInfo(jogInfoForm, userId);
    }

    @PutMapping("/jogs/{jogId}")
    public void updateJogInfo(@RequestBody JogInfoForm jogInfoForm,
                              @RequestParam("userId") Long userId,
                              @PathVariable Long jogId) {
        jogInfoService.updateJogInfo(jogInfoForm, userId, jogId);
    }

    @DeleteMapping("/jogs/{jogId}")
    public void deleteJogInfo(@RequestParam("userId") Long userId,
                              @PathVariable Long jogId) {
        jogInfoService.deleteJogInfo(userId, jogId);
    }
}
