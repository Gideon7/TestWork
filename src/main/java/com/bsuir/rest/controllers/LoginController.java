package com.bsuir.rest.controllers;

import com.bsuir.rest.exceptions.NotFoundException;
import com.bsuir.rest.forms.UserForm;
import com.bsuir.rest.services.UserService;
import com.bsuir.rest.transfer.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public TokenDto loginUser(@RequestBody UserForm userForm) {
        TokenDto tokenDto = userService.checkUser(userForm);

        if(tokenDto == null) throw new NotFoundException();
        return tokenDto;
    }
}
