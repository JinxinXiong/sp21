package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int FACTOR = 2;
    private int n = 0;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 8;
        nextFirst = 7;
        nextLast = 0;
    }

    private void resize(int nsize) {
        T[] a = (T[]) new Object[nsize];

        int i = (nextFirst + 1) % size;
        int j = 0;
        while (j < n) {
            a[j] = items[i];
            j += 1;
            i  = (i + 1) % size;
        }

//        System.out.println("current capacity" + size + " to " + nsize);
        size = nsize;
        items = a;
        nextFirst = nsize - 1;
        nextLast = j;
    }
    @Override
    public void addFirst(T item) {
        if (items[nextFirst] != null) {
            resize(size * FACTOR);
        }
        items[nextFirst] = item;
        nextFirst  = (nextFirst - 1 + size) % size;
        n += 1;
    }
    @Override
    public void addLast(T item) {
        if (items[nextLast] != null) {
            resize(size * FACTOR);
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1) % size;
        n += 1;
    }

//    public boolean isEmpty() {
//        return (items[(nextFirst + 1 + size) % size] == null) && (items[(nextLast - 1 + size) % size] == null);
//    }
    @Override
    public int size() {
        return n;
    }

    @Override
    public void printDeque() {
        int i = (nextFirst + 1) % size;
        while (i != nextLast) {
            System.out.print(items[i] + " ");
            i  = (i + 1) % size;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (n == 0) {
            return null;
        }

        if (size > 16 && n < size * 0.25) {
            resize(size / FACTOR);
        }
        nextFirst = (nextFirst + 1) % size;
        T x = items[nextFirst];
        items[nextFirst] = null;
        n -= 1;
        return x;
    }

    @Override
    public T removeLast() {
        if (n == 0) {
            return null;
        }

        if (size > 16 && n < 0.25 * size) {
            resize(size / FACTOR);
        }

        nextLast = (nextLast - 1 + size) % size;
        T x = items[nextLast];
        items[nextLast] = null;
        n -= 1;

        return x;
    }

    @Override
    public T get(int index) {
        return items[(nextFirst + 1 + index) % size];
    }

    @Override
    public Iterator<T> iterator() {
        return new ADIterator();
    }

    private class ADIterator implements Iterator<T> {
        private int iter_index = 0;

        @Override
        public boolean hasNext() {
            return iter_index < n;
        }

        @Override
        public T next() {
            T tmp = get(iter_index);
            iter_index += 1;
            return tmp;
        }
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof ArrayDeque)) {
            return false;
        }

        ArrayDeque obj = (ArrayDeque) o;

        if (this.size() != obj.size()) {
            return false;
        }

        for (int i = 0; i < size(); i += 1){
            if (this.get(i) != obj.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        ArrayDeque<String>  lld1 = new ArrayDeque<String>();
        ArrayDeque<String>  lld2 = new ArrayDeque<String>();
        ArrayDeque<String>  lld3;

        lld1.addFirst("a");
        lld1.addFirst("b");

        lld2.addFirst("a");
        lld2.addFirst("b");
        lld2.addFirst("b");

        lld3 = lld1;
//        lld2.addFirst(3.14159);
//        lld3.addFirst(true);

        System.out.println(lld1.equals(lld2));
        System.out.println(lld1.equals(lld3));
    }
}
