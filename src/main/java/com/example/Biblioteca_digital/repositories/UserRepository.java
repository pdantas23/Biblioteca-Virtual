package com.example.Biblioteca_digital.repositories;

import com.example.Biblioteca_digital.models.UserModel;
import com.example.Biblioteca_digital.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.BitSet;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByEmail(String email);
    boolean existsByRole(UserRole role);
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findById(Long id);
}
