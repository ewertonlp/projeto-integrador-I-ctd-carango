package com.backendprojetointegrador.lajeDev.api.testsIntegration;

import com.backendprojetointegrador.lajeDev.api.dtos.inputs.ProdutoInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProdutoTestIntegrationApi {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void a_CriaUmProdutoTest() throws Exception {
        ProdutoInput produtoInput = new ProdutoInput();
        produtoInput.setNome("Nivus");
        produtoInput.setDescricao("carro lindo e elegante");
        produtoInput.setCidade(1L);
        produtoInput.setCategoria(1L);
        produtoInput.setVin("nfklhjduti7845pdf");
        produtoInput.setCaracteristicas(Arrays.asList(1L));

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false).writer();

        String payloadJson = writer.writeValueAsString(produtoInput);

        mockMvc.perform(MockMvcRequestBuilders.post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andReturn();
    }

    @Test
    public void b_ListarTodosProdutosTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produtos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void c_ListarProdutosPorCategoria() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/produtos/listarPorCategoria?categoria={idCategoria}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void d_ListarProdutosPorCidade() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/listarPorCidade?cidade={idCidade}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void e_BuscarUmProdutoTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/{idProduto}", 1))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void f_ExcluirUmProdutoTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/produtos/{idProduto}", 1))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
