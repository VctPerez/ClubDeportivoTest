package clubdeportivo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class GrupoTest { // Hecho Por David Bueno Carmona & Victor Perez Armenta

    @Test
    public void GrupoConstructor_nPlazasNegativaOCero_LanzaClubException() {
        assertThrows(ClubException.class,
                () -> new Grupo("test", "test", -1, 0, 1),
                "Con plazas negativas, se deberia lanzar una ClubException.");
        assertThrows(ClubException.class,
                () -> new Grupo("test", "test", 0, 0, 1),
                "Con cero plazas, se deberia lanzar una ClubException.");
    }

    @Test
    public void GrupoConstructor_matriculadosNegativos_LanzaClubException() {
        assertThrows(ClubException.class,
                () -> new Grupo("test", "test", 1, -1, 1),
                "Con matriculados negativos, se deberia lanzar una ClubException");
    }

    @Test
    public void GrupoConstructor_tarifaNegativaOCero_LanzaClubException() {
        assertThrows(ClubException.class,
                () -> new Grupo("test", "test", 1, 0, -1),
                "Con tarifa negativa, se deberia lanzar una ClubException.");
        assertThrows(ClubException.class,
                () -> new Grupo("test", "test", 1, 0, 0),
                "Con tarifa cero, se deberia lanzar una ClubException.");
    }

    @Test
    public void GrupoContructor_MatriculadosMayorQueNPlazas_ThrowsClubException() {
        assertThrows(ClubException.class,
                () -> new Grupo("test", "test", 1, 2, 1),
                "Con matriculados > NPlazas, deberia lanzar CLubException.");
    }

    @Test
    public void GrupoConstructor_ParametrosValidos_NoLanzaClubException() {
        assertDoesNotThrow(() -> new Grupo("test", "test", 5, 2, 2),
                "Este test no deberia lanzar ninguna excepcion");
    }

    @Test
    public void GrupoConstructor_NombreNulo_ThrowsClubException() {
        assertThrows(ClubException.class,() -> new Grupo(null, "test", 5, 2, 2),
                "Este test no deberia lanzar ninguna excepcion");
    }

    @Test
    public void GrupoConstructor_ActividadNula_ThrowsClubException() {
        assertThrows(ClubException.class,() -> new Grupo("test", null, 5, 2, 2),
                "Este test no deberia lanzar ninguna excepcion");
    }

    @Test
    public void GetCodigo_DevuelveCodigo() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);
        String codigo;

        codigo = g.getCodigo();

        assertEquals("test", codigo, "El codigo debe ser: test.");
    }

    @Test
    public void GetActividad_DevuelveActividad() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);
        String actividad;

        actividad = g.getActividad();

        assertEquals("test", actividad, "La actividad debe ser: test.");
    }

    @Test
    public void GetPlazas_DevuelveNumeroDePlazas() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);
        int plazas;

        plazas = g.getPlazas();

        assertEquals(5, plazas, "El numero de plazas debe ser: 5.");
    }

    @Test
    public void GetMatriculados_DevuelveNumeroDeMatriculados() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);
        int matriculados;

        matriculados = g.getMatriculados();

        assertEquals(2, matriculados, "El numero de matriculados debe ser: 2.");
    }

    @Test
    public void GetTarifa_DevuelveTarifa() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);
        double tarifa;

        tarifa = g.getTarifa();

        assertEquals(2, tarifa, "La tarifa debe ser: 2.");
    }

    @Test
    public void PlazasLibres_DevuelvePlazasLibres() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);
        int plazasLibres;

        plazasLibres = g.plazasLibres();

        assertEquals(3, plazasLibres, "El codigo debe ser: test.");
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

    @Test
    public void Matricular_NoHayPlazasLibres_LanzaClubException() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 5, 2);

        assertThrows(ClubException.class,
                () -> g.matricular(1),
                "Sin plazas libres para matircular, deberia lanzar una ClubException.");
    }

    @Test
    public void Matricular_nNegativo_LanzaClubException() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);

        assertThrows(ClubException.class,
                () -> g.matricular(-1),
                "Con n negativo, deberia lanzar una ClubException.");
    }

    @Test
    public void Matricular_nCero_LanzaClubException() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 5, 2);

        assertThrows(ClubException.class,
                () -> g.matricular(0),
                "Sin plazas libres para matircular, deberia lanzar una ClubException.");
    }

    @Test
    public void Matricular_ParametroValido_AnyadeLosMatriculados() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);

        g.matricular(2);

        assertNotEquals(2, g.getMatriculados(), "El numero de matriculados no debe ser 2.");
        assertEquals(4, g.getMatriculados(), "El numero de matriculados deberia ser 4.");
    }

    @Test
    public void ToString_ReturnsCorrectFormatString() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);

        assertEquals("(test - test - 2.0 euros - P:5 - M:2)", g.toString(),
                "Los string deben coincidir");
    }

    @Test
    public void Equals_CodigoDiferente_DevuelveFalso() throws ClubException {
        Grupo g1 = new Grupo("test1", "test", 5, 2, 2);
        Grupo g2 = new Grupo("test2", "test", 5, 2, 2);

        assertNotEquals(g1, g2);
    }

    @Test
    public void Equals_ActividadDiferente_DevuelveFalso() throws ClubException {
        Grupo g1 = new Grupo("test", "test1", 5, 2, 2);
        Grupo g2 = new Grupo("test", "test2", 5, 2, 2);

        assertNotEquals(g1, g2);
    }

    @Test
    public void Equals_MismoCodigoMismaActividad_DevuelveTrue() throws ClubException {
        Grupo g1 = new Grupo("Test", "test", 5, 2, 2);
        Grupo g2 = new Grupo("tesT", "tEsT", 5, 2, 2);

        assertEquals(g1, g2);
    }

    @Test
    public void Equals_DistintoTipo_DevuelveFalso() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);
        String string = "string";

        assertNotEquals(g, string);
    }

    @Test
    public void Hashcode_DevuelveHashcodeCodigoYActividad() throws ClubException {
        Grupo g = new Grupo("test", "test", 5, 2, 2);

        assertEquals("TEST".hashCode() * 2, g.hashCode());
    }


}
