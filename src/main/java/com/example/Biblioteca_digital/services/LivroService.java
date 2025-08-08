package com.example.Biblioteca_digital.services;

import com.example.Biblioteca_digital.exceptions.DuplicateResourceException;
import com.example.Biblioteca_digital.exceptions.EventNotFoundException;
import com.example.Biblioteca_digital.models.LivroModel;
import com.example.Biblioteca_digital.repositorys.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public LivroModel salvarLivro(LivroModel livro){
        Optional<LivroModel> livroExistente = livroRepository.findByIsbn(livro.getIsbn());

        if(livroExistente.isPresent()){
            throw new DuplicateResourceException("Livro com ISBN " + livro.getIsbn() + " já cadastrado.");
        }

        return livroRepository.save(livro);
    }

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

    public void apagarLivroPorIsbn(String isbn) {
        LivroModel livro = buscarLivroPorIsbn(isbn);

        livroRepository.deleteByIsbn(isbn);
    }
}
