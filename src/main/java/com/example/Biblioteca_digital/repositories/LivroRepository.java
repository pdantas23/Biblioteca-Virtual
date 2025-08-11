package com.example.Biblioteca_digital.repositories;

import com.example.Biblioteca_digital.models.LivroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<LivroModel, Long> {
    Optional<LivroModel> findByIsbn(String isbn);
    LivroModel deleteByIsbn(String isbn);


}
