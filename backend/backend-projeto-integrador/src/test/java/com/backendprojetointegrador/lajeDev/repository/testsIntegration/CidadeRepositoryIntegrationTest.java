package com.backendprojetointegrador.lajeDev.repository.testsIntegration;

import com.backendprojetointegrador.lajeDev.domain.model.Cidade;
import com.backendprojetointegrador.lajeDev.domain.repository.ICidadeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CidadeRepositoryIntegrationTest {

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
    void checkCreationCidadeTest() {
        assertNotNull(cidadeSave.getId());
        assertEquals(cidade.getNome(), cidadeSave.getNome());
        assertEquals(cidade.getPais(), cidadeSave.getPais());
    }
}
