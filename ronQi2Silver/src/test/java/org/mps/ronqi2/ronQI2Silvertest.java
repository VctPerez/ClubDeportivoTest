package org.mps.ronqi2;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mps.dispositivo.DispositivoSilver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ronQI2Silvertest {
    RonQI2Silver ron;

    @BeforeEach
    void setup(){
        ron = new RonQI2Silver();
    }
    
    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
     * el método inicializar de ronQI2 o sus subclases, 
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */
    
    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
     * se llama una sola vez al configurar de cada sensor.
     */

    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */

    
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

            ron.anyadirDispositivo(disp);
            for(int i = 0; i < numLecturas; i++){
                ron.obtenerNuevaLectura();
            }
            assertTrue(ron.evaluarApneaSuenyo());
        }

        @ParameterizedTest
        @CsvSource({"1", "5", "6"})
        @DisplayName("Devuelve false si el promedio de las últimas 5 lecturas de ambos sensores no superan los límites establecidos")
        void evaluarApneaSuenyo_mediaLecturasNoSuperanLimites_devuelveFalse(int numLecturas){
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.leerSensorPresion()).thenReturn(10f);
            when(disp.leerSensorSonido()).thenReturn(10f);

            ron.anyadirDispositivo(disp);
            for(int i = 0; i < numLecturas; i++){
                ron.obtenerNuevaLectura();
            }
            assertFalse(ron.evaluarApneaSuenyo());
        }

        @ParameterizedTest
        @CsvSource({"1", "5", "6"})
        @DisplayName("Devuelve false si el promedio de las últimas 5 lecturas de presion no superan los límites establecidos")
        void evaluarApneaSuenyo_mediaLecturasPresionNoSuperanLimites_devuelveFalse(int numLecturas){
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.leerSensorPresion()).thenReturn(10f);
            when(disp.leerSensorSonido()).thenReturn(30f);

            ron.anyadirDispositivo(disp);
            for(int i = 0; i < numLecturas; i++){
                ron.obtenerNuevaLectura();
            }
            assertFalse(ron.evaluarApneaSuenyo());
        }

        @ParameterizedTest
        @CsvSource({"1", "5", "6"})
        @DisplayName("Devuelve false si el promedio de las últimas 5 lecturas de sonido no superan los límites establecidos")
        void evaluarApneaSuenyo_mediaLecturasSonidoNoSuperanLimites_devuelveFalse(int numLecturas){
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.leerSensorPresion()).thenReturn(20f);
            when(disp.leerSensorSonido()).thenReturn(10f);

            ron.anyadirDispositivo(disp);
            for(int i = 0; i < numLecturas; i++){
                ron.obtenerNuevaLectura();
            }
            assertFalse(ron.evaluarApneaSuenyo());
        }

        @Test
        @DisplayName("Devuelve false si no se han realizado lecturas")
        void evaluarApneaSuenyo_ceroLecturas_devuelveFalse(){
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.leerSensorPresion()).thenReturn(20f);
            when(disp.leerSensorSonido()).thenReturn(30f);

            ron.anyadirDispositivo(disp);

            assertFalse(ron.evaluarApneaSuenyo());
        }
    }



     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */
}
