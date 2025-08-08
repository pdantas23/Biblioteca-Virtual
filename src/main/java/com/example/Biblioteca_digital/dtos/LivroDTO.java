package com.example.Biblioteca_digital.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LivroDTO {
    @NotBlank
    private String titulo;

    @NotBlank
    private String autor;

    @NotBlank
    private String editora;

    @NotBlank
    private String anoDePublicacao;

    @NotBlank
    private String isbn;
}
