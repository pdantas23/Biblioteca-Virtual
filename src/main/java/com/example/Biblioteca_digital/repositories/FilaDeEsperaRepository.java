package com.example.Biblioteca_digital.repositories;

import com.example.Biblioteca_digital.models.FilaDeEsperaModel;
import com.example.Biblioteca_digital.models.LivroModel;
import com.example.Biblioteca_digital.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilaDeEsperaRepository extends JpaRepository<FilaDeEsperaModel, Long> {
    boolean existsByLivroAndUsuario(LivroModel livro, UserModel usuario);

    List<FilaDeEsperaModel> findByLivroOrderByDataSolicitacaoAsc(LivroModel livro);

    Optional<FilaDeEsperaModel> findFirstByLivroOrderByDataSolicitacaoAsc(LivroModel livro);
}

