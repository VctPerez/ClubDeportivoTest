package org.mps.boundedqueue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ArrayBoundedQueueTest {

    @Nested
    class Cosntructor{
        @Test
        @DisplayName("Throw exception when capacity is negative.")
        public void constructor_capacityNegative_throwException(){
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> new ArrayBoundedQueue<Integer>(-10))
                    .withMessage("ArrayBoundedException: capacity must be positive");
        }

        @Test
        @DisplayName("Throws exception when capacity is 0.")
        public void constructor_capacityIs0_throwException(){
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> new ArrayBoundedQueue<Integer>(0))
                    .withMessage("ArrayBoundedException: capacity must be positive");
        }

        @Test
        @DisplayName("Initialize correctly the queue")
        public void cosntructor_validCapacity_initializeCorrectlyQueue(){
            ArrayBoundedQueue<Integer> arrayBoundedQueue = new ArrayBoundedQueue<>(4);

            assertThat(arrayBoundedQueue).hasSize(0);
            assertThat(arrayBoundedQueue.getFirst()).isEqualTo(0);
            assertThat(arrayBoundedQueue.getLast()).isEqualTo(0);
        }   
    }

}
