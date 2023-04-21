package com.backendprojetointegrador.lajeDev.model;

import com.backendprojetointegrador.lajeDev.domain.model.Cidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class CidadeTest {

    private Cidade cidade;

    @BeforeEach
    void arrangeCidade() {
        cidade = new Cidade();
        cidade.setNome("Fortaleza");
        cidade.setPais("Brasil");
    }

    @Test
    void checkCidadeTest() {
        assertEquals("Fortaleza", cidade.getNome());
        assertEquals("Brasil", cidade.getPais());
    }
}
