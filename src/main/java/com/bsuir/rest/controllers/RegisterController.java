package com.bsuir.rest.controllers;

import com.bsuir.rest.exceptions.AlreadyExistException;
import com.bsuir.rest.forms.UserForm;
import com.bsuir.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public void register(@RequestBody UserForm userForm) {

        if(!userService.registerUser(userForm)) throw new AlreadyExistException();
    }
}