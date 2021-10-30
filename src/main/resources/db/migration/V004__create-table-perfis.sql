CREATE TABLE perfis (
  id bigint PRIMARY KEY AUTO_INCREMENT,
  nome varchar(100) not null
);

CREATE TABLE perfis_usuarios (
  usuario_id bigint not null,
  perfil_id bigint not null,

  primary key(usuario_id, perfil_id),
  foreign key(usuario_id) references usuarios(id),
  foreign key(perfil_id) references perfis(id)
);

INSERT INTO perfis (nome) VALUES ('ROLE_ADMIN');
INSERT INTO perfis (nome) VALUES ('ROLE_USER');
