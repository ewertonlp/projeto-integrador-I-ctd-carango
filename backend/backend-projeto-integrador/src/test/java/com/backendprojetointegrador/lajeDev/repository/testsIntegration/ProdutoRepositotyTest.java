package com.backendprojetointegrador.lajeDev.repository.testsIntegration;

import com.backendprojetointegrador.lajeDev.domain.model.Categoria;
import com.backendprojetointegrador.lajeDev.domain.model.Cidade;
import com.backendprojetointegrador.lajeDev.domain.model.Produto;
import com.backendprojetointegrador.lajeDev.domain.repository.ICategoriaRepository;
import com.backendprojetointegrador.lajeDev.domain.repository.ICidadeRepository;
import com.backendprojetointegrador.lajeDev.domain.repository.IProdutoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProdutoRepositotyTest {

    @Autowired
    private IProdutoRepository produtoRepository;
    @Autowired
    private ICidadeRepository cidadeRepository;
    @Autowired
    private ICategoriaRepository categoriaRepository;
    private Produto produto;
    private Produto produtoSave;
    private Cidade cidade;
    private Cidade cidadeSave;
    private Categoria categoria;
    private Categoria categoriaSave;

    @BeforeAll
    void arrangeProduto() {
        produto = new Produto();
        produto.setNome("Nivus Chevrollet");
        produto.setDescricao("carro SUV, discreto, com ae de sopfisticação e bom gosto, modesto por fora" +
                " e irresistivelmente agressivo por dentro");

        categoria = new Categoria();
        categoria.setQualificacao("SUV");
        categoria.setDescricao("carro espaçoso e grande");
        categoria.setUrlImagem("ros;lgknrgjklvnsdfkjlvnsdfklnvsdjn");
        categoriaSave = categoriaRepository.save(categoria);
        produto.setCategoria(categoriaSave);

        cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.setPais("Brasil");
        cidadeSave = cidadeRepository.save(cidade);
        produto.setCidade(cidadeSave);

        produtoSave = produtoRepository.save(produto);
    }

    @Test
    @Order(1)
    void searchProdutoTest() {
        assertTrue(produtoRepository.findById(produtoSave.getId()).isPresent());
    }


    @Test
    @Order(2)
    void listProdutosTest() {
        assertEquals(1, produtoRepository.findAll().size());
    }

    @Test
    @Order(3)
    void listProdutosByCategoriaTest() {
        assertEquals(1, produtoRepository.findByCategoria(categoriaSave).size());
    }

    @Test
    @Order(4)
    void listProdutosByCidadeTest() {
        assertEquals(1, produtoRepository.findByCidade(cidadeSave).size());
    }

    @Test
    @Order(5)
    void deleteProdutoTest() {
        produtoRepository.deleteById(produtoSave.getId());
        assertFalse(produtoRepository.existsById(produtoSave.getId()));
    }

}
