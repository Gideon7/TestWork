package com.bsuir.rest.services;

import com.bsuir.rest.entities.User;
import com.bsuir.rest.forms.UserForm;
import com.bsuir.rest.transfer.TokenDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public List<User> findAll();
    public TokenDto checkUser(UserForm userForm);
    public boolean registerUser(UserForm userForm);
}
