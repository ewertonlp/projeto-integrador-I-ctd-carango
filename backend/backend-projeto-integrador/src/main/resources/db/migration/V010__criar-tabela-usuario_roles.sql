CREATE TABLE `usuario_roles` (
  `usuario_id` bigint NOT NULL,
  `roles_id` bigint NOT NULL,
  CONSTRAINT `fk_role` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `fk_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
)