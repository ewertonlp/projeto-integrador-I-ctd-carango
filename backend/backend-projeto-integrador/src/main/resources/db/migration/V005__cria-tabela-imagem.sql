CREATE TABLE `imagem` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `content_length` bigint NOT NULL,
    `content_type` varchar(255) NOT NULL,
    `created_at` datetime(6) NOT NULL,
    `name` varchar(60) NOT NULL,
    `url` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
  )