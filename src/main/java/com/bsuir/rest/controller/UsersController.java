package com.bsuir.rest.controller;

import com.bsuir.rest.entity.UserEntity;
import com.bsuir.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserEntity> getUsers() {
        List<UserEntity> userEntityList = userService.findAll();
        return userEntityList;
    }
}
