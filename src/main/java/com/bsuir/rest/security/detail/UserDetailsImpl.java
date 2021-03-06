package com.bsuir.rest.security.detail;

import com.bsuir.rest.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private UserEntity userEntity;

    public UserDetailsImpl(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(userEntity.getRole().toString());
        return Collections.singletonList(auth);
    }

    @Override
    public String getPassword() {
        return userEntity.getHashPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    public Long getId() { return userEntity.getId(); }

    public String getRole() { return userEntity.getRole().toString(); }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
