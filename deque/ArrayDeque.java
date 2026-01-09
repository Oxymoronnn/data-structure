package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int minSize;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
        size = 0;
        minSize = 8;
    }


    private int minusOne(int index) {
        if (index == 0) {
            return items.length - 1;
        }
        return index - 1;
    }

    private int plusOne(int index) {
        if (index == items.length - 1) {
            return 0;
        }
        return index + 1;
    }

    private boolean checkUsage(int length) {
        return length >= minSize * 2 && (double) size / length < 0.25;
    }
    private void resize(int newLength) {
        newLength = Math.max(8, newLength);
        if (checkUsage(newLength)) {
            newLength =  (int) (size / 0.25);
        }
        if (newLength < size) {
            newLength = size;
        }
        int index = 0;
        T[] a = (T[]) new Object[newLength];
        for (int i = plusOne(nextFirst), count = 0; count < size; i = plusOne(i), count++) {
            a[index] = items[i];
            index++;
        }
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int count = 0, j = plusOne(nextFirst); count < size; count++, j = plusOne(j)) {
            System.out.print((items[j]));
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int first = plusOne(nextFirst);
        T garbage = items[first];
        items[first] = null;
        size--;
        nextFirst = first;
        if (checkUsage(items.length)) {
            resize(items.length / 2);
        }
        return garbage;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int last = minusOne(nextLast);
        T garbage = items[last];
        items[last] = null;
        size--;
        nextLast = last;
        if (checkUsage(items.length)) {
            resize(items.length / 2);
        }
        return garbage;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int current = plusOne(nextFirst) + index;
        while (current > items.length - 1) {
            current = current - items.length;
        }
        return items[current];
    }

    private class ADIterator implements Iterator<T> {

        private int current;

        ADIterator() {
            current = 0;
        }
        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public T next() {
            T returnItem = get(current);
            current += 1;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ADIterator();
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

