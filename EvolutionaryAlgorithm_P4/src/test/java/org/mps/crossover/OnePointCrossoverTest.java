package org.mps.crossover;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OnePointCrossoverTest {

    @Nested
    class Crossover{

        OnePointCrossover onePointCrossover;

        @BeforeEach
        public void initTests(){
            onePointCrossover = new OnePointCrossover();
        }

        @Test
        @DisplayName("Throws an exception if parent1 is null.")
        public void crossover_parent1Null_throwEvolutionaryAlgorithmException(){
            int[] parent1 = null;
            int[] parent2 = {};

            assertThrows(EvolutionaryAlgorithmException.class, () -> onePointCrossover.crossover(parent1, parent2)
                    , "Se deberia haber lanzado una excepccion.");
        }

        @Test
        @DisplayName("Throws an exception if parent2 is null.")
        public void crossover_parent2Null_throwEvolutionaryAlgorithmException(){
            int[] parent1 = {};
            int[] parent2 = null;

            assertThrows(EvolutionaryAlgorithmException.class, ()->onePointCrossover.crossover(parent1, parent2),
                    "Se deberia haber lanzado una excepcion.");
        }

        @Test
        @DisplayName("Throws an exception if parent1 length is 0.")
        public void crossover_parent1Length0_throwEvolutionaryAlgorithmException(){
            int[] parent1 = {1};
            int[] parent2 = {};

            assertThrows(EvolutionaryAlgorithmException.class, () -> onePointCrossover.crossover(parent1, parent2),
                    "Se deberia haber lanzado uan excepcion.");
        }

        @Test
        @DisplayName("Throws an exception if parent1 size is greater than parent2 size.")
        public void crossover_parent2LessParent1_throwEvolutionaryAlgorithmException(){
            int[] parent1 = {1, 2, 3};
            int[] parent2 = {5};

            assertThrows(EvolutionaryAlgorithmException.class, () -> onePointCrossover.crossover(parent1, parent2),
                    "Se deberia haber lanzado una excepcion.");
        }

        @Test
        public void crossover_parentsvValid_returnsOffspring() throws EvolutionaryAlgorithmException {
            int[] parent1 = {1,3,4,5};
            int[] parent2 = {2,4,6,8};
            int[][] offspring;

            offspring = onePointCrossover.crossover(parent1, parent2);

            assertEquals(parent1.length, offspring[0].length, "Deberia tener la misma longitud que el padre");
            assertEquals(2, offspring.length);
        }

        @Test
        public void crossover_parentsSize2_returnsOffspringUsingOnlyFirstFor() throws EvolutionaryAlgorithmException {
            int[] parent1 = {1,2};
            int[] parent2 = {3,4};
            int[][] offspring;

            offspring = onePointCrossover.crossover(parent1, parent2);

            assertEquals(parent1.length, offspring[0].length, "Deberia tener la misma longitud que el padre");
            assertEquals(2, offspring.length);
        }
    }
}
