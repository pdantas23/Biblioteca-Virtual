package com.example.Biblioteca_digital.dtos;

import com.example.Biblioteca_digital.enums.LivroStatus;
import com.example.Biblioteca_digital.models.LivroModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LivroListagemDTO {
    private String titulo;
    private String autor;
    private String anoDePublicacao;
    private String isbn;
    private LivroStatus status;

    public LivroListagemDTO(LivroModel livro) {
        this.titulo = livro.getTitulo();
        this.autor = livro.getAutor();
        this.anoDePublicacao = livro.getAnoDePublicacao();
        this.isbn = livro.getIsbn();
        this.status = livro.getStatus();
    }
}
