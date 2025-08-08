package com.example.Biblioteca_digital.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = false, nullable = false)
    private String titulo;

    @NotBlank
    @Column(unique = false, nullable = false)
    private String autor;

    @NotBlank
    @Column(unique = false, nullable = false)
    private String editora;

    @NotBlank
    @Column(unique = false, nullable = false)
    private String anoDePublicacao;

    @NotBlank
    @Column(unique = false, nullable = false)
    private String isbn;
}
