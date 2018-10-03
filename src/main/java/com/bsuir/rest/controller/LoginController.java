package com.bsuir.rest.controller;

import com.bsuir.rest.exception.NotFoundException;
import com.bsuir.rest.model.LoginForm;

import com.bsuir.rest.service.UserService;
import com.bsuir.rest.transfer.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public TokenDto loginUser(@RequestBody LoginForm loginForm) {
        TokenDto tokenDto = userService.checkUser(loginForm);

        if(tokenDto == null) throw new NotFoundException();
        return tokenDto;
    }
}
