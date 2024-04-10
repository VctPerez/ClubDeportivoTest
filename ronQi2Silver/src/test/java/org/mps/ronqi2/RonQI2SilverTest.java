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
    RonQI2 sujeto;

    @BeforeEach
    void setUp() {
        mockDispositivo = mock(DispositivoSilver.class);
        sujeto = new RonQI2Silver();
    }
    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
     * el método inicializar de ronQI2 o sus subclases, 
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */
    @Nested
    class Inicializar{
        RonQI2Silver ronQI2Silver;
        @BeforeEach
        public void initTests(){
            ronQI2Silver = new RonQI2Silver();
        }

        @Test
        @DisplayName("Devuelve false si el sensor de presion no conecta.")
        public void inicializar_sensorPresionNoConecta_returnFalse(){

            DispositivoSilver dispositivoSilver = mock(DispositivoSilver.class);
            when(dispositivoSilver.conectarSensorPresion()).thenReturn(false);
            ronQI2Silver.anyadirDispositivo(dispositivoSilver);

            boolean inicializado = ronQI2Silver.inicializar();

            assertFalse(inicializado, "RonQi2Silver no debería haber sido inicializado");

        }

        @Test
        @DisplayName("Devuelve false si el sensor de sonido no conecta.")
        public void inicializar_sensorSonidoNoConecta_returnFalse(){
            DispositivoSilver dispositivoSilver = mock(DispositivoSilver.class);
            when(dispositivoSilver.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilver.conectarSensorSonido()).thenReturn(false);
            ronQI2Silver.anyadirDispositivo(dispositivoSilver);

            boolean inicializado = ronQI2Silver.inicializar();

            assertFalse(inicializado, "RonQi2Silver no debería haber sido inicializado");
        }

        @Test
        @DisplayName("Devuelve false si el sensor de presion no se configura.")
        public void inicializar_configurarSensorPresionEsFalso_returnFalse(){
            DispositivoSilver dispositivoSilver = mock(DispositivoSilver.class);
            when(dispositivoSilver.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilver.conectarSensorSonido()).thenReturn(true);
            when(dispositivoSilver.configurarSensorPresion()).thenReturn(false);
            ronQI2Silver.anyadirDispositivo(dispositivoSilver);

            boolean inicializado = ronQI2Silver.inicializar();

            assertFalse(inicializado, "RonQi2Silver no debería haber sido inicializado");
        }

        @Test
        @DisplayName("Devuelve false si el sensor sonido no se configura.")
        public void inicializar_configurarSensorSonidoEsFalso_returnFalse(){
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
        @DisplayName("Devuelve true si los sensores se conectan y se configuran.")
        public void inicializar_conectarYConfigurarSonVerdadero_returnTrue(){
            DispositivoSilver dispositivoSilver = mock(DispositivoSilver.class);
            when(dispositivoSilver.conectarSensorPresion()).thenReturn(true);
            when(dispositivoSilver.conectarSensorSonido()).thenReturn(true);
            when(dispositivoSilver.configurarSensorPresion()).thenReturn(true);
            when(dispositivoSilver.configurarSensorSonido()).thenReturn(true);
            ronQI2Silver.anyadirDispositivo(dispositivoSilver);

            boolean inicializado = ronQI2Silver.inicializar();

            assertTrue(inicializado, "RonQi2Silver debería haber sido inicializado");
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
        @DisplayName("Reconectar con dispositivo ya conectado")
        void reconectar_reconexionDisposotivoYaConectado_returnFalse() throws Exception {
            sujeto.anyadirDispositivo(mockDispositivo);
            when(mockDispositivo.estaConectado()).thenReturn(true);

            boolean res = sujeto.reconectar();

            assertFalse(res);

        }

        @Test
        @DisplayName("Reconectar con dispositivo no conectado")
        void reconectar_reconexionDisposotivoNoConectado_returnTrue() throws Exception {
            sujeto.anyadirDispositivo(mockDispositivo);
            when(mockDispositivo.estaConectado()).thenReturn(false);
            when(mockDispositivo.conectarSensorPresion()).thenReturn(true);
            when(mockDispositivo.conectarSensorSonido()).thenReturn(true);

            boolean res = sujeto.reconectar();

            assertTrue(res);

        }

        @Test
        @DisplayName("Reconectar sin dispositivo")
        void reconectar_reconexionSinDispositivo_throwsException() throws Exception {
            when(mockDispositivo.estaConectado()).thenReturn(false);
            when(mockDispositivo.conectarSensorPresion()).thenReturn(true);
            when(mockDispositivo.conectarSensorSonido()).thenReturn(true);

            assertThrows(Exception.class, () -> sujeto.reconectar());

        }

        @Test
        @DisplayName("Reconectar fallo en ambos sensores")
        void reconectar_reconexionDispositivoFalloAmbosSensores_returnFalse() throws Exception {
            sujeto.anyadirDispositivo(mockDispositivo);
            when(mockDispositivo.estaConectado()).thenReturn(false);
            when(mockDispositivo.conectarSensorPresion()).thenReturn(false);
            when(mockDispositivo.conectarSensorSonido()).thenReturn(false);

            boolean res = sujeto.reconectar();

            assertFalse(res);

        }

        @Test
        @DisplayName("Reconectar fallo en primer sensor")
        void reconectar_reconexionDispositivoFalloPrimerSensor_throwsException() throws Exception {
            sujeto.anyadirDispositivo(mockDispositivo);
            when(mockDispositivo.estaConectado()).thenReturn(false);
            when(mockDispositivo.conectarSensorPresion()).thenReturn(false);
            when(mockDispositivo.conectarSensorSonido()).thenReturn(true);

            boolean res = sujeto.reconectar();

            assertFalse(res);

        }

        @Test
        @DisplayName("Reconectar fallo en segundo sensor")
        void reconectar_reconexionDispositivoFalloSegundoSensor_throwsException() throws Exception {
            sujeto.anyadirDispositivo(mockDispositivo);
            when(mockDispositivo.estaConectado()).thenReturn(false);
            when(mockDispositivo.conectarSensorPresion()).thenReturn(true);
            when(mockDispositivo.conectarSensorSonido()).thenReturn(false);

            boolean res = sujeto.reconectar();

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
        void evaluarApneaSuenyo_mediaLecturasSuperanLimites_devuelveTrue(int numLecturas){
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.leerSensorPresion()).thenReturn(20f);
            when(disp.leerSensorSonido()).thenReturn(30f);

            sujeto.anyadirDispositivo(disp);
            for(int i = 0; i < numLecturas; i++){
                sujeto.obtenerNuevaLectura();
            }
            assertTrue(sujeto.evaluarApneaSuenyo());
        }

        @ParameterizedTest
        @CsvSource({"1", "5", "6"})
        @DisplayName("Devuelve false si el promedio de las últimas 5 lecturas de ambos sensores no superan los límites establecidos")
        void evaluarApneaSuenyo_mediaLecturasNoSuperanLimites_devuelveFalse(int numLecturas){
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.leerSensorPresion()).thenReturn(10f);
            when(disp.leerSensorSonido()).thenReturn(10f);

            sujeto.anyadirDispositivo(disp);
            for(int i = 0; i < numLecturas; i++){
                sujeto.obtenerNuevaLectura();
            }
            assertFalse(sujeto.evaluarApneaSuenyo());
        }

        @ParameterizedTest
        @CsvSource({"1", "5", "6"})
        @DisplayName("Devuelve false si el promedio de las últimas 5 lecturas de presion no superan los límites establecidos")
        void evaluarApneaSuenyo_mediaLecturasPresionNoSuperanLimites_devuelveFalse(int numLecturas){
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.leerSensorPresion()).thenReturn(10f);
            when(disp.leerSensorSonido()).thenReturn(30f);

            sujeto.anyadirDispositivo(disp);
            for(int i = 0; i < numLecturas; i++){
                sujeto.obtenerNuevaLectura();
            }
            assertFalse(sujeto.evaluarApneaSuenyo());
        }

        @ParameterizedTest
        @CsvSource({"1", "5", "6"})
        @DisplayName("Devuelve false si el promedio de las últimas 5 lecturas de sonido no superan los límites establecidos")
        void evaluarApneaSuenyo_mediaLecturasSonidoNoSuperanLimites_devuelveFalse(int numLecturas){
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.leerSensorPresion()).thenReturn(20f);
            when(disp.leerSensorSonido()).thenReturn(10f);

            sujeto.anyadirDispositivo(disp);
            for(int i = 0; i < numLecturas; i++){
                sujeto.obtenerNuevaLectura();
            }
            assertFalse(sujeto.evaluarApneaSuenyo());
        }

        @Test
        @DisplayName("Devuelve false si no se han realizado lecturas")
        void evaluarApneaSuenyo_ceroLecturas_devuelveFalse(){
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.leerSensorPresion()).thenReturn(20f);
            when(disp.leerSensorSonido()).thenReturn(30f);

            sujeto.anyadirDispositivo(disp);

            assertFalse(sujeto.evaluarApneaSuenyo());
        }
    }

     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */
}
