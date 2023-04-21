package com.backendprojetointegrador.lajeDev.api.testsIntegration;

import com.backendprojetointegrador.lajeDev.api.dtos.inputs.CaracteristicaInput;
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
public class CaracterisitcaTestIntegrationApi {

    @Autowired
    private MockMvc mocMvc;

    @Test
    public void criaUmaCaracteristica() throws Exception {
        CaracteristicaInput caracteristicaInput = new CaracteristicaInput();
        caracteristicaInput.setNome("Luxuoso");
        caracteristicaInput.setIcone("asjkfghsjkfgbsdjkfhsd");

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false).writer();

        String payloadJson = writer.writeValueAsString(caracteristicaInput);

        mocMvc.perform(MockMvcRequestBuilders.post("/caracteristicas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andReturn();
    }
}
