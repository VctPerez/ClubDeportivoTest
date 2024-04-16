package org.mps.ronqi2;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mps.dispositivo.DispositivoSilver;


public class RonQI2SilverTest {

    @Mock
    DispositivoSilver mockDispositivo;
    RonQI2 ronQI2Silver;

    @BeforeEach
    void setUp() {
        mockDispositivo = mock(DispositivoSilver.class);
        ronQI2Silver = new RonQI2Silver();
    }
    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
     * el método inicializar de ronQI2 o sus subclases, 
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */
    @Nested
    class Inicializar{

        @Test
        @DisplayName("Lanza excepción no hay sensor.")
        public void inicializar_sensorNulo_lanzaException(){
            assertThrows(Exception.class, ()->{
                ronQI2Silver.inicializar();
            });
        }

        @Test
        @DisplayName("Devuelve false si el sensor de presion no conecta.")
        public void inicializar_sensorPresionNoConecta_devuelveFalse() throws Exception {

            DispositivoSilver dispositivoSilver = mock(DispositivoSilver.class);
            when(dispositivoSilver.conectarSensorPresion()).thenReturn(false);
            when(dispositivoSilver.conectarSensorSonido()).thenReturn(false);
            when(dispositivoSilver.conectarSensorPresion()).thenReturn(false);
            when(dispositivoSilver.conectarSensorSonido()).thenReturn(false);
            ronQI2Silver.anyadirDispositivo(dispositivoSilver);

            boolean inicializado = ronQI2Silver.inicializar();

            assertFalse(inicializado, "RonQi2Silver no debería haber sido inicializado");

        }

        @Test
        @DisplayName("Devuelve false si el sensor de sonido no conecta.")
        public void inicializar_sensorSonidoNoConecta_devuelveFalse() throws Exception {
            DispositivoSilver dispositivoSilver = mock(DispositivoSilver.class);
            when(dispositivoSilver.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilver.conectarSensorSonido()).thenReturn(false);
            when(dispositivoSilver.configurarSensorPresion()).thenReturn(false);
            when(dispositivoSilver.configurarSensorSonido()).thenReturn(false);
            ronQI2Silver.anyadirDispositivo(dispositivoSilver);

            boolean inicializado = ronQI2Silver.inicializar();

            assertFalse(inicializado, "RonQi2Silver no debería haber sido inicializado");
        }

        @Test
        @DisplayName("Devuelve false si ninguno de los dos sensores se conceta.")
        public void inicializar_conectarSensoresEsFalso_devuelveFalse() throws Exception {
            DispositivoSilver dispositivoSilver = mock(DispositivoSilver.class);
            when(dispositivoSilver.conectarSensorPresion()).thenReturn(false);
            when(dispositivoSilver.conectarSensorSonido()).thenReturn(true);
            when(dispositivoSilver.configurarSensorPresion()).thenReturn(true);
            when(dispositivoSilver.configurarSensorSonido()).thenReturn(false);
            ronQI2Silver.anyadirDispositivo(dispositivoSilver);

            boolean inicializado = ronQI2Silver.inicializar();

            assertFalse(inicializado, "RonQi2Silver no debería haber sido inicializado");
        }

        @Test
        @DisplayName("Devuelve false si el sensor de presion no se configura.")
        public void inicializar_configurarSensorPresionEsFalso_devuelveFalse() throws Exception {
            DispositivoSilver dispositivoSilver = mock(DispositivoSilver.class);
            when(dispositivoSilver.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilver.conectarSensorSonido()).thenReturn(true);
            when(dispositivoSilver.configurarSensorPresion()).thenReturn(false);
            when(dispositivoSilver.configurarSensorSonido()).thenReturn(true);
            ronQI2Silver.anyadirDispositivo(dispositivoSilver);

            boolean inicializado = ronQI2Silver.inicializar();

            assertFalse(inicializado, "RonQi2Silver no debería haber sido inicializado");
        }

