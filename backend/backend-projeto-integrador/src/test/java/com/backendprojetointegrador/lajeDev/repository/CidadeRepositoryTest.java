package com.backendprojetointegrador.lajeDev.repository;

import com.backendprojetointegrador.lajeDev.domain.model.Cidade;
import com.backendprojetointegrador.lajeDev.domain.repository.ICidadeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CidadeRepositoryTest {

    @Autowired
    private ICidadeRepository cidadeRepository;
    private Cidade cidade;
    private Cidade cidadeSave;

    @BeforeAll
    void arrangeCidade() {
        cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.setPais("Brasil");
        cidadeSave = cidadeRepository.save(cidade);
    }

    @Test
    @Order(1)
    void searchCidadeByIdTest() {
        assertTrue(cidadeRepository.findById(cidadeSave.getId()).isPresent());
    }


    @Test
    @Order(2)
    void updateCidadeTest() {
        cidade.setNome("Rio de Janeiro");
        cidade.setPais("Paraguai");
        cidade.setId(cidadeSave.getId());
        cidadeSave = cidadeRepository.save(cidade);
        assertNotNull(cidadeSave.getId());
        assertEquals(1, cidadeSave.getId());
        assertEquals("Rio de Janeiro", cidadeSave.getNome());
        assertEquals("Paraguai", cidadeSave.getPais());
    }

    @Test
    @Order(3)
    void listCidadeTest() {
        assertEquals(1, cidadeRepository.findAll().size());
    }

    @Test
    @Order(4)
    void deleteCidadeTest() {
        cidadeRepository.deleteById(cidadeSave.getId());
        assertFalse(cidadeRepository.existsById(cidadeSave.getId()));
    }
}
