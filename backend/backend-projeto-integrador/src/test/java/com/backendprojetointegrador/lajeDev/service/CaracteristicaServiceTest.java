package com.backendprojetointegrador.lajeDev.service;

import com.backendprojetointegrador.lajeDev.domain.model.Caracteristica;
import com.backendprojetointegrador.lajeDev.domain.service.CaracteristicaService;
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
public class CaracteristicaServiceTest  {

    @Autowired
    CaracteristicaService caracteristicaService;
    Caracteristica caracteristica;
    Caracteristica caracteristicaSave;

    @BeforeAll
    void arrageCaracteristica() {
        caracteristica = new Caracteristica();
        caracteristica.setNome("pequeno");
        caracteristica.setIcone("nesta propriedade ficara uma URL com o caminho para o icone desta caracteristica");
        caracteristicaSave = caracteristicaService.criarCaracteristica(caracteristica);
    }

    @Test
    @Order(1)
    void checkCreationCaracteristicaTest() {
        assertNotNull(caracteristicaSave.getId());
        assertEquals(caracteristica.getNome(), caracteristicaSave.getNome());
        assertEquals(caracteristica.getIcone(), caracteristicaSave.getIcone());
    }

    @Test
    @Order(2)
    void searchCaracteristicaByIdTest() {
        assertNotNull(caracteristicaService.buscarCaracteristica(caracteristicaSave.getId()));
    }

    @Test
    @Order(3)
    void updateCaracteristicaTest() {
        caracteristica.setNome("grande");
        caracteristica.setIcone("aqui mias um texto sem sentido para testar para simbolizar a URL do icone");
        caracteristica.setId(caracteristicaSave.getId());
        caracteristicaSave = caracteristicaService.criarCaracteristica(caracteristica);
        assertNotNull(caracteristicaSave.getId());
        assertEquals(1, caracteristicaSave.getId());
        assertEquals(caracteristica.getNome(), caracteristicaSave.getNome());
        assertEquals(caracteristica.getIcone(), caracteristicaSave.getIcone());
    }

    @Test
    @Order(4)
    void listTodasCaracteristicasTest() {
        Caracteristica caracteristicaTwo = new Caracteristica();
        caracteristicaTwo.setNome("Confortável");
        caracteristicaTwo.setIcone("texto totalmente aleatório para estar no lugar do endereço do icone");
        caracteristicaService.criarCaracteristica(caracteristicaTwo);

        assertEquals(2, caracteristicaService.listarCaracteristicas().size());
        caracteristicaService.excluirCaracteristica(caracteristicaTwo.getId());
    }

    @Test
    @Order(5)
    void deleteCaracterisitcaTest() {
        caracteristicaService.excluirCaracteristica(caracteristicaSave.getId());
        assertFalse(caracteristicaService.existeCaracteristica(caracteristicaSave.getId()));
        assertEquals(0, caracteristicaService.listarCaracteristicas().size());
    }
}
