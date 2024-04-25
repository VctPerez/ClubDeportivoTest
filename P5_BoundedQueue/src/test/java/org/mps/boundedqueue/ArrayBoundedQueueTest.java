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

    @Nested
    class IsFull{

        @Test
        @DisplayName("Returns false if size occupied isn't equal to capacity.")
        public void isFull_sizeDifferentCapacity_returnsFalse(){
            int capacity = 2;
            ArrayBoundedQueue<Integer> arrayBoundedQueue = new ArrayBoundedQueue<>(capacity);

            assertThat(arrayBoundedQueue.isFull()).isFalse();
        }

        @Test
        @DisplayName("Returns true if size occupied is equal to capacity.")
        public void isFull_sizeEqualCapacity_returnsFalse(){
            int capacity = 2;
            ArrayBoundedQueue<Integer> arrayBoundedQueue = new ArrayBoundedQueue<>(capacity);
            arrayBoundedQueue.put(1);
            arrayBoundedQueue.put(1);

            assertThat(arrayBoundedQueue.isFull()).isTrue();
        }
    }

}
