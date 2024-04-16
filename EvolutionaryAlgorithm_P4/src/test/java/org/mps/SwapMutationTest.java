package org.mps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.mutation.SwapMutation;
import static org.junit.jupiter.api.Assertions.*;

public class SwapMutationTest {

    SwapMutation swapMutation;

    @BeforeEach
    public void setUp() {
        swapMutation = new SwapMutation();
    }

    @Test
    @DisplayName("Constructor instanciate random")
    public void SwapMutation_CreateRandom_DoesNotThrowException() {

        assertDoesNotThrow(SwapMutation::new);

    }

    @Test
    @DisplayName("Mutate array null")
    public void Mutate_ArrayNull_ThrowsException() {

        assertThrows(EvolutionaryAlgorithmException.class,() -> swapMutation.mutate(null));

    }

    @Test
    @DisplayName("Mutate empty array")
    public void Mutate_EmptyArray_ThrowsException() {
        int[] array = new int[]{};

        assertThrows(EvolutionaryAlgorithmException.class,() -> swapMutation.mutate(array));

    }

    @Test
    @DisplayName("Mutate valid array")
    public void Mutate_ValidArray_ReturnsMutatedArray() throws EvolutionaryAlgorithmException {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int[] mutation = this.swapMutation.mutate(array);

        assertEquals(array.length, mutation.length);

    }
}
