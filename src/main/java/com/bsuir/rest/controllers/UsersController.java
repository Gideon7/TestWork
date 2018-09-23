package com.bsuir.rest.controllers;

import com.bsuir.rest.entities.User;
import com.bsuir.rest.entities.UserInfo;
import com.bsuir.rest.forms.UserInfoForm;
import com.bsuir.rest.services.UserInfoService;
import com.bsuir.rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RestController
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/users/{username}/")
    public List<UserInfo> getUserInfoList(@PathVariable("username") String username) {
        List<UserInfo> userInfoList = userInfoService.findAllByUsername(username);
        return userInfoList;
    }

    @PostMapping("/users/{username}/add")
    public void addUserInfo(@PathVariable("username") String username,
                            @RequestParam(name = "distance") String distance,
                            @RequestParam(name = "time") String time,
                            @RequestParam(name = "date") String date) {

        userInfoService.createUserInfo(new UserInfoForm(Double.parseDouble(distance),
                                                        time,
                                                        date),
                                                        username);
    }

    @DeleteMapping("/users/{username}/delete/{id}")
    public void deleteUserInfo(@PathVariable("username") String username,
                               @PathVariable("id") Long id) {

        userInfoService.deleteUserInfo(id);
    }

    @PutMapping("/users/{username}/update/{id}")
    public void updateInfo(@PathVariable("username") String username,
                           @PathVariable("id") Long id,
                           @RequestBody UserInfoForm infoForm) {

        userInfoService.updateUserInfo(infoForm, id);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> userList = userService.findAll();
        return userList;
    }
}
