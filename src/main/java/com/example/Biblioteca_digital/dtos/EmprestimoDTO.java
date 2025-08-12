package com.example.Biblioteca_digital.dtos;

import com.example.Biblioteca_digital.models.EmprestimoModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmprestimoDTO {
    private String titulo;
    private String autor;
    private String anoDePublicacao;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    public EmprestimoDTO(EmprestimoModel emprestimo) {
        this.titulo = emprestimo.getLivro().getTitulo();
        this.autor = emprestimo.getLivro().getAutor();
        this.anoDePublicacao = emprestimo.getLivro().getAnoDePublicacao();
        this.dataEmprestimo = emprestimo.getDataEmprestimo();
        this.dataDevolucao = emprestimo.getDataDevolucao();
    }
}
