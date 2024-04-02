package org.mps.deque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedNodeTest {

    @Nested
    class Constructor{
        LinkedNode<Integer> node;

        @Test
        @DisplayName("Next and previous are saved correctly.")
        public void constructor_validNextAndPrevious_nextAndPreviousSaved(){
            LinkedNode<Integer> previous = new LinkedNode<>(0,null, null);
            LinkedNode<Integer> next = new LinkedNode<>(2,null, null);

            node = new LinkedNode<>(1, previous, next);

            assertEquals(previous, node.getPrevious(), "El anterior deberia ser el introducido.");
            assertEquals(next, node.getNext(), "El siguiente deberia ser el introducido.");
        }

        @Test
        @DisplayName("Establish links between the three nodes.")
        public void constructor_validNextAndPrevious_establishLinks(){
            LinkedNode<Integer> previous = new LinkedNode<>(0,null, null);
            LinkedNode<Integer> next = new LinkedNode<>(2,null, null);

            node = new LinkedNode<>(1, previous, next);

            assertEquals(previous.getNext(), node, "El siguiente de previous deberia ser node.");
            assertEquals(next.getPrevious(), node, "El anterior de next deberia ser node.");
        }
    }
    @Nested
    class GetItem{
        LinkedNode<Integer> node;

        @BeforeEach
        public void initNode(){
            node = new LinkedNode<>(null, null, null);
        }
        @Test
        @DisplayName("Returns null when item is null")
        public void getItem_nullItem_returnsNull(){
            Integer item;

            item = node.getItem();

            assertNull(item, "Item deberia ser null");
        }

        @Test
        @DisplayName("Returns the item")
        public void getItem_validItem_returnsItem(){
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
        public void setItem_nullParameter_mustBeNull(){
            node.setItem(null);

            assertNull(node.getItem(), "El item deberia ser null");
            assertNotEquals(initialItem, node.getItem(), "El item no debe ser el inicial.");
        }

        @Test
        @DisplayName("Changes the item to new item.")
        public void setItem_validParameter_itemMustChange(){
            Integer newItem = 5;

            node.setItem(newItem);

            assertEquals(newItem, node.getItem(), "El item deberia ser el mismo que newItem");
        }
    }

    @Nested
    class GetPrevious{
        @Test
        @DisplayName("Returns null when previous is null.")
        public void getPrevious_whenPreviousNull_returnsNull(){
            LinkedNode<Integer> node = new LinkedNode<>(1, null, null);
            LinkedNode<Integer> previous;

            previous = node.getPrevious();

            assertNull(previous, "El previo deberia ser nulo.");
        }

        @Test
        @DisplayName("Returns previous when it is not null.")
        public void getPrevious_whenPreviousValid_returnsPrevious(){
            LinkedNode<Integer> expectedPrevious = new LinkedNode<>(2, null, null);
            LinkedNode<Integer> node = new LinkedNode<>(1, expectedPrevious, null);
            LinkedNode<Integer> actualPrevious;

            actualPrevious = node.getPrevious();

            assertEquals(expectedPrevious, actualPrevious, "El node obtenido en el get deberia ser el mismo");
        }
    }

    @Nested
    class SetPrevious{
        LinkedNode<Integer> previous;
        LinkedNode<Integer> node;
        @BeforeEach
        public void initNodeAndPrevious(){
           previous = new LinkedNode<>(2, null, null);
           node = new LinkedNode<>(1, previous, null);
        }
        @Test
        @DisplayName("Changes previous to null.")
        public void setPrevious_whenParameterIsNull_newPreviousIsNull(){

            node.setPrevious(null);

            assertNull(node.getPrevious(), "El nuevo previous deberia ser nulo");
        }

        @Test
        @DisplayName("Changes previous to new previous.")
        public void setPrevious_parameterIsNode_previousChange(){
            LinkedNode<Integer> newPrevious = new LinkedNode<>(5, null, null);

            node.setPrevious(newPrevious);

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
        public void getNext_nextIsNull_returnsNull(){

            LinkedNode<Integer> next;

            next = node.getNext();

            assertNull(next, "El previo deberia ser nulo.");
        }

        @Test
        @DisplayName("Returns next when next is not null")
        public void getNext_nextValid_returnsNext(){
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
        public void setNext_nextIsNull_newNextIsNull(){

            node.setNext(null);

            assertNull(node.getNext(), "El nuevo next deberia ser nulo");
        }

        @Test
        @DisplayName("Changes next to new next.")
        public void setNext_nextIsNode_nextChange(){
            LinkedNode<Integer> newNext = new LinkedNode<>(5, null, null);

            node.setNext(newNext);

            assertEquals(newNext, node.getNext(), "El next debe ser igual a newNext");
        }
    }

    @Nested
    class IsFirstNode{

        boolean isFirst;
        @Test
        @DisplayName("Returns true when previous is null")
        public void isFirstNode_previousIsNull_returnsTrue(){
            LinkedNode<Integer> node = new LinkedNode<>(1, null, null);

            isFirst = node.isFirstNode();

            assertTrue(isFirst, "Este nodo debería ser primero");
        }

        @Test
        @DisplayName("Returns false when previous is not null")
        public void isFirstNode_previousNotNull_returnsFalse(){
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
       public void isLastNode_nextIsNull_returnsTrue(){
           LinkedNode<Integer> node = new LinkedNode<>(1, null, null);

           isLast = node.isLastNode();

           assertTrue(isLast, "Este nodo deberia ser el ultimo");
       }

       @Test
       @DisplayName("Returns false when next is not null.")
       public void isLastNode_nextIsNotNull_returnsFalse(){
           LinkedNode<Integer> next = new LinkedNode<>(0, null, null);
           LinkedNode<Integer> node = new LinkedNode<>(1, null, next);

           isLast = node.isLastNode();

           assertFalse(isLast, "Este nodo no debería ser ultimo");
       }
   }

   @Nested
   class IsNotATerminalNode{
        boolean isNotTerminal;
       @Test
       @DisplayName("Returns true when next and previous are not null.")
       public void isNotATerminalNode_nextAndLastAreNotNull_returnsTrue(){
           LinkedNode<Integer> previous = new LinkedNode<>(0, null, null);
           LinkedNode<Integer> next = new LinkedNode<>(2,null, null);
           LinkedNode<Integer> node = new LinkedNode<>(1, previous, next);

           isNotTerminal = node.isNotATerminalNode();

           assertTrue(isNotTerminal, "No deberia ser un nodo terminal.");
       }

       @Test
       @DisplayName("Returns false when next is null.")
       public void isNotATerminalNode_nextIsNull_returnsFalse(){
           LinkedNode<Integer> previous = new LinkedNode<>(0, null, null);
           LinkedNode<Integer> node = new LinkedNode<>(1, previous, null);

           isNotTerminal = node.isNotATerminalNode();

           assertFalse(isNotTerminal, "Deberia ser un nodo terminal.");
       }
       @Test
       @DisplayName("Returns false when previous is null.")
       public void isNotATerminalNode_previousIsNull_returnsFalse(){
           LinkedNode<Integer> next = new LinkedNode<>(2, null, null);
           LinkedNode<Integer> node = new LinkedNode<>(1, null, next);

           isNotTerminal = node.isNotATerminalNode();

           assertFalse(isNotTerminal, "Deberia ser un nodo terminal.");
       }

       @Test
       @DisplayName("Returns false when previous and next are null.")
       public void isNotATerminalNode_previousAndNextAreNull_returnsFalse(){
           LinkedNode<Integer> node = new LinkedNode<>(1, null, null);

           isNotTerminal = node.isNotATerminalNode();

           assertFalse(isNotTerminal, "Deberia ser un nodo terminal.");
       }

   }





}
