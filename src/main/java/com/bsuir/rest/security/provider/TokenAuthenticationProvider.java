package com.bsuir.rest.security.provider;

import com.bsuir.rest.entity.TokenEntity;
import com.bsuir.rest.exception.NotFoundException;
import com.bsuir.rest.repository.TokenRepository;
import com.bsuir.rest.security.authentication.TokenAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthentication tokenAuth = (TokenAuthentication) authentication;

        TokenEntity tokenEntity = tokenRepository.findOneByValue(tokenAuth.getName());

        if(tokenEntity == null) {
            throw new NotFoundException();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenEntity.getUserEntity().getUsername());
        tokenAuth.setUserDetails(userDetails);
        tokenAuth.setAuthenticated(true);
        return tokenAuth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TokenAuthentication.class.equals(aClass);
    }
}
