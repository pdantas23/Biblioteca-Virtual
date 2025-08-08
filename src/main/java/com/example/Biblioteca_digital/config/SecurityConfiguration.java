package com.example.Biblioteca_digital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) //Desabilita CSRF
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) //Libera tudo
                .formLogin(form -> form.disable()) //Desabilita o formulário de login
                .httpBasic(httpBasic -> httpBasic.disable()); //Desabilita autenticação http basic

            return http.build();
    }
}
