package com.uma.example.springuma.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.integration.base.AbstractIntegration;

import com.uma.example.springuma.model.Medico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MedicoControllerIT extends AbstractIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Check medico is created correctly")
    public void givenMedicoEntity_whenMedicoCreated_thenMedicoExists() throws Exception {
        Medico medico = createMedico();

        this.mockMvc.perform(post("/medico")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medico)))
                .andExpect(status().isCreated());


    }

    @Test
    @DisplayName("Check medico is readable correctly")
    public void givenMedicoEntity_whenMedicoCreated_thenMedicoCanBeRead() throws Exception {
        Medico medico = createMedico();
        addMedico(medico);

        this.mockMvc.perform(get("/medico/"+medico.getId()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", equalTo((int) medico.getId())))
                .andExpect(jsonPath("$.nombre", equalTo( medico.getNombre())));

    }

    @Test
    @DisplayName("Check medico is readable correctly with DNI")
    public void givenMedicoEntity_whenMedicoCreated_thenMedicoCanBeReadWithDNI() throws Exception {
        Medico medico = createMedico();
        addMedico(medico);

        this.mockMvc.perform(get("/medico/dni/"+medico.getDni()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", equalTo((int) medico.getId())))
                .andExpect(jsonPath("$.nombre", equalTo( medico.getNombre())));

    }

    @Test
    @DisplayName("Check medico is edited correctly")
    public void givenMedicoEntity_whenMedicoEdited_thenMedicoIsEdited() throws Exception {
        Medico medico = createMedico();
        this.addMedico(medico);
        medico.setDni("12345678G");

        this.mockMvc.perform(put("/medico")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(medico)));

        this.mockMvc.perform(get("/medico/"+medico.getId()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", equalTo((int) medico.getId())))
                .andExpect(jsonPath("$.dni", equalTo("12345678G")));

    }

    @Test
    @DisplayName("Check medico is deleted correctly")
    public void givenMedicoEntity_whenMedicoCreated_thenMedicoCanBeDeleted() throws Exception {
        Medico medico = createMedico();
        this.addMedico(medico);

        this.mockMvc.perform(delete("/medico/"+medico.getId()));

        this.mockMvc.perform(get("/medico/"+medico.getId()))
                .andExpect(status().is5xxServerError());
    }

    public Medico createMedico() throws Exception {
        Medico medico = new Medico();
        medico.setNombre("David");
        medico.setId(1);
        medico.setDni("77234740G");
        medico.setEspecialidad("Cirujía");

        return medico;
    }

    public void addMedico(Medico medico) throws Exception {
        this.mockMvc.perform(post("/medico")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medico)))
                .andExpect(status().isCreated());
    }
}
