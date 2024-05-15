package com.uma.example.springuma.integration;

import com.uma.example.springuma.controller.ImagenController;
import com.uma.example.springuma.integration.base.AbstractIntegration;
import com.uma.example.springuma.model.Imagen;
import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.Paciente;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImagenControllerIT extends AbstractIntegration {

    @Autowired
    private ImagenController imagenController = new ImagenController();

    private Medico medico;
    private Paciente paciente;

    private final String HEALTHY_IMG = "./src/test/resources/healthy.png";
    private final String UNHEALTHY_IMG = "./src/test/resources/unhealthy.png";


    @LocalServerPort
    private Integer port;

    private WebTestClient webTestClient;


    @PostConstruct
    public void init() {
        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).responseTimeout(Duration.ofMillis(30000)).build();
    }

    @BeforeEach
    public void setPacienteAndMedico() {
        medico = new Medico();
        medico.setDni("77665544A");
        medico.setNombre("Tomas");
        medico.setEspecialidad("cirujia");
        medico.setId(1);

        paciente = new Paciente();
        paciente.setId(1);
        paciente.setDni("11223344A");
        paciente.setNombre("Pablo");
        paciente.setCita("operacion");
        paciente.setMedico(medico);
        paciente.setEdad(18);
    }

    private void saveMedico() {
        webTestClient.post().uri("/medico")
                .body(Mono.just(medico), Medico.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();
    }

    private void savePaciente() {
        webTestClient.post().uri("/paciente")
                .body(Mono.just(paciente), Paciente.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().returnResult();
    }

    private FluxExchangeResult<String> saveImage() {
        File uploadImage = new File(HEALTHY_IMG);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadImage));
        builder.part("paciente", paciente);

        FluxExchangeResult<String> responseBody = webTestClient.post().uri("/imagen").contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange().expectStatus().is2xxSuccessful().returnResult(String.class);

        return responseBody;
    }

    @Test
    @DisplayName("Subir imagen con un paciente valido")
    public void uploadImage_pacienteValido_returnsOk() throws IOException {
        saveMedico();
        savePaciente();
        String response = saveImage().getResponseBody().blockFirst();

        assertEquals("{\"response\" : \"file uploaded successfully : healthy.png\"}", response);
    }

    @Test
    public void downloadImage_idValido_returnsImagen() {
        saveMedico();
        savePaciente();
        saveImage();

        webTestClient.get().uri("/imagen/{id}", 1).accept(MediaType.IMAGE_PNG)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Imagen.class);
    }

    @Test
    public void downloadImage_idInvalido_returnsServerError() {
        saveMedico();
        savePaciente();

        webTestClient.get().uri("/imagen/{id}", 1).accept(MediaType.IMAGE_PNG).exchange()
                .expectStatus().is5xxServerError();
    }


}
