package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    private int size;
    private final Node sentFront;
    private final Node sentBack;

    private class Node {
        T item;
        Node prev;
        Node next;

        Node(T f, Node p, Node n) {
            item = f;
            prev = p;
            next = n;
        }
    }

    public LinkedListDeque() {
        sentBack = new Node(null, null, null);
        sentFront = new Node(null, null, null);
        sentFront.next = sentBack;
        sentBack.prev = sentFront;
    }

    @Override
    public void addFirst(T item) {
        Node current = new Node(item, sentFront, sentFront.next);
        sentFront.next.prev = current;
        sentFront.next = current;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node current = new Node(item, sentBack.prev, sentBack);
        sentBack.prev.next = current;
        sentBack.prev = current;
        size++;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node current = sentFront.next;
        while (current != sentBack) {
            System.out.print(current.item);
            current = current.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        Node first = sentFront.next;
        if (isEmpty()) {
            return null;
        }
        T garbage = first.item;
        first.next.prev = sentFront;
        sentFront.next = first.next;
        size--;
        return garbage;
    }

    @Override
    public T removeLast() {
        Node last = sentBack.prev;
        if (isEmpty()) {
            return null;
        }
        T garbage = last.item;
        last.prev.next = sentBack;
        sentBack.prev = last.prev;
        size--;
        return garbage;
    }

    @Override
    public T get(int index) {
        Node ptr = sentFront.next;
        while (index > 0) {
            ptr = ptr.next;
            index--;
        }
        return ptr.item;
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return helper(index, sentFront.next);
    }

    private T helper(int index, Node temp) {
        if (index == 0) {
            return temp.item;
        }
        return helper(index - 1, temp.next);
    }

    private class LinkedListIterator implements Iterator<T> {

        private Node current;

        LinkedListIterator() {
            current = sentFront.next;
        }
        @Override
        public boolean hasNext() {
            return current != sentBack;
        }

        @Override
        public T next() {
            T returnItem = current.item;
            current = current.next;
            return returnItem;
        }
    }
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Deque<?>) {
            Deque<T> other = (Deque<T>) o;
            if (this.size != other.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!this.get(i).equals(other.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}


