ALTER TABLE transacoes ADD COLUMN usuario_id BIGINT NOT NULL;
ALTER TABLE transacoes ADD FOREIGN KEY (usuario_id) REFERENCES usuarios(id);
