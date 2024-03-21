package clubdeportivo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class ClubDeportivoTest {
    ClubDeportivo club;

    @BeforeEach
    void setup() throws ClubException {
        club = new ClubDeportivo("Ejemplo");
    }

    @Test
    public void ClubDeportivo_SinTamano_ReturnTrue() {
        String nombre = "EjemploNombre";

        assertDoesNotThrow(() -> new ClubDeportivo(nombre));
    }

    @Test
    public void ClubDeportivo_NombreNULL_ReturnTrue() {
        String nombre = null;

        assertThrows(ClubException.class,() -> new ClubDeportivo(nombre));
    }

    @Test
    public void ClubDeportivo_TamanoPositivo_ReturnTrue() {
        String nombre = "EjemploNombre";
        int tam = 5;

        assertDoesNotThrow(() -> new ClubDeportivo(nombre, tam));
    }

    @Test
    public void ClubDeportivo_TamanoNegativo_ReturnThrow() {
        String nombre = "EjemploNombre";
        int tam = -5;

        assertThrows(ClubException.class, () -> new ClubDeportivo(nombre, tam));
    }

    @Test
    public void ClubDeportivo_Tamano0_ReturnThrow() {
        String nombre = "EjemploNombre";
        int tam = 0;

        assertThrows(ClubException.class, () -> new ClubDeportivo(nombre, tam));
    }

    @Test
    public void anyadirActividad_ArrayDatosValidos_ReturnTrue() {
        String[] Datos = {"Ejemplo", "Ejemplo", "10", "5", "5.3"};

        assertDoesNotThrow(() -> club.anyadirActividad(Datos));
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
    public void anyadirActividad_ArrayDatosMenosElementos_ReturnThrow() {
        String[] datos = {"codigo", "actividad", "12", "13"};

        assertThrows(ClubException.class, () -> club.anyadirActividad(datos));
    }

    @Test
    public void anyadirActividad_GrupoValido_ReturnTrue() throws ClubException {
        Grupo g = new Grupo("Ejemplo", "Ejemplo", 12, 11, 3.2);
        int expectedRes = 1;

        club.anyadirActividad(g);

        assertEquals(expectedRes, club.plazasLibres("Ejemplo"));
    }

    @Test
    public void anyadirActividad_GrupoNulo_ReturnThrow() {
        Grupo g = null;

        assertThrows(ClubException.class, () -> club.anyadirActividad(g));
    }

    @Test
    public void anyadirActividad_GrupoYaExistente_ReturnTrue() throws ClubException {
        Grupo g = new Grupo("Ejemplo", "Ejemplo", 12, 11, 3.2);
        club.anyadirActividad(g);
        Grupo g2 = new Grupo("Ejemplo", "Ejemplo", 13, 11, 3.2);
        int expectedRes = 2;

        club.anyadirActividad(g2);

        assertEquals(expectedRes, club.plazasLibres("Ejemplo"));
    }

    @Test
    public void plazasLibres_ActividadValida_ReturnTrue() throws ClubException {
        Grupo g = new Grupo("Ejemplo", "Ejemplo", 12, 11, 3.2);
        Grupo g2 = new Grupo("Ejemplo2", "Ejemplo", 15, 11, 3.2);
        club.anyadirActividad(g);
        club.anyadirActividad(g2);
        int expectedRes = 5;

        int res = club.plazasLibres("Ejemplo");

        assertEquals(expectedRes, res);
    }

    @Test
    public void plazasLibres_ActividadNoValida_ReturnTrue() throws ClubException {
        int expectedRes = 0;

        int res = club.plazasLibres("Ejemplo");

        assertEquals(expectedRes, res);
    }

    @Test
    public void plazasLibres_ActividadNULL_ReturnThrow() throws ClubException {

        assertThrows(ClubException.class,() -> club.plazasLibres(null));
    }

    @Test
    public void matricular_PersonasValidas_ReturnTrue() throws ClubException {
        String actividad = "Ejemplo";
        int npersonas = 1;
        int expectedRes = 1;
        Grupo g = new Grupo("Ejemplo", actividad, 12, 10, 3.2);
        club.anyadirActividad(g);

        club.matricular(actividad,npersonas);

        assertEquals(club.plazasLibres(actividad),expectedRes);
    }

    @Test
    public void matricular_PersonasNoValidos_ReturnThrow() throws ClubException {
        String actividad = "Ejemplo";
        int npersonas = 1;
        Grupo g = new Grupo("Ejemplo", actividad, 12, 12, 3.2);
        club.anyadirActividad(g);

        assertThrows(ClubException.class,()->club.matricular(actividad,npersonas));
    }

    @Test
    public void matricular_PersonasNegativas_ReturnTrue() throws ClubException {
        String actividad = "Ejemplo";
        int npersonas = 0;
        int expectedRes = club.plazasLibres("Ejemplo");
        Grupo g = new Grupo("Ejemplo", actividad, 12, 12, 3.2);
        club.anyadirActividad(g);

        club.matricular(actividad,npersonas);

        assertEquals(expectedRes,club.plazasLibres("Ejemplo"));
    }

    @Test
    public void matricular_ActividadNULL_ReturnThrow() throws ClubException {
        String actividad = null;
        int npersonas = 1;
        Grupo g = new Grupo("Ejemplo", "NULO", 12, 10, 3.2);
        club.anyadirActividad(g);

        assertThrows(ClubException.class,()->club.matricular(actividad,npersonas));
    }

    @Test
    public void matricular_ActividadNoExiste_ReturnThrow() throws ClubException {
        String actividad = "Ejemplo";
        int npersonas = 1;
        Grupo g = new Grupo("Ejemplo", "NULO", 12, 10, 3.2);
        club.anyadirActividad(g);

        assertThrows(ClubException.class,()->club.matricular(actividad,npersonas));
    }

    @Test
    public void matricular_RepartirPersonas_ReturnThrow() throws ClubException {
        String actividad = "Ejemplo";
        int npersonas = 2;
        int expectedRes = 0;
        Grupo g = new Grupo("Ejemplo", actividad, 12, 11, 3.2);
        Grupo g2 = new Grupo("Ejemplo2", actividad, 12, 11, 3.2);
        club.anyadirActividad(g);
        club.anyadirActividad(g2);

        club.matricular(actividad,npersonas);

        assertEquals(club.plazasLibres(actividad),expectedRes);
    }

    @Test
    public void ingresos_ConGrupos_ReturnExpected() throws ClubException {
        Grupo g = new Grupo("Ejemplo", "Ejemplo", 12, 11, 3.2);
        club.anyadirActividad(g);
        double expectedRes = 3.2 * 11;

        double res = club.ingresos();

        assertEquals(expectedRes,res);
    }

    @Test
    public void ingresos_SinGrupos_Return0() {
        double expectedRes = 0;

        double res = club.ingresos();

        assertEquals(expectedRes,res);
    }

    @Test
    public void toString_ReturnTrue() throws ClubException {
        Grupo g = new Grupo("Ejemplo", "Ejemplo", 12, 11, 3.2);
        club.anyadirActividad(g);
        String expectedRes = "Ejemplo --> [ (Ejemplo - Ejemplo - 3.2 euros - P:12 - M:11) ]";

        String res = club.toString();

        assertEquals(res,expectedRes);
    }
}

