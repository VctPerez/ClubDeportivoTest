package org.mps.boundedqueue;

import org.junit.jupiter.api.*;

import java.util.Iterator;

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
    @Nested
    class IsEmpty{
        @Test
        @DisplayName("Returns true if size is 0.")
        public void isEmpty_size0_returnsTrue(){
            ArrayBoundedQueue<Integer> arrayBoundedQueue = new ArrayBoundedQueue<>(2);

            assertThat(arrayBoundedQueue).isEmpty();
        }

        @Test
        @DisplayName("Returns false if size is greater than 0.")
        public void isEmpty_sizeGreaterThan0_returnsFalse(){
            ArrayBoundedQueue<Integer> arrayBoundedQueue = new ArrayBoundedQueue<>(2);
            arrayBoundedQueue.put(1);

            assertThat(arrayBoundedQueue).isNotEmpty();
        }
    }

    @Nested
    class Size{
        @Test
        @DisplayName("Returns the numbers of elements in the queue")
        public void size_returnsNumberOfElements(){
            ArrayBoundedQueue<Integer> arrayBoundedQueue = new ArrayBoundedQueue<>(3);
            arrayBoundedQueue.put(1);
            arrayBoundedQueue.put(2);

            assertThat(arrayBoundedQueue).hasSize(2);
        }
    }

    @Nested
    class Iterator{
        ArrayBoundedQueue<Integer> arrayBoundedQueue;

        @BeforeEach
        public void initIteratorTests(){
            arrayBoundedQueue = new ArrayBoundedQueue<>(3);
        }


        @Test
        @DisplayName("Returns an Iterator Object")
        public void iterator_returnsIteratorType(){
            java.util.Iterator<Integer> arrayBoundedQueueIterator = arrayBoundedQueue.iterator();
            assertThat(arrayBoundedQueueIterator).isInstanceOf(java.util.Iterator.class);
        }

        @Nested
        class HasNext{
            @Test
            @DisplayName("Returns true if there aren't visited elements.")
            public void hasNext_visitedLessSize_returnsTrue(){
                arrayBoundedQueue.put(1);
                arrayBoundedQueue.put(2);
                java.util.Iterator<Integer> arrayBoundedQueueIterator = arrayBoundedQueue.iterator();
                arrayBoundedQueueIterator.next();

                assertThat(arrayBoundedQueueIterator).hasNext();
            }

            @Test
            @DisplayName("Returns False if all elements are visited.")
            public void hasNext_visitedEqualSize_returnsFalse(){
                arrayBoundedQueue.put(2);
                java.util.Iterator<Integer> arrayBoundedQueueIterator = arrayBoundedQueue.iterator();
                arrayBoundedQueueIterator.next();

                assertThat(arrayBoundedQueueIterator).isExhausted();
            }
        }
    }

}
