package com.example.Biblioteca_digital.services;

import com.example.Biblioteca_digital.exceptions.DuplicateResourceException;
import com.example.Biblioteca_digital.exceptions.EventNotFoundException;
import com.example.Biblioteca_digital.models.UserModel;
import com.example.Biblioteca_digital.enums.UserRole;
import com.example.Biblioteca_digital.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> listarUsuarios(String mensagemSeVazio) {
        List<UserModel> usuarios = userRepository.findAll();
        if (usuarios.isEmpty()) {
            throw new EventNotFoundException(mensagemSeVazio);
        }
        return usuarios;
    }

    public Optional<UserModel> save(UserModel usuario) {
        if (userRepository.existsByEmail(usuario.getEmail())) {
            return Optional.empty();
        }

        if (usuario.getRole() == UserRole.GERENTE) {
            boolean gerenteExiste = userRepository.existsByRole(UserRole.GERENTE);
            if (gerenteExiste) {
                throw new DuplicateResourceException("Já existe um usuário com o papel GERENTE.");
            }
        }

        return Optional.of(userRepository.save(usuario));
    }

    public void deleteAll() {
        List<UserModel> usuarios = listarUsuarios("Não há usuários cadastrados para exclusão.");
        userRepository.deleteAll(usuarios);
    }

    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EventNotFoundException("Usuário com Email " + email + " não encontrado."));
    }

    public UserModel findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Usuário com ID " + id + " não encontrado."));
    }

    public UserModel alterarRole(Long id, String novaRoleStr) {
        UserModel user = findById(id);

        if (user.getRole() == UserRole.GERENTE) {
            throw new IllegalArgumentException("Não é permitido alterar a role de um GERENTE.");
        }

        UserRole novaRole;
        try {
            novaRole = UserRole.valueOf(novaRoleStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Role inválida. Use: USER, ADMIN ou GERENTE.");
        }

        if (user.getRole() == novaRole) {
            throw new IllegalArgumentException("Usuário já possui essa role.");
        }

        user.setRole(novaRole);
        userRepository.save(user);

        return user;
    }
}

