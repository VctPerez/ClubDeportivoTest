package com.uma.example.springuma.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Paciente;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RepositoryPacienteIT extends AbstractIntegration {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class GetPaciente{
        @Test
        void getPaciente_returnsPaciente() throws Exception {

        }
    }

    private void crearPacienteOK() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setNombre("Alumno");
        paciente.setDni("122");
        paciente.setEdad(16);
        paciente.setId(1);
        this.mockMvc.perform(post("/persona")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isOk());
    }
}
