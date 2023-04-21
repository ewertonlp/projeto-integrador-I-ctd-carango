package com.backendprojetointegrador.lajeDev.service;


import com.backendprojetointegrador.lajeDev.domain.model.Categoria;
import com.backendprojetointegrador.lajeDev.domain.service.CategoriaService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoriaServiceTest {

    @Autowired
    CategoriaService categoriaService;
    Categoria categoria;
    Categoria categoriaSalvo;

    @BeforeAll
    void arrageCategoria() {
        categoria = new Categoria();
        categoria.setQualificacao("SUV");
        categoria.setDescricao("veículo de alto padrão, por isso maior e com características mais sofisticadas em" +
                "comparação com um carro sedan");
        categoria.setUrlImagem("https://img.olhardigital.com.br/wp-content/uploads/2022/06/polestar3-1.jpg");
        categoriaSalvo = categoriaService.criarCategoria(categoria);
    }

    @Test
    @Order(1)
    void checkCreationCategoriaTest() {
        assertNotNull(categoriaSalvo.getId());
        assertEquals(categoria.getQualificacao(), categoriaSalvo.getQualificacao());
        assertEquals(categoria.getDescricao(), categoriaSalvo.getDescricao());
        assertEquals(categoria.getUrlImagem(), categoriaSalvo.getUrlImagem());
    }

    @Test
    @Order(2)
    void searchCategoriaByIdTest() {
        Categoria categoriaBuscada;
        categoriaBuscada = categoriaService.buscarCategoria(categoriaSalvo.getId());
        assertNotNull(categoriaBuscada);
    }

    @Test
    @Order(3)
    void updateCategoriaTest() {
        categoria.setQualificacao("Sedan");
        categoria.setDescricao("veículo utilitário com quatro portas, maior espaço interno que um carro hatch, " +
                "e bagageiro espaçoso");
        categoria.setUrlImagem("https://uploads-ssl.webflow.com/5de0424183c9d7b00dd43bca/5f9071bf9bbc9f70d25745af_carros-sedan-mais-vendidos.jpg");
        categoria.setId(categoriaSalvo.getId());
        categoriaSalvo = categoriaService.criarCategoria(categoria);
        assertNotNull(categoriaSalvo.getId());
        assertEquals(categoria.getQualificacao(), categoriaSalvo.getQualificacao());
        assertEquals(categoria.getDescricao(), categoriaSalvo.getDescricao());
        assertEquals(categoria.getUrlImagem(), categoriaSalvo.getUrlImagem());
    }

    @Test
    @Order(4)
    void listarTodasCategoriasTest() {
        Categoria categoriaTemp = new Categoria();
        categoriaTemp.setQualificacao("Conversivel");
        categoriaTemp.setDescricao("veículo esportivo, com estilo agressivo, tem menor espaço interno em comparação com " +
                "uma SUV, mas tem um motor mais potente e melhor desempenho em retas");
        categoriaTemp.setUrlImagem("https://blog.catarinacarros.com.br/wp-content/uploads/2020/02/bmw-zseries-z4-conversivel-1024x576.jpg");
        categoriaService.criarCategoria(categoriaTemp);

        assertEquals(2, categoriaService.listarCategorias().size());
        categoriaService.excluirCategoria(categoriaTemp.getId());
    }

    @Test
    @Order(5)
    public void deletarCategoriaByIdTest() {
        categoriaService.excluirCategoria(categoriaSalvo.getId());
        assertFalse(categoriaService.existeCategoria(categoriaSalvo.getId()));

        assertEquals(0, categoriaService.listarCategorias().size());
    }
}
