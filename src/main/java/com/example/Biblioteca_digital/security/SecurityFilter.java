package com.example.Biblioteca_digital.security;

import com.example.Biblioteca_digital.exceptions.EventNotFoundException;
import com.example.Biblioteca_digital.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;


    public SecurityFilter(TokenService tokenService, @Qualifier("customUserDetailsService") UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Request path: " + request.getServletPath());
        System.out.println("Auth: " + SecurityContextHolder.getContext().getAuthentication());

        var token = this.recoverToken(request);

        System.out.println("Authorization Header: " + request.getHeader("Authorization"));
        System.out.println("Token extraído: " + token);

        if (token != null) {
            var email = tokenService.validateToken(token);
            System.out.println("Email extraído do token: " + email);

            UserDetails user = userDetailsService.loadUserByUsername(email);
            System.out.println("Usuário autenticado: " + user.getUsername());

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Authentication set: " + SecurityContextHolder.getContext().getAuthentication());
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken (HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
