package com.uma.example.springuma.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
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
        @DisplayName("Devuelve datos del paciente si este se encuentra en la base de datos")
        void getPaciente_pacienteExistente_returnsPaciente() throws Exception {
            //arrange
            Paciente paciente = crearPaciente(1);

            //Act
            //Assert
            mockMvc.perform(get("/paciente/"+paciente.getId()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", equalTo((int) paciente.getId())))
                    .andExpect(jsonPath("$.nombre", equalTo( paciente.getNombre())))
                    .andExpect(jsonPath("$.dni", equalTo( paciente.getDni())));
        }
    }

    @Nested
    class GetPacientes{
        @Test
        @DisplayName("Devuelve una lista vacía si el médico no tiene pacientes")
        void getPacientes_medicoExistenteSinPacientes_returnsListaVacia() throws Exception {
            //arrange
            Medico medico = crearMedico(1);

            //Act
            //Assert
            mockMvc.perform(get("/paciente/medico/"+medico.getId()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(0)));
        }

        @Test
        @DisplayName("Devuelve la lista de pacientes asignados al medico")
        void getPacientes_medicoExistenteConPacientes_returnsListaConPacientesDelMedico() throws Exception {
            //arrange
            Paciente paciente1 = crearPaciente(1);
            Paciente paciente2 = crearPaciente(2);

            //Act
            //Assert
            mockMvc.perform(get("/paciente/medico/"+paciente1.getMedico().getId()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize(2)));
        }
    }

    @Nested
    class SavePaciente{
        @Test
        @DisplayName("Guarda a un paciente en la base de datos")
        void savePaciente_pacienteOk_guardaPacienteEnBD() throws Exception{
            Medico medico = crearMedico(1);
            Paciente paciente = new Paciente();
            paciente.setId(1);
            paciente.setNombre("Alumno");
            paciente.setDni(Integer.toString(1));
            paciente.setMedico(medico);

            mockMvc.perform(post("/paciente")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(paciente)))
                    .andExpect(status().isCreated());

            mockMvc.perform(get("/paciente/"+paciente.getId()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", equalTo((int) paciente.getId())))
                    .andExpect(jsonPath("$.medico.id", equalTo((int) medico.getId())));
        }

    }

    @Nested
    class UpdateCuenta{
        @Test
        @DisplayName("Actualiza los datos del paciente en la base de datos")
        void updateCuenta_pacienteOk_actualizaDatosPacienteEnBD() throws Exception{
            Medico medico = crearMedico(1);
            Paciente paciente = new Paciente();
            paciente.setId(1);
            paciente.setNombre("Alumno");
            paciente.setDni(Integer.toString(1));
            paciente.setMedico(medico);

            mockMvc.perform(post("/paciente")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(paciente)))
                    .andExpect(status().isCreated());

            Medico expectedMedico = crearMedico(2);
            paciente.setMedico(expectedMedico);

            mockMvc.perform(put("/paciente")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(paciente)))
                    .andExpect(status().is2xxSuccessful());

            mockMvc.perform(get("/paciente/"+paciente.getId()))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id", equalTo((int) paciente.getId())))
                    .andExpect(jsonPath("$.medico.id", equalTo((int) expectedMedico.getId())));
        }

    }

    @Nested
    class DeleteCuenta{
        @Test
        @DisplayName("Elimina al pacienete de la base de datos")
        void deleteCuenta_pacienteOk_borraPacienteDeBD() throws Exception{
            Paciente paciente = new Paciente();
            paciente.setId(1);
            paciente.setNombre("Alumno");
            paciente.setDni(Integer.toString(1));
            mockMvc.perform(post("/paciente")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(paciente)))
                    .andExpect(status().isCreated());

            mockMvc.perform(delete("/paciente/"+paciente.getId()))
                    .andExpect(status().is2xxSuccessful());
        }

    }

    private Paciente crearPaciente(long pacienteId) throws Exception {
        Paciente paciente = new Paciente();
        paciente.setId(pacienteId);
        paciente.setNombre("Alumno");
        paciente.setDni(Long.toString(pacienteId));
        paciente.setEdad(16);
        paciente.setCita("15-6-2024");
        paciente.setMedico(crearMedico(1));
        this.mockMvc.perform(post("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isCreated());
        return paciente;
    }

    private Medico crearMedico(long medicoId) throws Exception{
        Medico medico = new Medico();
        medico.setId(medicoId);
        mockMvc.perform(post("/medico")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medico)))
                .andExpect(status().isCreated());

        return medico;
    }
}
