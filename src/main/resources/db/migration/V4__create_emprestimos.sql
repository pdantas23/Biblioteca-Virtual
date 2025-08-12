CREATE TABLE emprestimos (
                             id SERIAL PRIMARY KEY,
                             usuario_id BIGINT NOT NULL,
                             livro_id BIGINT NOT NULL,
                             data_emprestimo DATE NOT NULL,
                             data_devolucao DATE,
                             status VARCHAR(20) NOT NULL,
                             CONSTRAINT fk_emprestimos_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
                             CONSTRAINT fk_emprestimos_livro FOREIGN KEY (livro_id) REFERENCES livro_model(id)
);