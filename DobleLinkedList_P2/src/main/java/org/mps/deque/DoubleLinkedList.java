package org.mps.deque;

import java.util.Comparator;

public class DoubleLinkedList<T> implements DoubleLinkedQueue<T> {
    private LinkedNode<T> first;

    private LinkedNode<T> last;
    private int size;

    public DoubleLinkedList() {
        this.size = 0;
        first = null;
        last = null;
    }

    @Override
    public void prepend(T value) {
        if(value == null) {
            throw new DoubleLinkedQueueException("Invalid NULL value");
        }
        if(size() == 0) {
            first = new LinkedNode<>(value,null,null);
            last = first;
        } else {
            LinkedNode<T> newNode = new LinkedNode<>(value,null,first);
            LinkedNode<T> temporalNode = first;
            temporalNode.setPrevious(newNode);
            first = newNode;
        }
        size++;
    }

    @Override
    public void append(T value) {
        if(value == null) {
            throw new DoubleLinkedQueueException("Invalid NULL value");
        }
        if(size() == 0) {
            first = new LinkedNode<>(value,null,null);
            last = first;
        } else {
            LinkedNode<T> newNode = new LinkedNode<>(value,last,null);
            LinkedNode<T> temporalNode = last;
            temporalNode.setNext(newNode);
            last = newNode;
        }
        size++;
    }

    @Override
    public void deleteFirst() {
        if(size() == 0) {
            throw new DoubleLinkedQueueException("Empty List");
        } else {
            first = first.getNext();
            size--;
        }
    }

    @Override
    public void deleteLast() {
        if(size() == 0) {
            throw new DoubleLinkedQueueException("Empty List");
        } else {
            last = last.getPrevious();
            size--;
        }
    }

    @Override
    public T first() {
        if(first == null) {
            throw new DoubleLinkedQueueException("First element does not exist");
        }
        return first.getItem();
    }

    @Override
    public T last() {
        if(last == null) {
            throw new DoubleLinkedQueueException("Last element does not exist");
        }
        return last.getItem();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public boolean contains(T value) {
        return false;
    }

    @Override
    public void remove(T value) {

    }

    @Override
    public void sort(Comparator<? super T> comparator) {

    }
}