package com.backendprojetointegrador.lajeDev.service;


import com.backendprojetointegrador.lajeDev.domain.model.Cidade;
import com.backendprojetointegrador.lajeDev.domain.service.CidadeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CidadeServiceTest {

    @Autowired
    CidadeService cidadeService;
    Cidade cidade;
    Cidade cidadeSave;

    @BeforeAll
    void arrangeCidade() {
        cidade = new Cidade();
        cidade.setNome("Salvador");
        cidade.setPais("Nepal");
        cidadeSave = cidadeService.criarCidade(cidade);
    }

    @Test
    @Order(1)
    void checkCreationCidadeTest() {
        assertNotNull(cidadeSave.getId());
        assertEquals(cidade.getNome(), cidadeSave.getNome());
        assertEquals(cidade.getPais(), cidadeSave.getPais());
    }

    @Test
    @Order(2)
    void searchCidadeByIdTest() {
        assertNotNull(cidadeService.buscarCidadeById(cidadeSave.getId()));
    }

    @Test
    @Order(3)
    void updateCidadeTest() {
        cidade.setNome("Recife");
        cidade.setPais("Portugal");
        cidade.setId(cidadeSave.getId());
        cidadeSave = cidadeService.criarCidade(cidade);
        assertNotNull(cidadeSave.getId());
        assertEquals(1, cidadeSave.getId());
        assertEquals(cidade.getNome(), cidadeSave.getNome());
        assertEquals(cidade.getPais(), cidadeSave.getPais());
    }

    @Test
    @Order(4)
    void listTodasCidadesTest() {
        assertEquals(1, cidadeService.listarCidade().size());
    }

    @Test
    @Order(5)
    void deleteCidadeTest() {
        cidadeService.excluirCidadeById(cidadeSave.getId());
        assertFalse(cidadeService.existeCidadeById(cidadeSave.getId()));
    }
}