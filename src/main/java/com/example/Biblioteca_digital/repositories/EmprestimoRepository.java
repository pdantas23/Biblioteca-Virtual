package com.example.Biblioteca_digital.repositories;

import com.example.Biblioteca_digital.enums.EmprestimoStatus;
import com.example.Biblioteca_digital.models.EmprestimoModel;
import com.example.Biblioteca_digital.models.LivroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, Long> {
    Optional<EmprestimoModel> findByLivroAndDataDevolucaoIsNull(LivroModel livro);
}
