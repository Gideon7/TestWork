package com.bsuir.rest.services;

import com.bsuir.rest.entities.Token;
import com.bsuir.rest.entities.User;
import com.bsuir.rest.forms.UserForm;
import com.bsuir.rest.repositories.TokenRepository;
import com.bsuir.rest.repositories.UserRepository;
import com.bsuir.rest.transfer.TokenDto;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        return userRepository.findAll();
    }

    @Override
    public TokenDto checkUser(UserForm userForm) {
        User user = userRepository.findOneByUsername(userForm.getUsername());

        if(user != null && passwordEncoder.matches(userForm.getPassword(), user.getHashPassword())) {
            Token token = Token.builder()
                                .user(user)
                                .value(RandomStringUtils.random(10, true, true))
                                .build();

            tokenRepository.save(token);
            return new TokenDto(token.getValue());
        }

        return null;
    }

    @Override
    public boolean registerUser(UserForm userForm) {
        User user = userRepository.findOneByUsername(userForm.getUsername());
        if(user != null) return false;
        else {
            String hash = passwordEncoder.encode(userForm.getPassword());

            user = User.builder()
                    .username(userForm.getUsername())
                    .hashPassword(hash)
                    .build();

            userRepository.save(user);
            return true;
        }
    }
}
