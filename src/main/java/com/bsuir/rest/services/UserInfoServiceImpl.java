package com.bsuir.rest.services;

import com.bsuir.rest.entities.User;
import com.bsuir.rest.entities.UserInfo;
import com.bsuir.rest.forms.UserInfoForm;
import com.bsuir.rest.repositories.UserInfoRepository;
import com.bsuir.rest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserInfo> findAllByUsername(String username) {
        List<UserInfo> resultList = userInfoRepository.findAllByUser_Username(username);
        return resultList;
    }

    public void createUserInfo(UserInfoForm userInfoForm, String username) {
        User user = userRepository.findOneByUsername(username);
        UserInfo userInfo = UserInfo.builder()
                                    .user(user)
                                    .distance(userInfoForm.getDistance())
                                    .time(userInfoForm.getTime())
                                    .date(userInfoForm.getDate())
                                    .build();

        userInfoRepository.save(userInfo);
    }

    @Override
    public void deleteUserInfo(Long id) {
        userInfoRepository.deleteOneById(id);
    }

    @Override
    public void updateUserInfo(UserInfoForm userInfoForm, Long id) {
        UserInfo userInfo = userInfoRepository.findOneById(id);

        if(userInfo == null) throw new IllegalStateException("User info not found");

        if(userInfoForm.getDistance() != userInfo.getDistance()) userInfo.setDistance(userInfoForm.getDistance());
        if(userInfoForm.getDate() != userInfo.getDate()) userInfo.setDate(userInfoForm.getDate());
        if(userInfoForm.getTime() != userInfo.getTime()) userInfo.setTime(userInfoForm.getTime());

        userInfoRepository.save(userInfo);
    }
}
