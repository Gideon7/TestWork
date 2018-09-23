package com.bsuir.rest.security.providers;

import com.bsuir.rest.entities.Token;
import com.bsuir.rest.repositories.TokenRepository;
import com.bsuir.rest.security.authentications.TokenAuthentication;
import com.bsuir.rest.security.details.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthentication tokenAuth = (TokenAuthentication) authentication;

        Token token = tokenRepository.findOneByValue(tokenAuth.getName());

        if(token != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(token.getUser().getUsername());
            tokenAuth.setUserDetails(userDetails);
            tokenAuth.setAuthenticated(true);
            return tokenAuth;
        }

        throw new IllegalArgumentException("Token not found");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TokenAuthentication.class.equals(aClass);
    }
}
