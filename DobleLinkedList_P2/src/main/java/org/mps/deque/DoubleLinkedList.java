package org.mps.deque;

import java.util.ArrayList;
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
            first = new LinkedNode<>(value,null,first);
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
            last = new LinkedNode<>(value,last,null);
        }
        size++;
    }

    @Override
    public void deleteFirst() {
        if(size() == 0) {
            throw new DoubleLinkedQueueException("Empty List");
        } else {
            first = first.getNext();
            if(first != null) first.setPrevious(null);
            size--;
        }
    }

    @Override
    public void deleteLast() {
        if(size() == 0) {
            throw new DoubleLinkedQueueException("Empty List");
        } else {
            last = last.getPrevious();
            if(last != null) last.setNext(null);
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
        if (index < 0 || index > this.size() - 1) {
            throw new IndexOutOfBoundsException();
        }
        LinkedNode<T> current = first;
        while (index > 0) {
            current = current.getNext();
            index--;
        }
        return current.getItem();
    }

    @Override
    public boolean contains(T value) {
        boolean res = false;
        int index = 0;
        LinkedNode<T> current = first;
        while (!res && index < this.size()) {
            if(current.getItem() == value){
                res = true;
            } else {
                current = current.getNext();
                index++;
            }
        }
        return res;
    }

    @Override
    public void remove(T value) {
        int index = 0;
        LinkedNode<T> current = first;
        boolean found = false;
        while (index < this.size() && !found) {
            if(current.getItem() == value) {
                found = true;
                if (current == first) {
                    this.deleteFirst();
                } else if (current == last) {
                    this.deleteLast();
                } else {
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                }
            }
            else {
                current = current.getNext();
                index++;
            }
        }
    }

    @Override
    public void sort(Comparator<? super T> comparator) {
        int size = this.size();
        boolean swaped = false;
        while(!swaped){
            LinkedNode<T> current = first;
            swaped = false;
            for(int i = 0; i < size - 1; i++) {
                if (comparator.compare(this.get(i), this.get(i + 1)) >= 1) {
                    swaped = true;
                    T aux = this.get(i);
                    current.setItem(this.get(i + 1));
                    current.getNext().setItem(aux);
                }
                current = current.getNext();
            }
        }

    }
}