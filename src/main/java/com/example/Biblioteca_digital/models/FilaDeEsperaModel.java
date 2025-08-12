package com.example.Biblioteca_digital.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "fila_de_espera")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FilaDeEsperaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private LivroModel livro;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserModel usuario;

    @Column(nullable = false)
    private LocalDate dataSolicitacao;
}
