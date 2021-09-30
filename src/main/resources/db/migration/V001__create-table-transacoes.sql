CREATE TABLE transacoes (
  id bigint PRIMARY KEY AUTO_INCREMENT,
  ticker varchar(6) NOT NULL,
  preco decimal(18,2) NOT NULL,
  quantidade int NOT NULL,
  tipo varchar(100) NOT NULL,
  data date NOT NULL
);
