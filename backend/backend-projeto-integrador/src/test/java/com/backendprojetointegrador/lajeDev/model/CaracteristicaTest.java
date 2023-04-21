package com.backendprojetointegrador.lajeDev.model;

import com.backendprojetointegrador.lajeDev.domain.model.Caracteristica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class CaracteristicaTest {

    private Caracteristica caracteristica;

    @BeforeEach
    void arrangeCaracteristica() {
        caracteristica = new Caracteristica();
        caracteristica.setNome("Luxuoso");
        caracteristica.setIcone("aqui estará uma url do endereço da imagem de icone desta caracteristica");
    }

    @Test
    void checkCaracterisitcaTest() {
        assertEquals("Luxuoso", caracteristica.getNome());
        assertEquals("aqui estará uma url do endereço da imagem de icone desta caracteristica",
                caracteristica.getIcone());
    }
}