        @Test
        @DisplayName("Devuelve false si el sensor sonido no se configura.")
        public void inicializar_configurarSensorSonidoEsFalso_devuelveFalse() throws Exception {
            DispositivoSilver dispositivoSilver = mock(DispositivoSilver.class);
            when(dispositivoSilver.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilver.conectarSensorSonido()).thenReturn(true);
            when(dispositivoSilver.configurarSensorPresion()).thenReturn(true);
            when(dispositivoSilver.configurarSensorSonido()).thenReturn(false);
            ronQI2Silver.anyadirDispositivo(dispositivoSilver);

            boolean inicializado = ronQI2Silver.inicializar();

            assertFalse(inicializado, "RonQi2Silver no debería haber sido inicializado");
        }

        @Test
        @DisplayName("Devuelve false si ninguno de los dos sensores se configura.")
        public void inicializar_configurarSensoresEsFalso_devuelveFalse() throws Exception {
            DispositivoSilver dispositivoSilver = mock(DispositivoSilver.class);
            when(dispositivoSilver.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilver.conectarSensorSonido()).thenReturn(true);
            when(dispositivoSilver.configurarSensorPresion()).thenReturn(false);
            when(dispositivoSilver.configurarSensorSonido()).thenReturn(false);
            ronQI2Silver.anyadirDispositivo(dispositivoSilver);

            boolean inicializado = ronQI2Silver.inicializar();

            assertFalse(inicializado, "RonQi2Silver no debería haber sido inicializado");
        }

        @Test
        @DisplayName("Devuelve true si los sensores se conectan y se configuran.")
        public void inicializar_conectarYConfigurarSonVerdadero_devuelveTrue() throws Exception {
            DispositivoSilver dispositivoSilver = mock(DispositivoSilver.class);
            when(dispositivoSilver.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilver.conectarSensorSonido()).thenReturn(true);
            when(dispositivoSilver.configurarSensorPresion()).thenReturn(true);
            when(dispositivoSilver.configurarSensorSonido()).thenReturn(true);
            ronQI2Silver.anyadirDispositivo(dispositivoSilver);

            boolean inicializado = ronQI2Silver.inicializar();

            assertTrue(inicializado, "RonQi2Silver debería haber sido inicializado");
            verify(dispositivoSilver).configurarSensorSonido();
            verify(dispositivoSilver).configurarSensorPresion();
        }

    }


    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
     * se llama una sola vez al configurar de cada sensor.
     */

    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */
    @Nested
    @DisplayName("Reconectar")
    class Reconectar {
        @Test
        @DisplayName("Devuelve falso si el dispositivo ya está conectado.")
        void reconectar_reconexionDisposotivoYaConectado_devuelveFalse() throws Exception {
            ronQI2Silver.anyadirDispositivo(mockDispositivo);
            when(mockDispositivo.estaConectado()).thenReturn(true);

            boolean res = ronQI2Silver.reconectar();

            assertFalse(res);

        }

        @Test
        @DisplayName("Devuelve verdadero si el dispositivo no está conectado.")
        void reconectar_reconexionDisposotivoNoConectado_devuelveTrue() throws Exception {
            ronQI2Silver.anyadirDispositivo(mockDispositivo);
            when(mockDispositivo.estaConectado()).thenReturn(false);
            when(mockDispositivo.conectarSensorPresion()).thenReturn(true);
            when(mockDispositivo.conectarSensorSonido()).thenReturn(true);

            boolean res = ronQI2Silver.reconectar();

            assertTrue(res);
        }

        @Test
        @DisplayName("Lanza excepción si no hay dispositivo.")
        void reconectar_reconexionSinDispositivo_lanzaException() throws Exception {
            when(mockDispositivo.estaConectado()).thenReturn(false);
            when(mockDispositivo.conectarSensorPresion()).thenReturn(true);
            when(mockDispositivo.conectarSensorSonido()).thenReturn(true);

            assertThrows(Exception.class, () -> ronQI2Silver.reconectar());

        }

        @Test
        @DisplayName("Devuelve falso si fallan ambos sensores.")
        void reconectar_reconexionDispositivoFalloAmbosSensores_devuelveFalse() throws Exception {
            ronQI2Silver.anyadirDispositivo(mockDispositivo);
            when(mockDispositivo.estaConectado()).thenReturn(false);
            when(mockDispositivo.conectarSensorPresion()).thenReturn(false);
            when(mockDispositivo.conectarSensorSonido()).thenReturn(false);

            boolean res = ronQI2Silver.reconectar();

            assertFalse(res);

        }

