package com.bsuir.rest.controllers;

import com.bsuir.rest.entities.Token;
import com.bsuir.rest.forms.UserForm;
import com.bsuir.rest.services.UserService;
import com.bsuir.rest.transfer.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> checkUser(@RequestParam(name = "username") String username,
                                              @RequestParam(name = "password") String password) {

        return ResponseEntity.ok(userService.checkUser(new UserForm(username, password)));
    }
}
