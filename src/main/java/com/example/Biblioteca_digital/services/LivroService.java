package com.example.Biblioteca_digital.services;

import com.example.Biblioteca_digital.models.LivroModel;
import com.example.Biblioteca_digital.repositorys.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public LivroModel salvarLivro(LivroModel livro){
        return livroRepository.save(livro);
    }

    public List<LivroModel> listarLivros(){
        return livroRepository.findAll();
    }

    public void deletarLivros(){
        livroRepository.deleteAll();
    }

    public Optional<LivroModel> buscarLivroPorId(Long id){
        return livroRepository.findById(id);
    }

    public void apagarLivroPorId(Long id){
        livroRepository.deleteById(id);
    }
}
