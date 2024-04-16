package org.mps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.selection.TournamentSelection;

import static org.junit.jupiter.api.Assertions.*;

public class TournamentSelectionTest {

    TournamentSelection tournamentSelection;

    @BeforeEach
    public void setUp() throws EvolutionaryAlgorithmException {
        tournamentSelection = new TournamentSelection(5);
    }

    @Test
    @DisplayName("Constructor positive size")
    public void Constructor_PositiveSize_DoesNotThrowException() {
        int validSize = 5;

        assertDoesNotThrow(() -> new TournamentSelection(validSize));
    }

    @Test
    @DisplayName("Constructor 0 size")
    public void Constructor_Size0_ThrowsException() {
        int invalidSize = 0;

        assertThrows(EvolutionaryAlgorithmException.class, () -> new TournamentSelection(invalidSize));
    }

    @Test
    @DisplayName("Constructor negative size")
    public void Constructor_NegativeSize_ThrowsException() {
        int invalidSize = -1;

        assertThrows(EvolutionaryAlgorithmException.class, () -> new TournamentSelection(invalidSize));
    }

    @Test
    @DisplayName("Select array null")
    public void Select_ArrayNull_ThrowsException() {
        int[] invalidArray = null;

        assertThrows(EvolutionaryAlgorithmException.class, () -> tournamentSelection.select(invalidArray));
    }

    @Test
    @DisplayName("Select empty array")
    public void Select_EmptyArray_ThrowsException() {
        int[] invalidArray = new int[0];

        assertThrows(EvolutionaryAlgorithmException.class, () -> tournamentSelection.select(invalidArray));
    }

    @Test
    @DisplayName("Select empty array")
    public void Select_ValidArray_ReturnsArray() throws EvolutionaryAlgorithmException {
        int[] validArray = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        int[] selected = tournamentSelection.select(validArray);

        assertEquals(selected.length,validArray.length);
    }































}
