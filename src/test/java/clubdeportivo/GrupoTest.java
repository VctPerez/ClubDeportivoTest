package clubdeportivo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class GrupoTest {

    @Test
    public void GrupoConstructor_nPlazasNegativaOCero_LanzaClubException(){
        assertThrows(ClubException.class,
                () -> new Grupo("test", "test", -1, 0, 1),
                "Con plazas negativas, se deberia lanzar una ClubException.");
        assertThrows(ClubException.class,
                () -> new Grupo("test", "test", 0, 0, 1),
                "Con cero plazas, se deberia lanzar una ClubException.");
    }

    @Test
    public void GrupoConstructor_matriculadosNegativos_LanzaClubException(){
        assertThrows(ClubException.class,
                () -> new Grupo("test", "test", 1, -1, 1),
                "Con matriculados negativos, se deberia lanzar una ClubException");
    }

    @Test
    public void GrupoConstructor_tarifaNegativaOCero_LanzaClubException(){
        assertThrows(ClubException.class,
                () -> new Grupo("test", "test", 1, 0, -1),
                "Con tarifa negativa, se deberia lanzar una ClubException.");
        assertThrows(ClubException.class,
                () -> new Grupo("test", "test", 1, 0, 0),
                "Con tarifa cero, se deberia lanzar una ClubException.");
    }

    @Test
    public void GrupoContructor_MatriculadosMayorQueNPlazas_ThrowsClubException(){
        assertThrows(ClubException.class,
                () -> new Grupo("test", "test", 1, 2, 1),
                "Con matriculados > NPlazas, deberia lanzar CLubException.");
    }

    @Test
    public void GrupoConstructor_ParametrosValidos_NoLanzaClubException(){
        assertDoesNotThrow(() -> new Grupo("test", "test", 5,2,2),
                "Este test no deberia lanzar ninguna excepcion");
    }

    @Test
    public void GetCodigo_DevuelveCodigo() throws ClubException {

        Grupo g = new Grupo("test", "test", 5, 2, 2);

        assertEquals("test", g.getCodigo(), "El codigo debe ser: test.");
    }

    @Test
    public void GetActividad_DevuelveActividad() throws ClubException {

        Grupo g = new Grupo("test", "test", 5, 2, 2);

        assertEquals("test", g.getActividad(), "La actividad debe ser: test.");
    }

    @Test
    public void GetPlazas_DevuelveNumeroDePlazas() throws ClubException {

        Grupo g = new Grupo("test", "test", 5, 2, 2);

        assertEquals(5, g.getPlazas(), "El numero de plazas debe ser: 5.");
    }

    @Test
    public void GetMatriculados_DevuelveNumeroDeMatriculados() throws ClubException {

        Grupo g = new Grupo("test", "test", 5, 2, 2);

        assertEquals(2, g.getMatriculados(), "El numero de matriculados debe ser: 2.");
    }

    @Test
    public void GetTarifa_DevuelveTarifa() throws ClubException {

        Grupo g = new Grupo("test", "test", 5, 2, 2);

        assertEquals(2, g.getTarifa(), "La tarifa debe ser: 2.");
    }

    @Test
    public void PlazasLibres_DevuelvePlazasLibres() throws ClubException {

        Grupo g = new Grupo("test", "test", 5, 2, 2);

        assertEquals(3, g.plazasLibres(), "El codigo debe ser: test.");
    }

    @Test
    public void ActualizarPlazas_nNegativo_LanzaClubExcepcion() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);

        assertThrows(ClubException.class,
                () -> g.actualizarPlazas(-2),
                "Con n negativo, deberia lanzar una ClubException");
    }

    @Test
    public void ActualizarPlazas_nCero_LanzaClubExcepcion() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);

        assertThrows(ClubException.class,
                () -> g.actualizarPlazas(0),
                "Con n igual a cero, deberia lanzar una ClubException");
    }

    @Test
    public void ActualizarPlazas_nMenorQueMatriculados_LanzaClubException() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);

        assertThrows(ClubException.class,
                () -> g.actualizarPlazas(1),
                "Con n menor que nMatriculados, deberia lanzar ClubException.");
    }

    @Test
    public void ActualizarPlazas_ParametroValido_CambiaNPlazas() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);

        g.actualizarPlazas(7);

        assertNotEquals(5, g.getPlazas(), "El numero de plazas no deberia ser el mismo");
        assertEquals(7, g.getPlazas(), "El numero de plazas debe haber cambiado a 7");
    }



}
