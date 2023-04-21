package com.backendprojetointegrador.lajeDev.repository;

import com.backendprojetointegrador.lajeDev.domain.model.Caracteristica;
import com.backendprojetointegrador.lajeDev.domain.repository.ICaracteristicasRepository;
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
public class CaracteristicaRepositoryTest {

    @Autowired
    private ICaracteristicasRepository caracteristicasRepository;

    private Caracteristica caracteristica;

    private Caracteristica caracteristicaSave;

    @BeforeAll
    void arrangeCaracteristica() {
        caracteristica = new Caracteristica();
        caracteristica.setNome("grande");
        caracteristica.setIcone("icone qualquer que represente esta característica em especifico");
        caracteristicaSave = caracteristicasRepository.save(caracteristica);
    }

    @Test
    @Order(1)
    void searchCaracteristicaByIdTest() {
        assertTrue(caracteristicasRepository.findById(caracteristicaSave.getId()).isPresent());
    }

    @Test
    @Order(2)
    void updateCaracteristicaTest() {
        caracteristica.setNome("utilitário");
        caracteristica.setIcone("mudando completamente a url do caminho deste icone para esta caracteristica");
        caracteristica.setId(caracteristicaSave.getId());
        caracteristicaSave = caracteristicasRepository.save(caracteristica);
        assertNotNull(caracteristicaSave.getId());
        assertEquals(1, caracteristicaSave.getId());
        assertEquals(caracteristica.getNome(), caracteristicaSave.getNome());
        assertEquals(caracteristica.getIcone(), caracteristicaSave.getIcone());
    }

    @Test
    @Order(3)
    void listCaracteristicasTest() {
        assertEquals(1, caracteristicasRepository.findAll().size());
    }

    @Test
    @Order(4)
    void deleteCaracteristicaTest() {
        caracteristicasRepository.deleteById(caracteristicaSave.getId());
        assertFalse(caracteristicasRepository.findById(caracteristicaSave.getId()).isPresent());
    }

}
