package com.example.Biblioteca_digital.controllers;

import com.example.Biblioteca_digital.dtos.EmprestimoDTO;
import com.example.Biblioteca_digital.dtos.ResponseDTO;
import com.example.Biblioteca_digital.models.EmprestimoModel;
import com.example.Biblioteca_digital.security.CustomUserDetails;
import com.example.Biblioteca_digital.services.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/emprestimo")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/alugar/{isbn}")
    public ResponseEntity<ResponseDTO<EmprestimoDTO>> alugarLivro(@PathVariable String isbn, @AuthenticationPrincipal CustomUserDetails user) {
        EmprestimoModel emprestimo = emprestimoService.registrarEmprestimo(isbn, user.getId());
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO(emprestimo);

        ResponseDTO<EmprestimoDTO> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Empréstimo feito com sucesso.",
                HttpStatus.OK,
                emprestimoDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @PostMapping("/devolucao/{isbn}")
    public ResponseEntity<ResponseDTO<EmprestimoDTO>> devolverLivro(@PathVariable String isbn, @AuthenticationPrincipal CustomUserDetails user) {
        EmprestimoModel devolvido = emprestimoService.registrarDevolucao(isbn);
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO(devolvido);

        ResponseDTO<EmprestimoDTO> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Livro devolvido com sucesso.",
                HttpStatus.OK,
                emprestimoDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
