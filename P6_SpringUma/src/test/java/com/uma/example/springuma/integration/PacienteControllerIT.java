package com.uma.example.springuma.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PacienteControllerIT extends AbstractIntegration {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class GetPaciente{

        @Test
        void getPaciente_returnsPaciente() throws Exception {
            //arrange
            Paciente paciente = crearPacienteOK();

            //Act
            //Assert
            mockMvc.perform(get("/paciente/"+paciente.getId()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.id", equalTo((int) paciente.getId())))
                    .andExpect(jsonPath("$.nombre", equalTo( paciente.getNombre())));
        }
    }

    private Paciente crearPacienteOK() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setNombre("Alumno");
        paciente.setDni("122");
        paciente.setEdad(16);
        paciente.setId(1);
        this.mockMvc.perform(post("/paciente")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isOk());
        return paciente;
    }
}
