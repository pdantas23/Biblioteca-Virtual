package com.example.Biblioteca_digital.repositories;

import com.example.Biblioteca_digital.enums.EmprestimoStatus;
import com.example.Biblioteca_digital.models.EmprestimoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, Long> {
    Optional<EmprestimoModel> findByLivroIsbnAndStatus(String isbn, EmprestimoStatus status);
}
