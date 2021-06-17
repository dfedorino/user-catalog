package com.dfedorino.user_catalog.repository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    private final User user;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // only one role per user so far
        String roleAuthority = user.getAuthority().getRole();
        return List.of(new SimpleGrantedAuthority(roleAuthority));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    // TODO: override properly!
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // TODO: override properly!
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // TODO: override properly!
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // TODO: override properly!
    @Override
    public boolean isEnabled() {
        return true;
    }
}
