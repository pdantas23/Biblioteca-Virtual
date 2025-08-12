package com.example.Biblioteca_digital.models;

import com.example.Biblioteca_digital.enums.LivroStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "livros")
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

    @NotNull
    @Column(unique = true, nullable = false)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LivroStatus status = LivroStatus.DISPONIVEL;

}
