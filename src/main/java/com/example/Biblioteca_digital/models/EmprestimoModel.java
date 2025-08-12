package com.example.Biblioteca_digital.models;

import com.example.Biblioteca_digital.enums.EmprestimoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "emprestimos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private UserModel user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "livro_id")
    private LivroModel livro;

    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataDevolucao;

    @Enumerated(EnumType.STRING)
    private EmprestimoStatus status;


    public EmprestimoModel(LivroModel livro, UserModel usuario, LocalDateTime dataEmprestimo, EmprestimoStatus status) {
        this.livro = livro;
        this.user = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.status = status;
    }

    public void setUsuario(UserModel user) {
        this.user = user;
    }
}
