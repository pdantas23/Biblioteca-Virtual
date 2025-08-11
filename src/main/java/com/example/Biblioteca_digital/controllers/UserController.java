package com.example.Biblioteca_digital.controllers;

import com.example.Biblioteca_digital.dtos.ResponseDTO;
import com.example.Biblioteca_digital.dtos.RoleChangeDTO;
import com.example.Biblioteca_digital.dtos.UserResponseDTO;
import com.example.Biblioteca_digital.exceptions.EventNotFoundException;
import com.example.Biblioteca_digital.models.UserModel;
import com.example.Biblioteca_digital.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/cadastro")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> cadastrarUsuario(@RequestBody UserModel user) {
        userService.save(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(user, userResponseDTO);

        ResponseDTO<UserResponseDTO> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Usuário cadastrado com sucesso!",
                HttpStatus.CREATED,
                userResponseDTO
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> findByEmail(@PathVariable String email) {
       UserModel user = userService.findByEmail(email);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(user, userResponseDTO);

        ResponseDTO<UserResponseDTO> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Usuário encontrado",
                HttpStatus.OK,
                userResponseDTO
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping("/admins/{id}")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> cadastrarAdmin(@PathVariable Long id, @RequestBody @Valid RoleChangeDTO roleChangeDTO) {
        UserModel user = userService.alterarRole(id, roleChangeDTO.getRole());

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        BeanUtils.copyProperties(user, userResponseDTO);

        ResponseDTO<UserResponseDTO> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Role do usuário com ID " + id + " atualizada. Nova Role: " + user.getRole(),
                HttpStatus.OK,
                userResponseDTO
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
