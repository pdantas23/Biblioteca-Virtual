package com.example.Biblioteca_digital.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LivroDTO {
    @NotBlank(message = "O titulo é obrigatório.")
    private String titulo;

    @NotBlank(message = "O nome do autor é obrigatório.")
    private String autor;

    @NotBlank(message = "O nome da autora é obrigatório.")
    private String editora;

    @NotBlank(message = "O ano de publicação é obrigatório.")
    @Pattern(regexp = "\\d{4}", message = "O ano de publicação deve conter 4 dígitos numéricos.")
    private String anoDePublicacao;

    @NotBlank(message = "O ISBN é obrigatório.")
    @Pattern(regexp = "\\d{13}", message = "O ISBN deve conter exatamente 13 dígitos numéricos.")
    private String isbn;
}
