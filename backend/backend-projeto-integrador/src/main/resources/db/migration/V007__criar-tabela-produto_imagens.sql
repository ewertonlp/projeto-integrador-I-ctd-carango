CREATE TABLE `produto_imagens` (
  `produto_id` bigint NOT NULL,
  `imagens_id` bigint NOT NULL,
  CONSTRAINT `fk_imagem` FOREIGN KEY (`imagens_id`) REFERENCES `imagem` (`id`),
  CONSTRAINT `fk_produto` FOREIGN KEY (`produto_id`) REFERENCES `produto` (`id`)
);