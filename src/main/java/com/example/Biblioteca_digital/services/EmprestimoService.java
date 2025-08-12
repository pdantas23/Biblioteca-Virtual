package com.example.Biblioteca_digital.services;

import com.example.Biblioteca_digital.enums.EmprestimoStatus;
import com.example.Biblioteca_digital.enums.LivroStatus;
import com.example.Biblioteca_digital.exceptions.DuplicateResourceException;
import com.example.Biblioteca_digital.exceptions.EventNotFoundException;
import com.example.Biblioteca_digital.models.EmprestimoModel;
import com.example.Biblioteca_digital.models.LivroModel;
import com.example.Biblioteca_digital.models.UserModel;
import com.example.Biblioteca_digital.repositories.EmprestimoRepository;
import com.example.Biblioteca_digital.repositories.LivroRepository;
import com.example.Biblioteca_digital.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public EmprestimoModel registrarEmprestimo(String isbn, Long usuarioId) {
        LivroModel livro = livroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EventNotFoundException("Livro não encontrado"));

        if (livro.getStatus() != LivroStatus.DISPONIVEL) {
            throw new DuplicateResourceException("Livro está alugado no momento");
        }

        UserModel usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new EventNotFoundException("Usuário não encontrado"));

        EmprestimoModel emprestimo = new EmprestimoModel(livro, usuario, LocalDate.now(), EmprestimoStatus.EMPRESTADO);

        livro.setStatus(LivroStatus.ALUGADO);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public EmprestimoModel registrarDevolucao(String isbn) {
        EmprestimoModel emprestimo = emprestimoRepository
                .findByLivroIsbnAndStatus(isbn, EmprestimoStatus.EMPRESTADO)
                .orElseThrow(() -> new EventNotFoundException("Empréstimo não encontrado para o livro com ISBN: " + isbn));

        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.setStatus(EmprestimoStatus.DEVOLVIDO);

        LivroModel livro = emprestimo.getLivro();
        livro.setStatus(LivroStatus.DISPONIVEL);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }
}
