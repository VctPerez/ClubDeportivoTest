package org.mps.deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedNodeTest {

    @Nested
    class GetItem{
        LinkedNode<Integer> node;
        Integer item;

        @BeforeEach
        public void initNode(){
            node = new LinkedNode<>(null, null, null);
        }
        @Test
        @DisplayName("Returns null when item is null")
        public void GetItem_NullItem_ReturnsNull(){


            item = node.getItem();

            assertNull(item, "Item deberia ser null");
        }

        @Test
        @DisplayName("Returns the item")
        public void GetItem_ValidItem_ReturnsItem(){
            LinkedNode<Integer> node = new LinkedNode<>(1, null, null);
            Integer item;

            item = node.getItem();

            assertEquals(1, item, "Item deberia ser 1");
        }
    }

    @Nested
    class SetItem{
        Integer initialItem;
        LinkedNode<Integer> node;

        @BeforeEach
        public void initNodeAndItem(){
            initialItem = 1;
            node = new LinkedNode<>(initialItem, null, null);
        }

        @Test
        @DisplayName("Changes the item to null")
        public void SetItem_NullParameter_MustBeNull(){



            node.setItem(null);

            assertNull(node.getItem(), "El item deberia ser null");
            assertNotEquals(initialItem, node.getItem(), "El item no debe ser el inicial.");
        }

        @Test
        @DisplayName("Changes the item to new item.")
        public void SetItem_ValidParameter_ItemMustChange(){
            Integer newItem = 5, initialItem = 1;
            LinkedNode<Integer> node = new LinkedNode<>(initialItem, null, null);


            node.setItem(newItem);

            assertEquals(newItem, node.getItem(), "El item deberia ser el mismo que newItem");
            assertNotEquals(initialItem, node.getItem(), "El item no debe ser el inicial.");
        }
    }

    @Nested
    class GetPrevious{
        @Test
        @DisplayName("Returns null when previous is null.")
        public void GetPrevious_WhenPreviousNull_ReturnsNull(){
            LinkedNode<Integer> node = new LinkedNode<>(1, null, null);
            LinkedNode<Integer> previous;

            previous = node.getPrevious();

            assertNull(previous, "El previo deberia ser nulo.");
        }

        @Test
        @DisplayName("Returns previous when it is not null.")
        public void GetPrevious_WhenPreviousValid_ReturnsPrevious(){
            LinkedNode<Integer> previous = new LinkedNode<>(2, null, null);
            LinkedNode<Integer> node = new LinkedNode<>(1, previous, null);
            LinkedNode<Integer> previousGot;

            previousGot = node.getPrevious();

            assertEquals(previous, previousGot, "El node obtenido en el get deberia ser el mismo");
        }
    }

    @Nested
    class SetPrevious{
        LinkedNode<Integer> previous;
        LinkedNode<Integer> node;
        @BeforeEach
        public void initNodeandPrevious(){
           previous = new LinkedNode<>(2, null, null);
           node = new LinkedNode<>(1, previous, null);
        }
        @Test
        @DisplayName("Changes previous to null.")
        public void SetPrevious_WhenParameterIsNull_NewPreviousIsNull(){

            node.setPrevious(null);

            assertNull(node.getPrevious(), "El nuevo previous deberia ser nulo");
        }

        @Test
        @DisplayName("Changes previous to new previous.")
        public void SetPrevious_WhenParameterIsNode_PreviousChange(){
            LinkedNode<Integer> newPrevious = new LinkedNode<>(5, null, null);

            node.setPrevious(newPrevious);

            assertNotEquals(previous, node.getPrevious(), "El previous deberia haber cambiado");
            assertEquals(newPrevious, node.getPrevious(), "El previous debe ser igual a newPrevious");
        }
    }


    @Nested
    class GetNext{

        LinkedNode<Integer> node;

        @BeforeEach
        public void initNode(){
            node = new LinkedNode<>(1, null, null);
        }
        @Test
        @DisplayName("Returns null when next is null.")
        public void GetNext_WhenNextIsNull_ReturnsNull(){

            LinkedNode<Integer> next;

            next = node.getNext();

            assertNull(next, "El previo deberia ser nulo.");
        }

        @Test
        @DisplayName("Returns next when next is not null")
        public void GetNext_WhenNextValid_ReturnsNext(){
            LinkedNode<Integer> next = new LinkedNode<>(2, null, null);
            LinkedNode<Integer> node = new LinkedNode<>(1, null, next);
            LinkedNode<Integer> nextGot;

            nextGot = node.getNext();

            assertEquals(next, nextGot, "El node obtenido en el get deberia ser el mismo");
        }
    }

    @Nested
    class SetNext{
        LinkedNode<Integer> next;
        LinkedNode<Integer> node;
        @BeforeEach
        public void initNodeAndNext(){
            next = new LinkedNode<>(2, null, null);
            node = new LinkedNode<>(1, null, next);
        }
        @Test
        @DisplayName("Changes next to null.")
        public void SetNext_WhenNextIsNull_NewNextIsNull(){

            node.setNext(null);

            assertNull(node.getNext(), "El nuevo next deberia ser nulo");
        }

        @Test
        @DisplayName("Changes next to new next.")
        public void SetNext_WhenNextIsNode_NextChange(){
            LinkedNode<Integer> newNext = new LinkedNode<>(5, null, null);

            node.setNext(newNext);

            assertNotEquals(next, node.getNext(), "El next deberia haber cambiado");
            assertEquals(newNext, node.getNext(), "El next debe ser igual a newPrevious");
        }
    }

    @Nested
    class IsFirstNode{

        boolean isFirst;
        @Test
        @DisplayName("Returns true when previous is null")
        public void IsFirstNode_WhenPreviousIsNull_ReturnsTrue(){
            LinkedNode<Integer> node = new LinkedNode<>(1, null, null);

            isFirst = node.isFirstNode();

            assertTrue(isFirst, "Este nodo debería ser primero");
        }

        @Test
        @DisplayName("Returns false when previous is not null")
        public void IsFirstNode_WhenPreviousNotNull_ReturnsFalse(){
            LinkedNode<Integer> previous = new LinkedNode<>(0, null, null);
            LinkedNode<Integer> node = new LinkedNode<>(1, previous, null);

            isFirst = node.isFirstNode();

            assertFalse(isFirst, "Este nodo no debería ser primero");
        }
    }

   @Nested
   class IsLastNode{
       boolean isLast;

       @Test
       @DisplayName("Returns true when next is null.")
       public void IsLastNode_WhenNextIsNull_ReturnsTrue(){
           LinkedNode<Integer> node = new LinkedNode<>(1, null, null);

           isLast = node.isLastNode();

           assertTrue(isLast, "Este nodo deberia ser el ultimo");
       }

       @Test
       @DisplayName("Returns false when next is not null.")
       public void IsLastNode_WhenNextIsNotNull_ReturnsFalse(){
           LinkedNode<Integer> next = new LinkedNode<>(0, null, null);
           LinkedNode<Integer> node = new LinkedNode<>(1, null, next);

           isLast = node.isLastNode();

           assertFalse(isLast, "Este nodo no debería ser ultimo");
       }
   }

   @Nested
   class IsNotATerminalNode{
        boolean isNotTerminal;
        @BeforeEach
        public void initNode(){

        }
       @Test
       @DisplayName("Returns true when next and previous are not null.")
       public void IsNotATerminalNode_WhenNextAndLastAreNotNull_ReturnsTrue(){
           LinkedNode<Integer> previous = new LinkedNode<>(0, null, null);
           LinkedNode<Integer> next = new LinkedNode<>(2,null, null);
           LinkedNode<Integer> node = new LinkedNode<>(1, previous, next);

           isNotTerminal = node.isNotATerminalNode();

           assertTrue(isNotTerminal, "No deberia ser un nodo terminal.");
       }

       @Test
       @DisplayName("Returns false when next is null.")
       public void IsNotATerminalNode_WhenNextIsNull_ReturnsFalse(){
           LinkedNode<Integer> previous = new LinkedNode<>(0, null, null);
           LinkedNode<Integer> node = new LinkedNode<>(1, previous, null);

           isNotTerminal = node.isNotATerminalNode();

           assertFalse(isNotTerminal, "Deberia ser un nodo terminal.");
       }
       @Test
       @DisplayName("Returns false when previous is null.")
       public void IsNotATerminalNode_WhenPreviousIsNull_ReturnsFalse(){
           LinkedNode<Integer> next = new LinkedNode<>(2, null, null);
           LinkedNode<Integer> node = new LinkedNode<>(1, null, next);

           isNotTerminal = node.isNotATerminalNode();

           assertFalse(isNotTerminal, "Deberia ser un nodo terminal.");
       }

       @Test
       @DisplayName("Returns previous and next are null.")
       public void IsNotATerminalNode_WhenPReviousAndNextAreNull_ReturnsFalse(){
           LinkedNode<Integer> node = new LinkedNode<>(1, null, null);

           isNotTerminal = node.isNotATerminalNode();

           assertFalse(isNotTerminal, "Deberia ser un nodo terminal.");
       }

   }





}
