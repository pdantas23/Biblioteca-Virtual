package com.example.Biblioteca_digital.dtos;

import com.example.Biblioteca_digital.models.LivroModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LivroStatusDTO {
    private LivroDTO livro;

    public LivroStatusDTO(LivroModel livroModel) {
        this.livro = new LivroDTO(livroModel);
    }
}
