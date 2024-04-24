package org.mps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.mutation.SwapMutation;
import static org.junit.jupiter.api.Assertions.*;

public class SwapMutationTest {//Trabajo en Grupo de David Bueno Carmona, Victor Perez Armenta y Jose Ángel Bueno Ruiz

    SwapMutation swapMutation;

    @Nested
    class Constructor {
        @Test
        @DisplayName("Constructor instanciate random")
        public void SwapMutation_CreateRandom_DoesNotThrowException() {

            assertDoesNotThrow(SwapMutation::new);

        }
    }

    @Nested
    class Mutate {

        @BeforeEach
        public void setUp() {
            swapMutation = new SwapMutation();
        }

        @Test
        @DisplayName("Throws Exception if array is null")
        public void Mutate_ArrayNull_ThrowsException() {

            assertThrows(EvolutionaryAlgorithmException.class,() -> swapMutation.mutate(null));

        }

        @Test
        @DisplayName("Throws Exception if array is empty")
        public void Mutate_EmptyArray_ThrowsException() {
            int[] array = new int[]{};

            assertThrows(EvolutionaryAlgorithmException.class,() -> swapMutation.mutate(array));

        }

        @Test
        @DisplayName("Return array if input is valid")
        public void Mutate_ValidArray_ReturnsMutatedArray() throws EvolutionaryAlgorithmException {
            int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

            int[] mutation = swapMutation.mutate(array);

            assertEquals(array.length, mutation.length);

        }
    }
}
