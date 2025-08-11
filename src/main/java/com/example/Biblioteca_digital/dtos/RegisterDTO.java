package com.example.Biblioteca_digital.dtos;

import com.example.Biblioteca_digital.enums.UserRole;

public record RegisterDTO(String username, String password, String email, UserRole role) {}
