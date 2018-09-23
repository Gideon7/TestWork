package com.bsuir.rest.controllers;

import com.bsuir.rest.entities.User;
import com.bsuir.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> userList = userService.findAll();
        return userList;
    }
}
