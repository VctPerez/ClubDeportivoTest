package clubdeportivo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class ClubDeportivoAltoRendimientoTest { // Hecho Por David Bueno Carmona & Victor Perez Armenta

    ClubDeportivoAltoRendimiento club;
    @BeforeEach
    void setup() throws ClubException {
        club = new ClubDeportivoAltoRendimiento("Nombre",10,1.5);
    }

    @Test
    public void ClubDeportivoAltoRendimientoSinTamaño_DatosValidos_ReturnNotThrow() {
        String nombre = "EjemploNombre";
        int maximo = 10;
        double incremento = 1.5;

        assertDoesNotThrow(() -> new ClubDeportivoAltoRendimiento(nombre,maximo,incremento));
    }

    @Test
    public void ClubDeportivoAltoRendimientoSinTamaño_MaximoNoValido_ReturnThrow() {
        String nombre = "EjemploNombre";
        int maximo = -10;
        double incremento = 1.5;

        assertThrows(ClubException.class,() -> new ClubDeportivoAltoRendimiento(nombre,maximo,incremento));
    }

    @Test
    public void ClubDeportivoAltoRendimientoSinTamaño_NombreNULL_ReturnNotThrow() {
        String nombre = null;
        int maximo = 10;
        double incremento = 1.5;

        assertThrows(ClubException.class,() -> new ClubDeportivoAltoRendimiento(nombre,maximo,incremento));
    }

    @Test
    public void ClubDeportivoAltoRendimientoSinTamaño_IncrementoNoValido_ReturnNotThrow() {
        String nombre = "EjemploNombre";
        int maximo = 10;
        double incremento = -1.5;

        assertThrows(ClubException.class,() -> new ClubDeportivoAltoRendimiento(nombre,maximo,incremento));
    }

    @Test
    public void ClubDeportivoAltoRendimientoConTamaño_DatosValidos_ReturnNotThrow() {
        String nombre = "EjemploNombre";
        int maximo = 10;
        double incremento = 1.5;
        int tamaño = 20;

        assertDoesNotThrow(() -> new ClubDeportivoAltoRendimiento(nombre,tamaño,maximo,incremento));
    }

    @Test
    public void ClubDeportivoAltoRendimientoConTamaño_MaximoNoValido_ReturnThrow() {
        String nombre = "EjemploNombre";
        int maximo = -10;
        double incremento = 1.5;
        int tamaño = 20;

        assertThrows(ClubException.class,() -> new ClubDeportivoAltoRendimiento(nombre,tamaño,maximo,incremento));
    }

    @Test
    public void ClubDeportivoAltoRendimientoConTamaño_IncrementoNoValido_ReturnNotThrow() {
        String nombre = "EjemploNombre";
        int maximo = 10;
        double incremento = -1.5;
        int tamaño = 20;

        assertThrows(ClubException.class,() -> new ClubDeportivoAltoRendimiento(nombre,tamaño,maximo,incremento));
    }

    @Test
    public void ClubDeportivoAltoRendimientoConTamaño_NombreNULL_ReturnNotThrow() {
        String nombre = null;
        int maximo = 10;
        double incremento = 1.5;
        int tamano = 20;

        assertThrows(ClubException.class,() -> new ClubDeportivoAltoRendimiento(nombre,tamano,maximo,incremento));
    }

    @Test
    public void ClubDeportivoAltoRendimientoConTamaño_TamañoNegativo_ReturnNotThrow() {
        String nombre = "EjemploNombre";
        int maximo = 10;
        double incremento = 1.5;
        int tamano = -20;

        assertThrows(ClubException.class,() -> new ClubDeportivoAltoRendimiento(nombre,tamano,maximo,incremento));
    }

    @Test
    public void anyadirActividad_DatosValidos_ReturnNotThrow() throws ClubException {
        String nombre = "EjemploNombre";
        String actividad = "EjemploActividad";
        String plazas = "10";
        String matriculados = "5";
        String tarifa = "12";
        String[] datos = {nombre,actividad,plazas,matriculados,tarifa};

        club.anyadirActividad(datos);

        assertEquals(5,club.plazasLibres("EjemploActividad"));
    }

    @Test
    public void anyadirActividad_PlazasMayorQueMaximo_ReturnNotThrow() throws ClubException {
        String nombre = "EjemploNombre";
        String actividad = "EjemploActividad";
        String plazas = "12";
        String matriculados = "5";
        String tarifa = "12";
        String[] datos = {nombre,actividad,plazas,matriculados,tarifa};

        club.anyadirActividad(datos);

        assertEquals(5,club.plazasLibres("EjemploActividad"));
    }

    @Test
    public void anyadirActividad_TamanoArrayMenor5_ReturnNotThrow() {
        String nombre = "EjemploNombre";
        String actividad = "EjemploActividad";
        String plazas = "12";
        String matriculados = "5";
        String[] datos = {nombre,actividad,plazas,matriculados};

        assertThrows(ClubException.class,() -> club.anyadirActividad(datos));
    }

    @ParameterizedTest
    @CsvSource({
            "Ejemplo,Ejemplo,12,2,",
            "Ejemplo,Ejemplo,12,,3",
            "Ejemplo,Ejemplo,,2,3",
            "Ejemplo,,12,2,3",
            ",Ejemplo,12,2,3",
    })
    public void anyadirActividad_ArrayDatosNULL_ReturnThrow(String codigo, String actividad, String nplazas, String nmatriculados, String tarifa) {
        String[] datos = {codigo, actividad, nplazas, nmatriculados, tarifa};

        assertThrows(ClubException.class, () -> club.anyadirActividad(datos));
    }

    @ParameterizedTest
    @CsvSource({
            "Ejemplo,Ejemplo,12,2,hola",
            "Ejemplo,Ejemplo,12,hola,3",
            "Ejemplo,Ejemplo,hola,2,3",
    })
    public void anyadirActividad_ArrayFormatoInvalidos_ReturnThrow(String codigo, String actividad, String nplazas, String nmatriculados, String tarifa) {
        String[] datos = {codigo, actividad, nplazas, nmatriculados, tarifa};

        assertThrows(ClubException.class, () -> club.anyadirActividad(datos));
    }

    @Test
    public void ingresos_ConGrupos_ReturnExpected() throws ClubException {
        String[] datos = {"Ejemplo", "Ejemplo", "10", "5", "3.2"};
        club.anyadirActividad(datos);
        double expectedRes = 16.24;

        double res = club.ingresos();

        assertEquals(expectedRes,res);
    }

    @Test
    public void ingresos_SinGrupos_Return0() {
        double expectedRes = 0;

        double res = club.ingresos();

        assertEquals(expectedRes,res);
    }
}
