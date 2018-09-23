package com.bsuir.rest.controllers;

import com.bsuir.rest.forms.UserForm;
import com.bsuir.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestParam(value = "username") String username,
                                           @RequestParam(value = "password") String password) {

        if(userService.registerUser(new UserForm(username, password))) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