        @Test
        @DisplayName("Devuelve falso si falla el sensor de presion.")
        void reconectar_reconexionDispositivoFalloSensorPresion_lanzaException() throws Exception {
            ronQI2Silver.anyadirDispositivo(mockDispositivo);
            when(mockDispositivo.estaConectado()).thenReturn(false);
            when(mockDispositivo.conectarSensorPresion()).thenReturn(false);
            when(mockDispositivo.conectarSensorSonido()).thenReturn(true);

            boolean res = ronQI2Silver.reconectar();

            assertFalse(res);
        }

        @Test
        @DisplayName("Devuelve falso si falla el sensor de sonido.")
        void reconectar_reconexionDispositivoFalloSensorSonido_lanzaException() throws Exception {
            ronQI2Silver.anyadirDispositivo(mockDispositivo);
            when(mockDispositivo.estaConectado()).thenReturn(false);
            when(mockDispositivo.conectarSensorPresion()).thenReturn(true);
            when(mockDispositivo.conectarSensorSonido()).thenReturn(false);

            boolean res = ronQI2Silver.reconectar();

            assertFalse(res);
        }

    }

    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     */
    @Nested
    class evaluarApneaSuenyo{
        @ParameterizedTest
        @CsvSource({"1", "5", "6"})
        @DisplayName("Devuelve true si el promedio de las últimas 5 lecturas de ambos sensores superan los límites establecidos")
        void evaluarApneaSuenyo_mediaLecturasSuperanLimites_devuelveTrue(int numLecturas) throws Exception {
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.estaConectado()).thenReturn(true);
            when(disp.leerSensorPresion()).thenReturn(20f);
            when(disp.leerSensorSonido()).thenReturn(30f);

            ronQI2Silver.anyadirDispositivo(disp);
            for(int i = 0; i < numLecturas; i++){
                ronQI2Silver.obtenerNuevaLectura();
            }
            assertTrue(ronQI2Silver.evaluarApneaSuenyo());
        }

        @ParameterizedTest
        @CsvSource({"1", "5", "6"})
        @DisplayName("Devuelve false si el promedio de las últimas 5 lecturas de ambos sensores no superan los límites establecidos")
        void evaluarApneaSuenyo_mediaLecturasNoSuperanLimites_devuelveFalse(int numLecturas) throws Exception {
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.estaConectado()).thenReturn(true);
            when(disp.leerSensorPresion()).thenReturn(10f);
            when(disp.leerSensorSonido()).thenReturn(10f);

