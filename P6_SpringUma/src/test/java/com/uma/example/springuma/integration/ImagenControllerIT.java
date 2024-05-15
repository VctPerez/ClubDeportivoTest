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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.*;
import java.io.File;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImagenControllerIT extends AbstractIntegration {

    @Autowired
    private ImagenController imagenController = new ImagenController();

    private Medico medico;
    private Paciente paciente;

    private final String HEALTHY_IMG = "./src/test/resources/healthy.png";
    private final String UNHEALTHY_IMG = "./src/test/resources/no_healthty.png";


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

    private FluxExchangeResult<String> saveImage(String path) {
        File uploadImage = new File(path);

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", new FileSystemResource(uploadImage));
        builder.part("paciente", paciente);

        return webTestClient.post().uri("/imagen").contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange().expectStatus().is2xxSuccessful().returnResult(String.class);
    }

    @Test
    @DisplayName("Subir imagen con un path valido")
    public void uploadImage_returnsOk(){
        saveMedico();
        savePaciente();
        String response = saveImage(HEALTHY_IMG).getResponseBody().blockFirst();

        assertEquals("{\"response\" : \"file uploaded successfully : healthy.png\"}", response);
    }

    @Test
    @DisplayName("DownloadImage con un id valido devuelve la imagen")
    public void downloadImage_idValido_returnsImagen() {
        saveMedico();
        savePaciente();
        saveImage(HEALTHY_IMG);

        webTestClient.get().uri("/imagen/{id}", 1).accept(MediaType.IMAGE_PNG)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Imagen.class);
    }

    @Test
    @DisplayName("DownloadImage con un id invalido devuelve error del servidor.")
    public void downloadImage_idInvalido_returnsServerError() {
        saveMedico();
        savePaciente();

        webTestClient.get().uri("/imagen/{id}", 1).accept(MediaType.IMAGE_PNG).exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("GetImagen con un id valido devuelve OK.")
    public void getImagen_idValido_returnsOk(){
        saveMedico();
        savePaciente();
        saveImage(HEALTHY_IMG);

        webTestClient.get().uri("/imagen/{id}", 1).accept(MediaType.IMAGE_PNG).exchange()
                .expectStatus().isOk().returnResult(Imagen.class);
    }

    @Test
    @DisplayName("GetImagen con un id invalido devuelve error del servidor.")
    public void getImagen_idInvalido_returnsServerError() {
        saveMedico();
        savePaciente();

        webTestClient.get().uri("/imagen/{id}", 1).accept(MediaType.IMAGE_PNG).exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("GetImagenPrediction con un id valido devuelve OK y la prediccion")
    public void getImagenPrediction_idValido_returnsOk(){
        saveMedico();
        savePaciente();
        saveImage(HEALTHY_IMG);

        webTestClient.get().uri("/imagen/predict/{id}", 1)
                .accept(MediaType.IMAGE_PNG).exchange().expectStatus().isOk()
                .returnResult(String.class);
    }

    @Test
    @DisplayName("GetIamgenPrediction con un id invalido devuelve error del servidor.")
    public void getImagenPrediction_idInvalido_returnsServerError() {
        saveMedico();
        savePaciente();

        webTestClient.get().uri("/imagen/predict/{id}", 1).accept(MediaType.IMAGE_PNG)
                .exchange().expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("GetIamgenes sin imagenes guardadas devuelve lista vacia.")
    public void getImagenes_sinImagenes_returnsListaVacia(){
        saveMedico();
        savePaciente();

        List<Imagen> imagenes = webTestClient.get().uri("imagen/paciente/{id}", 1)
                .exchange().expectBodyList(Imagen.class).returnResult().getResponseBody();

        assertTrue(imagenes.isEmpty());
    }

    @Test
    @DisplayName("GetImagenes con imagenes guardadas devulve una lista de imagenes")
    public void getImagenes_conImagenes_returnsListaConElementos(){
        saveMedico();
        savePaciente();
        saveImage(HEALTHY_IMG);
        saveImage(UNHEALTHY_IMG);

        List<Imagen> imagenes = webTestClient.get().uri("imagen/paciente/{id}", 1)
                .exchange().expectBodyList(Imagen.class).returnResult().getResponseBody();

        assertFalse(imagenes.isEmpty());
    }

    @Test
    @DisplayName("DeleteCuenta con un id valido se realiza correctamente")
    public void deleteCuenta_idValido_returnsNoContent(){
        saveMedico();
        savePaciente();
        saveImage(HEALTHY_IMG);

        webTestClient.delete().uri("/imagen/{id}", 1)
                .exchange().expectStatus().isNoContent();
    }

    @Test
    @DisplayName("DeleteCuenta con un id invalido no se realiza")
    public void deleteCuenta_idInvalido_returnsNoContent() {
        saveMedico();
        savePaciente();

        webTestClient.delete().uri("/imagen/{id}", 1)
                .exchange().expectStatus().isNoContent();
    }


}
