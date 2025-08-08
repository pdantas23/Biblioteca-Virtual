package com.example.Biblioteca_digital.controllers;

import com.example.Biblioteca_digital.dtos.LivroDTO;
import com.example.Biblioteca_digital.dtos.ResponseDTO;
import com.example.Biblioteca_digital.models.LivroModel;
import com.example.Biblioteca_digital.services.LivroService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroService livroService;

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

    @GetMapping("/exibir-livros")
    public ResponseEntity<ResponseDTO<List<LivroModel>>> exibirLivros() {
        List<LivroModel> livros = livroService.listarLivros();

        ResponseDTO<List<LivroModel>> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Lista de livros cadastrados exibida com sucesso!",
                HttpStatus.OK,
                livros
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/deletar-livros")
    public ResponseEntity<ResponseDTO<?>> deletarLivros() {
        livroService.deletarLivros();

        ResponseDTO<?> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Todos os livros foram deletados com sucesso!",
                HttpStatus.OK,
                null
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

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

    @DeleteMapping("/{isbn}")
    public ResponseEntity<ResponseDTO<?>> deletarLivroPorId(@PathVariable String isbn) {
        LivroModel livro = livroService.buscarLivroPorIsbn(isbn);

        livroService.apagarLivroPorIsbn(isbn);

        ResponseDTO<LivroModel> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Livro apagado com sucesso",
                HttpStatus.OK,
                livro
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
