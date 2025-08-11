package com.example.Biblioteca_digital.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    @Getter
    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String username, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // ajuste conforme sua lógica
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // ajuste conforme sua lógica
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // ajuste conforme sua lógica
    }

    @Override
    public boolean isEnabled() {
        return true; // ajuste conforme sua lógica
    }
}
