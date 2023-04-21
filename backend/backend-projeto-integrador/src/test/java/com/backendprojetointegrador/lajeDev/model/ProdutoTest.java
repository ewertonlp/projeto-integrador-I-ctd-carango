package com.backendprojetointegrador.lajeDev.model;

import com.backendprojetointegrador.lajeDev.domain.model.Caracteristica;
import com.backendprojetointegrador.lajeDev.domain.model.Categoria;
import com.backendprojetointegrador.lajeDev.domain.model.Cidade;
import com.backendprojetointegrador.lajeDev.domain.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ProdutoTest {

    private Produto produto;

    @BeforeEach
    void arrangeProduto() {
        produto = new Produto();
        produto.setNome("Nivus Chevrollet");
        produto.setDescricao("carro SUV, discreto, com ae de sopfisticação e bom gosto, modesto por fora" +
                " e irresistivelmente agressivo por dentro");

        Caracteristica caracteristicaOne = new Caracteristica();
        caracteristicaOne.setNome("discreto");
        caracteristicaOne.setIcone("sjkdfghsdjklgnsdjkgnfs");
        Caracteristica caracteristicaTwo = new Caracteristica();
        caracteristicaOne.setNome("agressivo");
        caracteristicaOne.setIcone("sdgklsdngklsdn");
        List<Caracteristica> caracteristicas = Arrays.asList(caracteristicaOne, caracteristicaTwo);
        produto.setCaracteristicas(caracteristicas);

        Categoria categoria = new Categoria();
        categoria.setQualificacao("SUV");
        categoria.setDescricao("carro espaçoso e grande");
        categoria.setUrlImagem("ros;lgknrgjklvnsdfkjlvnsdfklnvsdjn");
        produto.setCategoria(categoria);

        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.setPais("Brasil");
        produto.setCidade(cidade);
    }

    @Test
    void checkProdutoTest() {
        assertEquals("Nivus Chevrollet", produto.getNome());
        assertEquals("carro SUV, discreto, com ae de sopfisticação e bom gosto, modesto por fora" +
                " e irresistivelmente agressivo por dentro", produto.getDescricao());
        assertEquals("Nivus Chevrollet", produto.getNome());
        assertEquals(2, produto.getCaracteristicas().size());
        assertEquals("SUV", produto.getCategoria().getQualificacao());
        assertEquals("carro espaçoso e grande", produto.getCategoria().getDescricao());
        assertEquals("ros;lgknrgjklvnsdfkjlvnsdfklnvsdjn", produto.getCategoria().getUrlImagem());
        assertEquals("São Paulo", produto.getCidade().getNome());
        assertEquals("Brasil", produto.getCidade().getPais());
    }
}
