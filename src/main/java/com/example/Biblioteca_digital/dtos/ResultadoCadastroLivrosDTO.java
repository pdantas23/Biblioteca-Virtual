package com.example.Biblioteca_digital.dtos;

import com.example.Biblioteca_digital.models.LivroModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ResultadoCadastroLivrosDTO {
    private List<LivroModel> livrosSalvos;
    private List<LivroModel> livrosIgnorados;

    public ResultadoCadastroLivrosDTO(List<LivroModel> livrosSalvos, List<LivroModel> livrosIgnorados) {
        this.livrosSalvos = livrosSalvos;
        this.livrosIgnorados = livrosIgnorados;
    }

    public List<LivroModel> getLivrosSalvos() {
        return livrosSalvos;
    }

    public List<LivroModel> getLivrosIgnorados() {
        return livrosIgnorados;
    }
}
