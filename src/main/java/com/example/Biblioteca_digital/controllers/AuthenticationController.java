package com.example.Biblioteca_digital.controllers;

import com.example.Biblioteca_digital.dtos.AuthenticationDTO;
import com.example.Biblioteca_digital.dtos.LoginResponseDTO;
import com.example.Biblioteca_digital.dtos.RegisterDTO;
import com.example.Biblioteca_digital.models.UserModel;
import com.example.Biblioteca_digital.repositories.UserRepository;
import com.example.Biblioteca_digital.security.CustomUserDetails;
import com.example.Biblioteca_digital.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        // auth.getPrincipal() é CustomUserDetails
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        // Gere o token usando o id ou username, dependendo do seu TokenService
        var token = tokenService.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        Optional<UserModel> user = userRepository.findByEmail(data.email());

        if(user.isPresent()) {
            return ResponseEntity.badRequest().body("Usuário já cadastrado");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserModel newUser = new UserModel(data.username(), encryptedPassword, data.email(), data.role());

        userRepository.save(newUser);

        return ResponseEntity.ok().build();

    }
}
