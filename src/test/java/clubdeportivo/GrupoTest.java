package clubdeportivo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

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




}
