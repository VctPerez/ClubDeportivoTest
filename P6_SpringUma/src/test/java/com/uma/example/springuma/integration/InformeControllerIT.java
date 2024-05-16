package com.uma.example.springuma.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uma.example.springuma.integration.base.AbstractIntegration;

import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.Informe;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

import java.awt.*;
import java.io.File;
import java.time.Duration;

public class InformeControllerIT extends AbstractIntegration {

    @LocalServerPort
    private Integer port;

    @Autowired
    private MockMvc mockMvc;

    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:"+port).responseTimeout(Duration.ofMillis(100000)).build();
    }

    private Informe informe;

    @BeforeEach
    public void beforeEach() throws  Exception{
        informe = createInforme();
    }

    @Test
    @DisplayName("Check infome is created correctly")
    public void givenInformeEntity_whenInformeCreated_thenInformeExists() throws Exception {
        webTestClient.post().uri("/informe")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(informe))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @DisplayName("Check infome is read correctly")
    public void givenInformeEntity_whenInformeCreated_thenInformeCanBeRead() throws Exception {
        webTestClient.post().uri("/informe")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(informe))
                .exchange()
                .expectStatus().isCreated();

        webTestClient.get().uri("/informe/"+informe.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @DisplayName("Check infome list is read correctly")
    public void givenInformeEntity_whenInformeCreated_thenInformeListCanBeRead() throws Exception {
        webTestClient.post().uri("/informe")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(informe))
                .exchange()
                .expectStatus().isCreated();

        webTestClient.get().uri("/informe/imagen/"+informe.getImagen().getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @DisplayName("Check infome is deleted correctly")
    public void givenInformeEntity_whenInformeCreated_thenInformeCanBeDeleted() throws Exception {
        webTestClient.post().uri("/informe")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(informe))
                .exchange()
                .expectStatus().isCreated();

        webTestClient.delete().uri("/informe/"+informe.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    public Informe createInforme() throws Exception {
        Medico medico = createMedico();
        Paciente paciente = createPaciente(medico);
        Imagen imagen = createImagen(paciente);

        //Creamos y añadimos un informe
        Informe informe = new Informe();
        informe.setId(1);
        informe.setImagen(imagen);
        informe.setPrediccion("Okey");
        informe.setContenido("Okey");

        return informe;
    }

    public Medico createMedico() throws Exception {
        //Creamos y añadimos un médico

        Medico medico = new Medico();
        medico.setId(1);

        this.mockMvc.perform(post("/medico")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(medico)))
                .andExpect(status().isCreated());
        return medico;
    }

    public Paciente createPaciente(Medico medico) throws Exception {
        //Creamos y añadimos un paciente
        Paciente paciente = new Paciente();
        paciente.setId(1);
        paciente.setMedico(medico);

        this.mockMvc.perform(post("/paciente")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isCreated());
        return paciente;
    }

    public Imagen createImagen(Paciente paciente) throws Exception {
        //Creamos y añadimos una imagen a ese paciente
        File imagen = new File("./src/test/resources/healthy.png");

        LinkedMultiValueMap<String, Object> objetos = new LinkedMultiValueMap<>();
        objetos.add("image", new FileSystemResource(imagen));
        objetos.add("paciente", paciente);

        webTestClient.post().uri("/imagen")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(objetos))
                .exchange()
                .expectStatus().is2xxSuccessful();

        FluxExchangeResult<Imagen> result = webTestClient.get().uri("/imagen/info/1")
                .exchange()
                .returnResult(Imagen.class);
        return result.getResponseBody().blockFirst();
    }


}
