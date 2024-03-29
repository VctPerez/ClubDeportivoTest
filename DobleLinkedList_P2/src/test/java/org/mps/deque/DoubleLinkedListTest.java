package org.mps.deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A DoubleLinkedList")
public class DoubleLinkedListTest {
    DoubleLinkedList<Integer> list;

    //PRIMERA SESION
    @Test
    @DisplayName("list is initialized")
    void DoubleLinkedListConstructor_initializesList() {
        list = new DoubleLinkedList<>();
        assertEquals(0, list.size());
    }

    @BeforeEach
    void createDoubleLinkedList() { list = new DoubleLinkedList<>(); }

    @Nested
    @DisplayName("Size")
    class Size {
        @Test
        @DisplayName("size for empty list is 0")
        void size_emptyList_returnsZero() {
            assertEquals(0, list.size());
        }

        @Test
        @DisplayName("size for not empty list is different than 0")
        void size_notEmptyList_doesNotReturnsZero() {
            list.append(1);
            assertNotEquals(0, list.size());
        }

        @Test
        @DisplayName("size for not list is not negative")
        void size_list_isNotNegative() {
            assertTrue(0 <= list.size());
        }
    }

    @Nested
    @DisplayName("First")
    class First {
        @Test
        @DisplayName("throws DoubleLinkedQueueException when consulting first element of empty list")
        void first_emptyList_throwsDoubleLinkedQueueException() {
           assertThrows(DoubleLinkedQueueException.class, () -> list.first());
        }

        @Test
        @DisplayName("returns element of list with one element")
        void first_singleElementList_returnsElement() {
            int expected = 1;
            list.append(expected);

            assertEquals(expected, list.first());
        }

        @Test
        @DisplayName("returns first element of list with multiple elements")
        void first_multipleElementList_returnsFirstElement() {
            int expected = 1;
            list.append(expected);
            list.append(2);

            assertEquals(expected, list.first());
        }
    }

    @Nested
    @DisplayName("Last")
    class Last {
        @Test
        @DisplayName("throws DoubleLinkedQueueException when consulting last element of empty list")
        void last_emptyList_throwsDoubleLinkedQueueException(){
            assertThrows(DoubleLinkedQueueException.class, ()->{
                list.last();
            });
        }

        @Test
        @DisplayName("returns element of list with one element")
        void last_singleElementList_returnsElement(){
            int expected = 1;
            list.append(expected);

            assertEquals(expected, list.last());
        }

        @Test
        @DisplayName("returns last element of list with multiple elements")
        void last_multipleElementList_returnsFirstElement(){
            int expected = 1;
            list.append(2);
            list.append(expected);

            assertEquals(expected, list.last());
        }
    }

    @Nested
    @DisplayName("Delete First")
    class DeleteFirst {
        @Test
        @DisplayName("throws DoubleLinkedQueueException when deleting first element of empty list")
        void deleteFirst_emptyList_throwsDoubleLinkedQueueException(){
            assertThrows(DoubleLinkedQueueException.class, ()->{
                list.deleteFirst();
            });
        }

        @Test
        @DisplayName("deleting first element of list decreases it's size")
        void deleteFirst_notEmptyList_dereasesListSize() {
            int expectedSize = list.size()-1;
            list.append(1);

            list.deleteFirst();

            assertEquals(expectedSize, list.size());
        }
        //EL TEST DE ABAJO NO SERIA REDUNDANTE? YA SE COMPRUEBA QUE DECREMENTE EL TAMAÑO Y QUE ELIMINE EL ELEMENTO
        @Test
        @DisplayName("deletes element of list with one element")
        void deleteFirst_singleElementList_returnsElement(){
            list.append(1);

            list.deleteFirst();

            assertEquals(0, list.size());
        }

        @Test
        @DisplayName("deletes first element of list with multiple elements")
        void deleteFirst_multipleElementList_returnsFirstElement(){
            list.append(1);
            list.append(2);

            list.deleteFirst();

            assertEquals(2, list.first());
        }
    }

    @Nested
    @DisplayName("Delete Last")
    class DeleteLast {
        @Test
        @DisplayName("throws DoubleLinkedQueueException when deleting last element of empty list")
        void deleteLast_emptyList_throwsDoubleLinkedQueueException(){
            assertThrows(DoubleLinkedQueueException.class, ()->{
                list.deleteLast();
            });
        }

        @Test
        @DisplayName("deleting last element of list decreases it's size")
        void deleteLast_notEmptyList_dereasesListSize() {
            int expectedSize = list.size()-1;
            list.append(1);

            list.deleteLast();

            assertEquals(expectedSize, list.size());
        }

        @Test
        @DisplayName("deletes element of list with one element")
        void deleteLast_singleElementList_returnsElement(){
            list.append(1);

            list.deleteLast();

            assertEquals(0, list.size());
        }

        @Test
        @DisplayName("deletes last element of list with multiple elements")
        void deleteLast_multipleElementList_returnsFirstElement(){
            list.append(1);
            list.append(2);

            list.deleteLast();

            assertEquals(1, list.first());
        }
    }

