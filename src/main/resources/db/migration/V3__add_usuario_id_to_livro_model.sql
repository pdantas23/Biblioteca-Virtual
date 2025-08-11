ALTER TABLE livro_model
    ADD COLUMN usuario_id BIGINT;

ALTER TABLE livro_model
    ADD CONSTRAINT fk_usuario_livro
        FOREIGN KEY (usuario_id) REFERENCES usuarios(id);
