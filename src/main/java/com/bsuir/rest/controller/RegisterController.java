package com.bsuir.rest.controller;

import com.bsuir.rest.exception.AlreadyExistException;
import com.bsuir.rest.model.RegisterForm;
import com.bsuir.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody RegisterForm registerForm) {

        if(!userService.registerUser(registerForm)) throw new AlreadyExistException();
    }
}