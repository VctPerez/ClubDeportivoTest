package org.mps.deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A DoubleLinkedList")
public class DoubleLinkedListTest {
    DoubleLinkedList<Object> list;

    @Test
    void DoubleLinkedListConstructor_initializesList(){
        list = new DoubleLinkedList<>();
        assertEquals(0, list.size());
    }

    @BeforeEach
    void createDoubleLinkedList() {list = new DoubleLinkedList<>();}

    @Nested
    @DisplayName("Size")
    class Size{
        @Test
        @DisplayName("size for empty list is 0")
        void size_emptyList_returnsZero(){
            assertEquals(0, list.size());
        }

        @Test
        @DisplayName("size for not empty list is different than 0")
        void size_notEmptyList_doesNotReturnsZero(){
            list.append(1);
            assertNotEquals(0, list.size());
        }

        @Test
        @DisplayName("size for not list is not negative")
        void size_list_isNotNegative(){
            assertTrue(0 <= list.size());
        }
    }

    @Nested
    @DisplayName("First")
    class First{
        @Test
        @DisplayName("throws DoubleLinkedQueueException when consulting first element of empty list")
        void first_emptyList_throwsDoubleLinkedQueueException(){
           assertThrows(DoubleLinkedQueueException.class, ()->{
               list.first();
           });
        }

        @Test
        @DisplayName("returns element of list with one element")
        void first_singleElementList_returnsElement(){
            int expected = 1;
            list.append(expected);

            assertEquals(expected, list.first());
        }

        @Test
        @DisplayName("returns first element of list with multiple elements")
        void first_multipleElementList_returnsFirstElement(){
            int expected = 1;
            list.append(expected);
            list.append(2);

            assertEquals(expected, list.first());
        }
    }

    @Nested
    @DisplayName("Last")
    class Last{
        @Test
        @DisplayName("throws DoubleLinkedQueueException when consulting last element of empty list")
        void first_emptyList_throwsDoubleLinkedQueueException(){
            assertThrows(DoubleLinkedQueueException.class, ()->{
                list.last();
            });
        }

        @Test
        @DisplayName("returns element of list with one element")
        void first_singleElementList_returnsElement(){
            int expected = 1;
            list.append(expected);

            assertEquals(expected, list.last());
        }

        @Test
        @DisplayName("returns last element of list with multiple elements")
        void first_multipleElementList_returnsFirstElement(){
            int expected = 1;
            list.append(2);
            list.append(expected);

            assertEquals(expected, list.last());
        }
    }

    @Nested
    @DisplayName("Delete First")
    class DeleteFirst{
        @Test
        @DisplayName("throws DoubleLinkedQueueException when deleting first element of empty list")
        void first_emptyList_throwsDoubleLinkedQueueException(){
            assertThrows(DoubleLinkedQueueException.class, ()->{
                list.deleteFirst();
            });
        }

        @Test
        @DisplayName("deletes element of list with one element")
        void first_singleElementList_returnsElement(){
            list.append(1);

            list.deleteFirst();

            assertEquals(0, list.size());
        }

        @Test
        @DisplayName("deletes first element of list with multiple elements")
        void first_multipleElementList_returnsFirstElement(){
            list.append(1);
            list.append(2);

            list.deleteFirst();

            assertEquals(2, list.first());
        }
    }

    @Nested
    @DisplayName("Delete Last")
    class DeleteLast{
        @Test
        @DisplayName("throws DoubleLinkedQueueException when deleting last element of empty list")
        void first_emptyList_throwsDoubleLinkedQueueException(){
            assertThrows(DoubleLinkedQueueException.class, ()->{
                list.deleteLast();
            });
        }

        @Test
        @DisplayName("deletes element of list with one element")
        void first_singleElementList_returnsElement(){
            list.append(1);

            list.deleteLast();

            assertEquals(0, list.size());
        }

        @Test
        @DisplayName("deletes last element of list with multiple elements")
        void first_multipleElementList_returnsFirstElement(){
            list.append(1);
            list.append(2);

            list.deleteLast();

            assertEquals(1, list.first());
        }
    }

    @Nested
    @DisplayName("Preppend")
    class Preppend{
        @Test
        @DisplayName("preppending element adds it to empty list")
        void preppend_emptyList_addsElementToList(){
            int expected = 1;
            list.prepend(expected);

            assertEquals(expected, list.first());
        }

        @Test
        @DisplayName("preppending element adds it at the start of the list")
        void preppend_nonEmptyList_addsElementAtStartOfList(){
            int expected = 1;
            list.prepend(2);
            list.prepend(expected);
            assertEquals(expected, list.first());
        }

        @Test
        @DisplayName("preppending element increases size of list")
        void preppend_list_increasesSize(){
            int expected = list.size() + 1;
            list.prepend(1);

            assertEquals(expected, list.size());
        }

        @Test
        @DisplayName("throws DoubleLinkedQueueException when preppending null element")
        void preppend_null_throwsDoubleLinkedQueueException(){
            assertThrows(DoubleLinkedQueueException.class, ()->{
                list.prepend(null);
            });
        }
    }

    @Nested
    @DisplayName("Append")
    class Append {
        @Test
        @DisplayName("appending element adds it to empty list")
        void append_emptyList_addsElementToList() {
            int expected = 1;
            list.append(expected);

            assertEquals(expected, list.first());
        }

        @Test
        @DisplayName("appending element adds it at the end of the list")
        void append_nonEmptyList_addsElementAtStartOfList() {
            int expected = 1;
            list.append(2);
            list.append(expected);
            assertEquals(expected, list.last());
        }

        @Test
        @DisplayName("appending element increases size of list")
        void append_list_increasesSize() {
            int expected = list.size() + 1;
            list.append(1);

            assertEquals(expected, list.size());
        }

        @Test
        @DisplayName("throws DoubleLinkedQueueException when appending null element")
        void append_null_throwsDoubleLinkedQueueException() {
            assertThrows(DoubleLinkedQueueException.class, () -> {
                list.append(null);
            });
        }
    }
}
