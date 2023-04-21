package com.backendprojetointegrador.lajeDev.api.testsIntegration;

import com.backendprojetointegrador.lajeDev.api.dtos.inputs.CategoriaInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CategoriaTestIntegrationApi {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void criaUmaCategoriaTest() throws Exception {
        CategoriaInput categoriaInput = new CategoriaInput();
        categoriaInput.setQualificacao("SUV");
        categoriaInput.setDescricao("veículo utilitário com quatro portas, maior espaço " +
                "interno que um carro hatch e bagageiro espaçoso");
        categoriaInput.setUrlImagem("https://uploads-ssl.webflow.com" +
                "/5de0424183c9d7b00dd43bca/5f9071bf9bbc9f70d25745af_carros-sedan-mais-vendidos.jpg");

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer();

        String payloadJson = writer.writeValueAsString(categoriaInput);

        mockMvc.perform(MockMvcRequestBuilders.post("/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andReturn();
    }
}