    @Nested
    @DisplayName("Preppend")
    class Preppend {
        @Test
        @DisplayName("preppending element adds it to empty list")
        void preppend_emptyList_addsElementToList() {
            int expected = 1;
            list.prepend(expected);

            assertEquals(expected, list.first());
        }

        @Test
        @DisplayName("preppending element increases size of list")
        void preppend_list_increasesListSize() {
            int expectedSize = list.size()+1;
            list.prepend(1);

            assertEquals(expectedSize, list.size());
        }

        @Test
        @DisplayName("preppending element adds it at the start of the list")
        void preppend_nonEmptyList_addsElementAtStartOfList() {
            int expected = 1;
            list.prepend(2);
            list.prepend(expected);
            assertEquals(expected, list.first());
        }

        @Test
        @DisplayName("preppending element increases size of list")
        void preppend_list_increasesSize() {
            int expected = list.size() + 1;
            list.prepend(1);

            assertEquals(expected, list.size());
        }

        @Test
        @DisplayName("throws DoubleLinkedQueueException when preppending null element")
        void preppend_null_throwsDoubleLinkedQueueException() {
            assertThrows(DoubleLinkedQueueException.class, () -> list.prepend(null));
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

            assertEquals(expected, list.first());//en este tipo de metodos hay que comprobar tambien que aumente el tamaño de la lista?
        }

        @Test
        @DisplayName("appending element increases size of list")
        void append_list_increasesListSize() {
            int expectedSize = list.size()+1;
            list.append(1);

            assertEquals(expectedSize, list.size());
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
            assertThrows(DoubleLinkedQueueException.class, () -> list.append(null));
        }
    }

    //SEGUNDA SESION
    @Nested
    @DisplayName("Get")
    class Get{
        @Test
        @DisplayName("throws IndexOutOfBoundsException when the index is negative")
        void get_negativeIndex_throwsIndexOutOfBoundsException(){
            assertThrows(IndexOutOfBoundsException.class, ()-> {
               list.get(-1);
            });
        }

        @Test
        @DisplayName("throws IndexOutOfBoundsException when the index is greater than the size of the list")
        void get_indexGreaterThanSize_throwsIndexOutOfBoundsException(){
            assertThrows(IndexOutOfBoundsException.class, ()-> {
                list.get(list.size()+1);
            });
        }

        @Test
        @DisplayName("returns the first element of the list when the index is 0")
        void get_indexZero_returnsFirstElement(){
            list.append(1);
            list.append(2);
            assertEquals(list.first(), list.get(0));
        }

        @Test
        @DisplayName("returns the last element of the list when the index is the size minus one of the list")
        void get_indexSizeMinusOne_returnsLastElement(){
            list.append(1);
            list.append(2);
            assertEquals(list.last(), list.get(list.size() - 1));
        }

        @Test
        @DisplayName("returns expected element of the list")
        void get_validIndex_returnsExpectedElement(){
            int expected = 111;

            list.append(1);
            list.append(2);
            list.append(expected);
            list.append(3);
            list.append(4);

            assertEquals(expected, list.get(2));
        }
    }

    @Nested
    @DisplayName("Contains")
    class Contains{
        @Test
        @DisplayName("returns false when the element is not in the list")
        void contains_notPresentElement_returnsFalse(){
            assertFalse(list.contains(1));
        }

        @Test
        @DisplayName("returns true when the element is in the list once")
        void contains_presentElement_returnsTrue(){
            list.append(1);
            assertTrue(list.contains(1));
        }
        @Test
        @DisplayName("returns true when the element is in the list multiple times")
        void contains_multiplePresentElements_returnsTrue(){
            list.append(1);
            list.append(1);
            list.append(2);
            assertTrue(list.contains(1));
        }
    }

    @Nested
    @DisplayName("Remove")
    class Remove{
        @Test
        @DisplayName("doesn't do anything if the element is not present in the list")
        void remove_notPresentElement_doesNotThrowException(){
            list.append(2);
            int expectedSize = list.size();

            assertDoesNotThrow(()->{
                list.remove(1);
            });
            assertEquals(expectedSize, list.size());
        }

        @Test
        @DisplayName("removes the element from the list if it's present once")
        void remove_presentElement_removesElementFromList(){
            list.append(1);
            int expectedSize = list.size();

            list.remove(1);

            assertFalse(list.contains(1));
            assertNotEquals(expectedSize, list.size());
        }

        @Test
        @DisplayName("removes the first instance of the element from the list if it's present multiple times")
        void remove_multiplePresentElement_removesFirstInstaceOfElementFromList(){
            list.append(1);
            list.append(1);
            int expectedSize = list.size();

            list.remove(1);

            assertTrue(list.contains(1));
            assertNotEquals(expectedSize, list.size());
        }
    }

    @Nested
    @DisplayName("Sort")
    class Sort{

    }
}
