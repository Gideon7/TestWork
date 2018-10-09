package com.bsuir.rest.service;

import com.bsuir.rest.entity.UserEntity;
import com.bsuir.rest.model.LoginForm;
import com.bsuir.rest.model.RegisterForm;
import com.bsuir.rest.model.User;
import com.bsuir.rest.transfer.TokenDto;

import java.util.List;

public interface UserService {
    List<User> findAll();
    TokenDto checkUser(LoginForm loginForm);
    boolean registerUser(RegisterForm registerForm);
}
