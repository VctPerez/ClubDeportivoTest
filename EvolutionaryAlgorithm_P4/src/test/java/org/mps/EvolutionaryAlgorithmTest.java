package org.mps;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import org.mps.crossover.CrossoverOperator;
import org.mps.crossover.OnePointCrossover;
import org.mps.mutation.MutationOperator;
import org.mps.mutation.SwapMutation;
import org.mps.selection.SelectionOperator;
import org.mps.selection.TournamentSelection;

import java.lang.reflect.Array;
import java.util.Arrays;


public class EvolutionaryAlgorithmTest {
    EvolutionaryAlgorithm algorithm;
    SelectionOperator selectionOperator;
    MutationOperator mutationOperator;
    CrossoverOperator crossoverOperator;

    @Nested
    class Constructor{
        @Test
        @DisplayName("Constructor with null SelectionOperator throws exception")
        void constructor_nullSelectionOperator_throwsException(){
            selectionOperator = null;
            mutationOperator = new SwapMutation();
            crossoverOperator = new OnePointCrossover();

            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                algorithm = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);
            });
        }

        @Test
        @DisplayName("Constructor with null MutationOperator throws exception")
        void constructor_nullMutationOperator_throwsException() throws EvolutionaryAlgorithmException {
            selectionOperator = new TournamentSelection(1);
            mutationOperator = null;
            crossoverOperator = new OnePointCrossover();

            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                algorithm = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);
            });
        }

        @Test
        @DisplayName("Constructor with null CrossoverOperator throws exception")
        void constructor_nullCrossoverOperator_throwsException() throws EvolutionaryAlgorithmException {
            selectionOperator = new TournamentSelection(1);
            mutationOperator = new SwapMutation();
            crossoverOperator = null;

            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                algorithm = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);
            });
        }

        @Test
        @DisplayName("Constructor with valid arguments builds EvolutiyonaryAlgorithm")
        void constructor_validArguments_buildsEvolutionaryAlgorithm() throws EvolutionaryAlgorithmException {
            selectionOperator = new TournamentSelection(1);
            mutationOperator = new SwapMutation();
            crossoverOperator = new OnePointCrossover();

            assertDoesNotThrow(()->{
                algorithm = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);
            });
            assertEquals(selectionOperator, algorithm.getSelectionOperator());
            assertEquals(mutationOperator, algorithm.getMutationOperator());
            assertEquals(crossoverOperator, algorithm.getCrossoverOperator());
        }
    }


    @BeforeEach
    void setup() throws EvolutionaryAlgorithmException {
        selectionOperator = new TournamentSelection(3);
        mutationOperator = new SwapMutation();
        crossoverOperator = new OnePointCrossover();
        algorithm = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);
    }

    @Nested
    class Getters{
        @Test
        @DisplayName("getSelectionOperator returns correct operator")
        void getSelectionOperator_returnsCorrectOperator(){
            assertEquals(selectionOperator, algorithm.getSelectionOperator());
        }

        @Test
        @DisplayName("getMutationOperator returns correct operator")
        void getMutationOperator_returnsCorrectOperator(){
            assertEquals(mutationOperator, algorithm.getMutationOperator());
        }

        @Test
        @DisplayName("getCrossoverOperator returns correct operator")
        void getCrossoverOperator_returnsCorrectOperator(){
            assertEquals(crossoverOperator, algorithm.getCrossoverOperator());
        }
    }

    @Nested
    class SetSelectionOperator{
        @Test
        @DisplayName("Setting null operator throws EvolutionaryAlgorithmException")
        void setSelectionOperator_nullOperator_throwsEvolutionaryAlgorithmException(){
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                algorithm.setSelectionOperator(null);
            });
        }

        @Test
        @DisplayName("Setting valid operator changes the algorithm's operator")
        void setSelectionOperator_validOperator_changesOperator() throws EvolutionaryAlgorithmException {
            SelectionOperator newSelectionOperator = new TournamentSelection(3);

            algorithm.setSelectionOperator(newSelectionOperator);

            assertEquals(newSelectionOperator, algorithm.getSelectionOperator());
        }
    }

    @Nested
    class SetMutationOperator{
        @Test
        @DisplayName("Setting null operator throws EvolutionaryAlgorithmException")
        void setMutationOperator_nullOperator_throwsEvolutionaryAlgorithmException(){
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                algorithm.setMutationOperator(null);
            });
        }

        @Test
        @DisplayName("Setting valid operator changes the algorithm's operator")
        void setMutationOperator_validOperator_changesOperator() throws EvolutionaryAlgorithmException {
            MutationOperator newMutationOperator = new SwapMutation();

            algorithm.setMutationOperator(newMutationOperator);

            assertEquals(newMutationOperator, algorithm.getMutationOperator());
        }
    }

    @Nested
    class SetCrossoverOperator{
        @Test
        @DisplayName("Setting null operator throws EvolutionaryAlgorithmException")
        void setCrossoverOperator_nullOperator_throwsEvolutionaryAlgorithmException(){
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                algorithm.setCrossoverOperator(null);
            });
        }

        @Test
        @DisplayName("Setting valid operator changes the algorithm's operator")
        void setSelectionOperator_validOperator_changesOperator() throws EvolutionaryAlgorithmException {
            CrossoverOperator newCrossoverOperator = new OnePointCrossover();

            algorithm.setCrossoverOperator(newCrossoverOperator);

            assertEquals(newCrossoverOperator, algorithm.getCrossoverOperator());
        }
    }

    @Nested
    class Optimize{
        @Test
        @DisplayName("Optimize null population throws EvolutionaryAlgorithmException")
        void optimize_nullPopulation_throwsEvolutionaryAlgorithmException(){
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation = algorithm.optimize(null);
            });
        }
        @Test
        @DisplayName("Optimize population with null attributes throws EvolutionaryAlgorithmException")
        void optimize_nullAttributesPopulation_throwsEvolutionaryAlgorithmException(){
            int[][] population = {null, null};
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation = algorithm.optimize(population);
            });
        }
        @Test
        @DisplayName("Optimize empty population throws EvolutionaryAlgorithmException")
        void optimize_emptyPopulation_throwsEvolutionaryAlgorithmException(){
            int[][] population = {};
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation =algorithm.optimize(population);
            });
        }
        @Test
        @DisplayName("Optimize population with empty attributes throws EvolutionaryAlgorithmException")
        void optimize_emptyAttributesPopulation_throwsEvolutionaryAlgorithmException(){
            int[][] population = {{},{}};
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation =algorithm.optimize(population);
            });
        }

        @Test
        @DisplayName("Optimize population with single individual throws EvolutionaryAlgorithmException")
        void optimize_singleIndividualPopulation_returnsPopulationThrowsEvolutionaryAlgorithmException() throws EvolutionaryAlgorithmException {
            int[][] population = {{4,5,6}};

            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation = algorithm.optimize(population);
            });
        }

        @Test
        @DisplayName("Optimize odd population throws EvolutionaryAlgorithmException")
        void optimize_oddPopulation_returnsPopulationThrowsEvolutionaryAlgorithmException() throws EvolutionaryAlgorithmException {
            int[][] population = {{1,2,3}, {4,5,6}, {7,8,9}};

            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation = algorithm.optimize(population);
            });
        }

        @Test
        @DisplayName("Optimize population with different attribute's sizes throws EvolutionaryAlgorithmException")
        void optimize_differentAttributeSizesPopulation_throwsEvolutionaryAlgorithmException(){
            int[][] population = {{1,2},{1}};
            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation =algorithm.optimize(population);
            });
        }

        @Test
        @DisplayName("Optimize single attribute population returns a population of the same size")
        void optimize_oddPopulation_returnsPopulationTheSameSize() throws EvolutionaryAlgorithmException {//Test innecesario porque se comprueba en los tests de crossover?????
            int[][] population = {{1},{4},{7},{3}};

            assertThrows(EvolutionaryAlgorithmException.class, ()->{
                int[][] optimizedPopulation =algorithm.optimize(population);
            });
        }

        @Test
        @DisplayName("Optimize valid population returns a population of the same size")
        void optimize_validPopulation_returnsPopulationTheSameSize() throws EvolutionaryAlgorithmException {//Test innecesario porque se comprueba en los tests de crossover?????
            int[][] population = {{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18},{19,20,21,22,23,24}};
            int expectedPopulationLength = population.length;
            int expectedAttributesLength = population[0].length;

            int[][] optimizedPopulation = algorithm.optimize(population);

            assertEquals(expectedPopulationLength, optimizedPopulation.length);
            assertEquals(expectedAttributesLength, optimizedPopulation[0].length);
            assertEquals(expectedAttributesLength, optimizedPopulation[1].length);
            assertEquals(expectedAttributesLength, optimizedPopulation[2].length);
        }

    }
}

