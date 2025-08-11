package com.example.Biblioteca_digital.controllers;

import com.example.Biblioteca_digital.dtos.*;
import com.example.Biblioteca_digital.models.LivroModel;
import com.example.Biblioteca_digital.models.UserModel;
import com.example.Biblioteca_digital.repositories.UserRepository;
import com.example.Biblioteca_digital.security.CustomUserDetails;
import com.example.Biblioteca_digital.services.LivroService;
import com.example.Biblioteca_digital.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroService livroService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('GERENTE')")
    @PostMapping("/alugar/{isbn}")
    public ResponseEntity<ResponseDTO<LivroDTO>> alugarLivro(@PathVariable String isbn, @AuthenticationPrincipal CustomUserDetails user) {
        LivroModel livro = livroService.alugarLivroPorIsbn(isbn, user.getId());
        LivroDTO livroDTO = new LivroDTO(livro);

        ResponseDTO<LivroDTO> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                livro.getTitulo() + " alugado por " + user.getUsername(),
                HttpStatus.OK,
                livroDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @GetMapping("/gerenciar/{isbn}")
    public ResponseEntity<ResponseDTO<LivroStatusDTO>> gerenciarLivro(@PathVariable String isbn, @AuthenticationPrincipal CustomUserDetails user) {
        LivroModel livro = livroService.gerenciarLivroPorIsbn(isbn);

        LivroStatusDTO livroStatusDTO = new LivroStatusDTO(livro);

        ResponseDTO<LivroStatusDTO> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Livro encontrado com sucesso!",
                HttpStatus.OK,
                livroStatusDTO
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @PostMapping("/cadastro-livros")
    public ResponseEntity<ResponseDTO<LivroModel>> cadastroLivro(@RequestBody @Valid LivroDTO livroDTO) {
        LivroModel livro = new LivroModel();
        BeanUtils.copyProperties(livroDTO, livro);

        LivroModel salvo = livroService.salvarLivro(livro);

        ResponseDTO<LivroModel> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Livro criado com sucesso!",
                HttpStatus.CREATED,
                salvo
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('GERENTE')")
    @GetMapping("/exibir-livros")
    public ResponseEntity<ResponseDTO<List<LivroListagemDTO>>> exibirLivros() {
        List<LivroModel> livros = livroService.listarLivros();

        List<LivroListagemDTO> livrosDTO = livros.stream()
                .map(LivroListagemDTO::new)
                .collect(Collectors.toList());

        ResponseDTO<List<LivroListagemDTO>> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Lista de livros cadastrados exibida com sucesso!",
                HttpStatus.OK,
                livrosDTO
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PreAuthorize("hasRole('GERENTE')")
    @DeleteMapping("/deletar-livros")
    public ResponseEntity<ResponseDTO<?>> deletarTodosOsLivros() {
        livroService.deletarLivros();

        ResponseDTO<?> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Todos os livros foram deletados com sucesso!",
                HttpStatus.OK,
                null
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('GERENTE')")
    @GetMapping("/{isbn}")
    public ResponseEntity<ResponseDTO<LivroModel>> buscarLivroPorIsbn(@PathVariable String isbn) {
        LivroModel livro = livroService.buscarLivroPorIsbn(isbn);


        ResponseDTO<LivroModel> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Livro encontrado com sucesso!",
                HttpStatus.OK,
                livro
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    @DeleteMapping("/deletar/{isbn}")
    public ResponseEntity<ResponseDTO<?>> deletarLivroPorISBN(@PathVariable String isbn) {
        LivroModel livro = livroService.apagarLivroPorIsbn(isbn);

        ResponseDTO<LivroModel> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Livro apagado com sucesso",
                HttpStatus.OK,
                livro
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
