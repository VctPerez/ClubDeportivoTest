package org.mps.boundedqueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

import static org.assertj.core.api.Assertions.*;

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
    public class Get {

        @Test
        @DisplayName("Throw Exception on Empty queue")
        public void get_emptyQueue_ThrowsException() {
            ArrayBoundedQueue arrayBoundedQueue = new ArrayBoundedQueue(10);

            assertThatExceptionOfType(EmptyBoundedQueueException.class).isThrownBy(() -> arrayBoundedQueue.get());
            assertThat(arrayBoundedQueue.getFirst()).isEqualTo(0);
        }

        @Test
        @DisplayName("Using get on a not-empty list, reduce the size")
        public void get_validQueue_ReduceSize() {
            ArrayBoundedQueue arrayBoundedQueue = new ArrayBoundedQueue(10);
            arrayBoundedQueue.put(1);
            arrayBoundedQueue.put(2);

            arrayBoundedQueue.get();

            assertThat(arrayBoundedQueue.size()).isEqualTo(1);
        }

        @Test
        @DisplayName("Using get on a not-empty list, returns the element")
        public void get_validQueue_ReturnsElement() {
            ArrayBoundedQueue arrayBoundedQueue = new ArrayBoundedQueue(10);
            arrayBoundedQueue.put(1);
            arrayBoundedQueue.put(2);

            Object res = arrayBoundedQueue.get();

            assertThat(res).isEqualTo(1);
        }

        @Test
        @DisplayName("Using get on a not-empty list, update first")
        public void get_validQueue_UpdateFirst() {
            ArrayBoundedQueue arrayBoundedQueue = new ArrayBoundedQueue(10);
            arrayBoundedQueue.put(1);
            arrayBoundedQueue.put(2);

            arrayBoundedQueue.get();

            assertThat(arrayBoundedQueue.getFirst()).isEqualTo(1);
        }

        @Test
        @DisplayName("Mantains circular slice for first element")
        public void get_testCircularSlice_ReturnFirst() {
            ArrayBoundedQueue arrayBoundedQueue = new ArrayBoundedQueue(2);
            arrayBoundedQueue.put(1);
            arrayBoundedQueue.put(2);
            arrayBoundedQueue.get();
            arrayBoundedQueue.put(3);
            arrayBoundedQueue.get();

            int res = arrayBoundedQueue.getFirst();

            assertThat(res).isEqualTo(0);
        }

    }

    ArrayBoundedQueue<Integer> queue;

    @BeforeEach
    void setUp() {queue = new ArrayBoundedQueue<>(5);}

    @Nested
    class Put{
        @Test
        @DisplayName("Throws IllegalArgumentException with null value")
        public void put_nullValue_throwsIllegalArgumentException(){
            assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> queue.put(null));
        }

        @Test
        @DisplayName("Throws FullBoundedQueueException with full queue")
        public void put_fullQueue_throwsEmptyBoundedQueueException(){
            queue.put(1);
            queue.put(2);
            queue.put(3);
            queue.put(4);
            queue.put(5);

            assertThatExceptionOfType(FullBoundedQueueException.class).isThrownBy(() -> queue.put(6));
        }

        @Test
        @DisplayName("Increases non-full queue size with valid element")
        public void put_notFullQueue_increasesItsSize(){
            int expectedSize = queue.size() + 1;

            queue.put(1);

            assertThat(queue.size()).isEqualTo(expectedSize);
        }

        @Test
        @DisplayName("Adds element to non-full queue with valid element")
        public void put_notFullQueue_addsElementToQueue(){
            int expectedElement = 1;

            queue.put(expectedElement);

            assertThat(queue).isNotEmpty();
            assertThat(queue.get()).isEqualTo(expectedElement);
        }

        @Test
        @DisplayName("Increases non-full queue nextElement index")
        public void put_notFullQueue_increasesNextElementIndex(){
            int expectedNextElement = queue.getLast()+1;

            queue.put(1);

            assertThat(queue.getLast()).isEqualTo(expectedNextElement);
        }

        @Test
        @DisplayName("Maintains non-full queue circular slice for nextElement")
        public void put_notFullQueue_maintainsNextElementCircular(){
            int expectedNextElement = queue.getLast();

            for(int i = 0; i< queue.size(); i++){
                queue.put(0);
            }

            assertThat(queue.getLast()).isEqualTo(expectedNextElement);
        }
    }
}
