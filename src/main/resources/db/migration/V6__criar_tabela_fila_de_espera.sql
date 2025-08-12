CREATE TABLE fila_de_espera (
                                id SERIAL PRIMARY KEY,
                                livro_id BIGINT NOT NULL,
                                usuario_id BIGINT NOT NULL,
                                data_solicitacao DATE NOT NULL,
                                CONSTRAINT fk_livro FOREIGN KEY (livro_id) REFERENCES livros(id),
                                CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);