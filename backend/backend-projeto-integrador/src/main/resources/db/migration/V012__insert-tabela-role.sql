INSERT INTO usuario (nome, sobrenome, email, senha) VALUES ('Juriscreudo', 'da Silva', 'admin@admin.com',
'$2a$10$cLBsr5JgT7EjzknGOK8RO.rt5YbwjyUxlxbzA/0jxT.48uLzefw8.');

INSERT INTO role (nome) values  ('ADMIN');
INSERT INTO role (nome) values  ('USER');
INSERT INTO role (nome) values  ('CLIENT');

INSERT INTO usuario_roles (usuario_id, roles_id) VALUES (1, 1);