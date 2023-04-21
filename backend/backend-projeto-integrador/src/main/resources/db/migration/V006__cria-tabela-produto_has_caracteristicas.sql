CREATE TABLE `produto_has_caracteristicas` (
  `produto_id` bigint NOT NULL,
  `caracteristica_id` bigint NOT NULL,
  CONSTRAINT `fk_produto_has_caracteristicas` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`),
  CONSTRAINT `fk_caracteristica` FOREIGN KEY (`caracteristica_id`) REFERENCES `caracteristica` (`id`)
);