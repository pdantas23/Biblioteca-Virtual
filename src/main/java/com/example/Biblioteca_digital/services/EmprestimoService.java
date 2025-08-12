package com.example.Biblioteca_digital.services;

import com.example.Biblioteca_digital.enums.EmprestimoStatus;
import com.example.Biblioteca_digital.enums.LivroStatus;
import com.example.Biblioteca_digital.exceptions.DuplicateResourceException;
import com.example.Biblioteca_digital.exceptions.EventNotFoundException;
import com.example.Biblioteca_digital.models.EmprestimoModel;
import com.example.Biblioteca_digital.models.FilaDeEsperaModel;
import com.example.Biblioteca_digital.models.LivroModel;
import com.example.Biblioteca_digital.models.UserModel;
import com.example.Biblioteca_digital.repositories.EmprestimoRepository;
import com.example.Biblioteca_digital.repositories.FilaDeEsperaRepository;
import com.example.Biblioteca_digital.repositories.LivroRepository;
import com.example.Biblioteca_digital.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilaDeEsperaRepository filaDeEsperaRepository;

    @Transactional
    public EmprestimoModel registrarEmprestimo(String isbn, Long usuarioId) {
        LivroModel livro = livroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EventNotFoundException("Livro não encontrado"));

        UserModel usuario = userRepository.findById(usuarioId)
                .orElseThrow(() -> new EventNotFoundException("Usuário não encontrado"));

        if (livro.getStatus() == LivroStatus.DISPONIVEL) {
            livro.setStatus(LivroStatus.ALUGADO);
            livroRepository.save(livro);

            EmprestimoModel emprestimo = new EmprestimoModel(
                    livro, usuario, LocalDateTime.now(), EmprestimoStatus.EMPRESTADO
            );
            return emprestimoRepository.save(emprestimo);
        }
        adicionarNaFilaDeEspera(livro, usuario);

        return null;
    }

    public void adicionarNaFilaDeEspera(LivroModel livro, UserModel usuario) {
        boolean jaNaFila = filaDeEsperaRepository.existsByLivroAndUsuario(livro, usuario);

        if (!jaNaFila) {
            FilaDeEsperaModel fila = new FilaDeEsperaModel();
            fila.setLivro(livro);
            fila.setUsuario(usuario);
            fila.setDataSolicitacao(LocalDate.now());
            filaDeEsperaRepository.save(fila);
        }
    }

    @Transactional
    public EmprestimoModel registrarDevolucao(String isbn) {
        LivroModel livro = livroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EventNotFoundException("Livro não encontrado"));

        if (livro.getStatus() != LivroStatus.ALUGADO) {
            throw new EventNotFoundException("Não é possível devolver um livro que não está alugado.");
        }

        EmprestimoModel emprestimoAtivo = emprestimoRepository
                .findByLivroAndDataDevolucaoIsNull(livro)
                .orElseThrow(() -> new EventNotFoundException("Empréstimo ativo não encontrado"));

        emprestimoAtivo.setStatus(EmprestimoStatus.DEVOLVIDO);
        emprestimoAtivo.setDataDevolucao(LocalDateTime.now());
        emprestimoRepository.save(emprestimoAtivo);


        livro.setStatus(LivroStatus.DISPONIVEL);
        livroRepository.save(livro);

        return emprestimoAtivo;
    }


    @Transactional
    public EmprestimoModel moverFila(String isbn) {
        LivroModel livro = livroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EventNotFoundException("Livro não encontrado"));

        if (livro.getStatus() != LivroStatus.DISPONIVEL) {
            throw new EventNotFoundException("Livro não está disponível para empréstimo.");
        }

        Optional<FilaDeEsperaModel> primeiroDaFila = filaDeEsperaRepository
                .findFirstByLivroOrderByDataSolicitacaoAsc(livro);

        if (primeiroDaFila.isEmpty()) {
            throw new EventNotFoundException("Nenhum usuário na fila para este livro.");
        }

        FilaDeEsperaModel entradaFila = primeiroDaFila.get();
        UserModel usuario = entradaFila.getUsuario();

        EmprestimoModel novoEmprestimo = new EmprestimoModel(livro, usuario, LocalDateTime.now(), EmprestimoStatus.EMPRESTADO);
        emprestimoRepository.save(novoEmprestimo);

        livro.setStatus(LivroStatus.ALUGADO);
        livroRepository.save(livro);

        filaDeEsperaRepository.delete(entradaFila);

        return novoEmprestimo;
    }
}
