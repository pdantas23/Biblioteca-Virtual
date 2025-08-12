package com.example.Biblioteca_digital.services;

import com.example.Biblioteca_digital.dtos.ResultadoCadastroLivrosDTO;
import com.example.Biblioteca_digital.exceptions.DuplicateResourceException;
import com.example.Biblioteca_digital.exceptions.EventNotFoundException;
import com.example.Biblioteca_digital.models.LivroModel;
import com.example.Biblioteca_digital.enums.LivroStatus;
import com.example.Biblioteca_digital.models.UserModel;
import com.example.Biblioteca_digital.repositories.LivroRepository;
import com.example.Biblioteca_digital.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UserRepository userRepository;


    public List<LivroModel> listarLivros(){

        List<LivroModel> livros = livroRepository.findAll();

        if(livros.isEmpty()){
            throw new EventNotFoundException("Nenhum livro cadastrado.");
        }
        return livros;
    }

    public void deletarLivros(){

        if(livroRepository.findAll().isEmpty()){
            throw new EventNotFoundException("Nenhum livro cadastrado.");
        }

        livroRepository.deleteAll();
    }

    public LivroModel buscarLivroPorIsbn(String isbn) {
        return livroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EventNotFoundException("Livro com ISBN " + isbn + " não encontrado."));
    }

    @Transactional
    public LivroModel apagarLivroPorIsbn(String isbn) {
        LivroModel livro = livroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EventNotFoundException("Livro com ISBN " + isbn + " não encontrado."));

        livroRepository.delete(livro);

        return livro;
    }

    public LivroModel gerenciarLivroPorIsbn(String isbn) {
        LivroModel livro = buscarLivroPorIsbn(isbn);
        return livro;
    }

    public ResultadoCadastroLivrosDTO salvarTodosComIgnorados(List<LivroModel> livros) {
        List<LivroModel> livrosParaSalvar = new ArrayList<>();
        List<LivroModel> livrosIgnorados = new ArrayList<>();

        for (LivroModel livro : livros) {
            Optional<LivroModel> existente = livroRepository.findByIsbn(livro.getIsbn());

            if (existente.isEmpty()) {
                livrosParaSalvar.add(livro);
            } else {
                livrosIgnorados.add(livro);
            }
        }

        List<LivroModel> livrosSalvos = livroRepository.saveAll(livrosParaSalvar);

        return new ResultadoCadastroLivrosDTO(livrosSalvos, livrosIgnorados);
    }
}
