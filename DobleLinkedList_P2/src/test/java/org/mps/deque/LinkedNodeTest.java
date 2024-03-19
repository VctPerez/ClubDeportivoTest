package org.mps.deque;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LinkedNodeTest {


    @Test
    public void GetItem_NullItem_ReturnsNull(){
        LinkedNode<Integer> node = new LinkedNode<>(null, null, null);
        Integer item;

        item = node.getItem();

        assertNull(item, "Item deberia ser null");
    }

    @Test
    public void GetItem_ValidItem_ReturnsItem(){
        LinkedNode<Integer> node = new LinkedNode<>(1, null, null);
        Integer item;

        item = node.getItem();

        assertEquals(1, item, "Item deberia ser 1");
    }

    @Test
    public void SetItem_NullParameter_MustBeNull(){
        Integer newItem = 5, initialItem = 1;
        LinkedNode<Integer> node = new LinkedNode<>(initialItem, null, null);


        node.setItem(newItem);

        assertEquals(newItem, node.getItem(), "El item deberia ser el mismo que newItem");
        assertNotEquals(initialItem, node.getItem(), "El item no debe ser el inicial.");
    }

    @Test
    public void GetPrevious_WhenPreviousNull_ReturnsNull(){
        LinkedNode<Integer> node = new LinkedNode<>(1, null, null);
        LinkedNode<Integer> previous;

        previous = node.getPrevious();

        assertNull(previous, "El previo deberia ser nulo.");
    }

    @Test
    public void GetPrevious_WhenPreviousValid_ReturnsPrevious(){
        LinkedNode<Integer> previous = new LinkedNode<>(2, null, null);
        LinkedNode<Integer> node = new LinkedNode<>(1, previous, null);
        LinkedNode<Integer> previousGot;

        previousGot = node.getPrevious();

        assertEquals(previous, previousGot, "El node obtenido en el get deberia ser el mismo");
    }

    @Test
    public void SetPrevious_WhenPrevousIsNull_NewPreviousIsNull(){
        LinkedNode<Integer> previous = new LinkedNode<>(2, null, null);
        LinkedNode<Integer> node = new LinkedNode<>(1, previous, null);

        node.setPrevious(null);

        assertNull(node.getPrevious(), "El nuevo previous deberia ser nulo");
    }

    @Test
    public void SetPrevious_WhenPreviousIsNode_PreviousChange(){
        LinkedNode<Integer> previous = new LinkedNode<>(2, null, null);
        LinkedNode<Integer> node = new LinkedNode<>(1, previous, null);
        LinkedNode<Integer> newPrevious = new LinkedNode<>(5, null, null);

        node.setPrevious(newPrevious);

        assertNotEquals(previous, node.getPrevious(), "El previous deberia haber cambiado");
        assertEquals(newPrevious, node.getPrevious(), "El previous debe ser igual a newPrevious");
    }

    @Test
    public void GetNext_WhenPreviousNull_ReturnsNull(){
        LinkedNode<Integer> node = new LinkedNode<>(1, null, null);
        LinkedNode<Integer> next;

        next = node.getNext();

        assertNull(next, "El previo deberia ser nulo.");
    }

    @Test
    public void GetNext_WhenPreviousValid_ReturnsPrevious(){
        LinkedNode<Integer> next = new LinkedNode<>(2, null, null);
        LinkedNode<Integer> node = new LinkedNode<>(1, null, next);
        LinkedNode<Integer> nextGot;

        nextGot = node.getNext();

        assertEquals(next, nextGot, "El node obtenido en el get deberia ser el mismo");
    }


    @Test
    public void SetNext_WhenNextIsNull_NewNextIsNull(){
        LinkedNode<Integer> next = new LinkedNode<>(2, null, null);
        LinkedNode<Integer> node = new LinkedNode<>(1, null, next);

        node.setNext(null);

        assertNull(node.getNext(), "El nuevo next deberia ser nulo");
    }

    @Test
    public void SetNext_WhenNextIsNode_NextChange(){
        LinkedNode<Integer> next = new LinkedNode<>(2, null, null);
        LinkedNode<Integer> node = new LinkedNode<>(1, null, next);
        LinkedNode<Integer> newNext = new LinkedNode<>(5, null, null);

        node.setNext(newNext);

        assertNotEquals(next, node.getNext(), "El next deberia haber cambiado");
        assertEquals(newNext, node.getNext(), "El next debe ser igual a newPrevious");
    }

    @Test
    public void IsFirstNode_WhenPreviousIsNull_ReturnsTrue(){
        LinkedNode<Integer> node = new LinkedNode<>(1, null, null);
        boolean isFirst;

        isFirst = node.isFirstNode();

        assertTrue(isFirst, "Este nodo debería ser primero");
    }

    @Test
    public void IsFirstNode_WhenPreviousNotNull_ReturnsFalse(){
        LinkedNode<Integer> previous = new LinkedNode<>(0, null, null);
        LinkedNode<Integer> node = new LinkedNode<>(1, previous, null);
        boolean isFirst;

        isFirst = node.isFirstNode();

        assertFalse(isFirst, "Este nodo no debería ser primero");
    }

    @Test
    public void IsLastNode_WhenNextIsNull_ReturnsTrue(){
        LinkedNode<Integer> node = new LinkedNode<>(1, null, null);
        boolean isLast;

        isLast = node.isLastNode();

        assertTrue(isLast, "Este nodo deberia ser el ultimo");
    }

    @Test
    public void IsLastNode_WhenNextIsNotNull_ReturnsFalse(){
        LinkedNode<Integer> next = new LinkedNode<>(0, null, null);
        LinkedNode<Integer> node = new LinkedNode<>(1, null, next);
        boolean isLast;

        isLast = node.isLastNode();

        assertFalse(isLast, "Este nodo no debería ser ultimo");
    }



}