            ronQI2Silver.anyadirDispositivo(disp);
            for(int i = 0; i < numLecturas; i++){
                ronQI2Silver.obtenerNuevaLectura();
            }
            assertFalse(ronQI2Silver.evaluarApneaSuenyo());
        }

        @ParameterizedTest
        @CsvSource({"1", "5", "6"})
        @DisplayName("Devuelve false si el promedio de las últimas 5 lecturas de presion no superan los límites establecidos")
        void evaluarApneaSuenyo_mediaLecturasPresionNoSuperanLimites_devuelveFalse(int numLecturas) throws Exception {
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.estaConectado()).thenReturn(true);
            when(disp.leerSensorPresion()).thenReturn(10f);
            when(disp.leerSensorSonido()).thenReturn(30f);

            ronQI2Silver.anyadirDispositivo(disp);
            for(int i = 0; i < numLecturas; i++){
                ronQI2Silver.obtenerNuevaLectura();
            }
            assertFalse(ronQI2Silver.evaluarApneaSuenyo());
        }

        @ParameterizedTest
        @CsvSource({"1", "5", "6"})
        @DisplayName("Devuelve false si el promedio de las últimas 5 lecturas de sonido no superan los límites establecidos")
        void evaluarApneaSuenyo_mediaLecturasSonidoNoSuperanLimites_devuelveFalse(int numLecturas) throws Exception {
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.estaConectado()).thenReturn(true);
            when(disp.leerSensorPresion()).thenReturn(20f);
            when(disp.leerSensorSonido()).thenReturn(10f);

            ronQI2Silver.anyadirDispositivo(disp);
            for(int i = 0; i < numLecturas; i++){
                ronQI2Silver.obtenerNuevaLectura();
            }
            assertFalse(ronQI2Silver.evaluarApneaSuenyo());
        }

        @Test
        @DisplayName("Devuelve false si no se han realizado lecturas")
        void evaluarApneaSuenyo_ceroLecturas_devuelveFalse() throws Exception {
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.estaConectado()).thenReturn(true);
            when(disp.leerSensorPresion()).thenReturn(20f);
            when(disp.leerSensorSonido()).thenReturn(30f);

            ronQI2Silver.anyadirDispositivo(disp);

            assertFalse(ronQI2Silver.evaluarApneaSuenyo());
        }

        @Test
        @DisplayName("Devuelve false si el dispositivo no esta conectado")
        void evaluarApneaSuenyo_dispositvoDesconectado_devuelveFalse() throws Exception {
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.estaConectado()).thenReturn(false);
            when(disp.leerSensorPresion()).thenReturn(20f);
            when(disp.leerSensorSonido()).thenReturn(30f);

            ronQI2Silver.anyadirDispositivo(disp);
            ronQI2Silver.obtenerNuevaLectura();

            assertFalse(ronQI2Silver.evaluarApneaSuenyo());
        }
    }

     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */

    @Nested
    class obtenerNuevaLectura{
        @DisplayName("Almacena las lecturas de los sensores si tiene un dispositivo conectado")
        @Test
        void obtenerNuevaLectura_dispositivoConectado_almacenaLecturasDeSensores() throws Exception {
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.estaConectado()).thenReturn(true);
            ronQI2Silver.anyadirDispositivo(disp);

            ronQI2Silver.obtenerNuevaLectura();

            verify(disp).leerSensorPresion();
            verify(disp).leerSensorSonido();
        }

        @DisplayName("No almacena las lecturas de los sensores si tiene un dispositivo desconectado")
        @Test
        void obtenerNuevaLectura_dispositivoNoConectado_noAlmacenaLecturasDeSensores() throws Exception {
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.estaConectado()).thenReturn(false);
            ronQI2Silver.anyadirDispositivo(disp);

            ronQI2Silver.obtenerNuevaLectura();

            verify(disp, never()).leerSensorPresion();
            verify(disp, never()).leerSensorSonido();
        }

        @DisplayName("Lanza excepcion si no tiene un dispositivo")
        @Test
        void obtenerNuevaLectura_dispositivoNulo_lanzaException(){
            assertThrows(Exception.class, ()->{
               ronQI2Silver.obtenerNuevaLectura();
            });
        }
    }

    @Nested
    class anyadirDispositivo{
        @Test
        @DisplayName("Anyade el dispositivo si no es nulo.")
        void anyadirDispositivo_dispositivoNoNulo_noLanzaException(){
            assertDoesNotThrow(() -> ronQI2Silver.anyadirDispositivo(mockDispositivo));
        }

        @Test
        @DisplayName("Lanza excepción si el dispositivo es nulo.")
        void anyadirDispositivo_dispositivoNulo_lanzaException(){
            assertThrows(Exception.class, () -> ronQI2Silver.anyadirDispositivo(null));
        }
    }

    @Nested
    class estaConectado{
        @Test
        @DisplayName("Devuelve verdadero si el dispositivo está conectado.")
        void estaConectado_dispositivoExistenteConectado_devuelveTrue() throws Exception{
            ronQI2Silver.anyadirDispositivo(mockDispositivo);
            when(mockDispositivo.estaConectado()).thenReturn(true);

            boolean res = ronQI2Silver.estaConectado();

            assertTrue(res);

        }

        @Test
        @DisplayName("Devuelve falso si el dispositivo no está conectado.")
        void estaConectado_dispositivoExistenteDesconectado_devuelveFalse() throws Exception{
            ronQI2Silver.anyadirDispositivo(mockDispositivo);
            when(mockDispositivo.estaConectado()).thenReturn(false);

            boolean res = ronQI2Silver.estaConectado();

            assertFalse(res);

        }

        @Test
        @DisplayName("Lanza excepción si no hay dispositivo.")
        void estaConectado_dispositivoNulo_lanzaException() {

            assertThrows(Exception.class, () -> ronQI2Silver.estaConectado());

        }
    }
}
