package com.bsuir.rest.service;

import com.bsuir.rest.entity.TokenEntity;
import com.bsuir.rest.entity.UserEntity;
import com.bsuir.rest.exception.AlreadyExistException;
import com.bsuir.rest.model.LoginForm;
import com.bsuir.rest.model.Role;
import com.bsuir.rest.model.RegisterForm;
import com.bsuir.rest.model.User;
import com.bsuir.rest.repository.TokenRepository;
import com.bsuir.rest.repository.UserRepository;
import com.bsuir.rest.transfer.TokenDto;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public List<User> findAll() {
        return User.from(userRepository.findAll());
    }

    @Override
    public TokenDto checkUser(LoginForm loginForm) {

        UserEntity userEntity = userRepository.findOneByUsername(loginForm.getUsername());

        if(userEntity != null && passwordEncoder.matches(loginForm.getPassword(), userEntity.getHashPassword())) {
            TokenEntity tokenEntity = TokenEntity.builder()
                                .userEntity(userEntity)
                                .value(RandomStringUtils.random(10, true, true))
                                .build();

            tokenRepository.save(tokenEntity);
            return new TokenDto(tokenEntity.getValue());
        }

        return null;
    }

    @Override
    @PreAuthorize("@securityUtil.isAdmin() or @securityUtil.isUserRole(#registerForm)")
    public boolean registerUser(RegisterForm registerForm) {

        UserEntity userEntity = userRepository.findOneByUsername(registerForm.getUsername());

        if(userEntity == null) {
            String hash = passwordEncoder.encode(registerForm.getPassword());
            userEntity = UserEntity.builder()
                    .username(registerForm.getUsername())
                    .hashPassword(hash)
                    .role(Role.valueOf(registerForm.getRole()))
                    .build();

            userRepository.save(userEntity);
            return true;
        }
        throw new AlreadyExistException();
    }
}
