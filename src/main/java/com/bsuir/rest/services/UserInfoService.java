package com.bsuir.rest.services;

import com.bsuir.rest.entities.UserInfo;
import com.bsuir.rest.forms.UserInfoForm;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserInfoService {
    public List<UserInfo> findAllByUsername(String username);
    public void createUserInfo(UserInfoForm userInfoForm, String Username);
    public void deleteUserInfo(Long id);
    public void updateUserInfo(UserInfoForm userInfoForm, Long id);
}
