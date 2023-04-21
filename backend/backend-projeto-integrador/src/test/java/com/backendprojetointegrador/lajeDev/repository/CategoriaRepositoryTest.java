package com.backendprojetointegrador.lajeDev.repository;

import com.backendprojetointegrador.lajeDev.domain.model.Categoria;
import com.backendprojetointegrador.lajeDev.domain.repository.ICategoriaRepository;
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
public class CategoriaRepositoryTest {
    @Autowired
    private ICategoriaRepository categoriaRepository;
    private Categoria categoria;
    private Categoria categoriaSave;

    @BeforeAll
    void arrangeCategoria() {
        categoria = new Categoria();
        categoria.setQualificacao("SUV");
        categoria.setDescricao("veículo de alto padrão, por isso maior e com características mais sofisticadas em" +
                "comparação com um carro sedan");
        categoria.setUrlImagem("https://img.olhardigital.com.br/wp-content/uploads/2022/06/polestar3-1.jpg");
        categoriaSave = categoriaRepository.save(categoria);
    }

    @Test
    @Order(1)
    void searchCategoriaByIdTest() {
        assertTrue(categoriaRepository.findById(categoriaSave.getId()).isPresent());
    }

    @Test
    @Order(2)
    void updateCategoriaTest() {
        categoria.setQualificacao("Conversivel");
        categoria.setDescricao("veículo esportivo, com estilo agressivo, tem menor espaço interno em comparação com " +
                "uma SUV, mas tem um motor mais potente e melhor desempenho em retas");
        categoria.setUrlImagem("https://blog.catarinacarros.com.br/wp-content/uploads/2020/02/bmw-zseries-z4-conversivel-1024x576.jpg");
        categoriaSave = categoriaRepository.saveAndFlush(categoria);
        assertNotNull(categoriaSave.getId());
        assertEquals(categoria.getQualificacao(), categoriaSave.getQualificacao());
        assertEquals(categoria.getDescricao(), categoriaSave.getDescricao());
        assertEquals(categoria.getUrlImagem(), categoriaSave.getUrlImagem());
    }

    @Test
    @Order(3)
    void deleteCategoriaTest() {
        categoriaRepository.deleteById(categoriaSave.getId());
        assertFalse(categoriaRepository.existsById(categoriaSave.getId()));
    }
}
