package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>{
    private class ListNode {
        private T item;
        private ListNode prev;
        private ListNode next;

        public ListNode(T i) {
            item = i;
            prev = null;
            next = null;
        }
    }

    private int size = 0;
    private ListNode sentFront;
    private ListNode sentBack;

    public LinkedListDeque() {
        sentFront = new ListNode(null);
        sentBack = new ListNode(null);
        sentFront.next = sentBack;
        sentBack.prev = sentFront;
    }

    /** Add a node with value x to the end of the list**/
    @Override
    public void addFirst(T item) {
        ListNode newNode = new ListNode(item);
        ListNode first = sentFront.next;
        newNode.next = first;
        newNode.prev = sentFront;
        first.prev = newNode;
        sentFront.next = newNode;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        ListNode newNode = new ListNode(item);
        ListNode secondLast = sentBack.prev;
        newNode.prev = secondLast;
        newNode.next = sentBack;
        secondLast.next = newNode;
        sentBack.prev = newNode;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }
//    public boolean isEmpty() {
//        return size == 0;
//    }

    @Override
    public void printDeque() {
        ListNode p = sentFront.next;

        while (p != sentBack) {
            System.out.print(p.item);
            System.out.print(' ');
            p = p.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        ListNode first = sentFront.next;

        sentFront.next = first.next;
        first.next.prev = sentFront;
        size -= 1;

        return first.item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }

        ListNode last = sentBack.prev;

        sentBack.prev = last.prev;
        last.prev.next = sentBack;
        size -= 1;

        return last.item;
    }

    @Override
    public T get(int index) {
        if (index + 1 > size) {
            System.out.println("index out of bound");
            return null;
        }
        ListNode p = sentFront.next;
        int i = 0;
        while (p != sentBack && i < index) {
            p = p.next;
            i += 1;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if (index > this.size) {
            return null;
        }
        if (index == 0) {
            return this.sentFront.next.item;
        }
        LinkedListDeque<T> lld = new LinkedListDeque<>();
        lld.size = this.size - 1;
        lld.sentFront = this.sentFront.next;
        lld.sentBack = this.sentBack;
        return lld.getRecursive(index - 1);
    }

    public Iterator<T> iterator() {
        return new LLDIterator();
    }

    private class LLDIterator implements Iterator<T> {
        private int iter_idx = 0;

        @Override
        public boolean hasNext() {
            return iter_idx < size();
        }

        @Override
        public T next() {
            T nextE = get(iter_idx);
            iter_idx += 1;
            return nextE;
        }
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (! (o instanceof LinkedListDeque)) {
            return false;
        }

        LinkedListDeque obj = (LinkedListDeque) o;
        if (this.size() != obj.size) {
            return false;
        }

        int n = this.size();
        for (int i = 0; i < n; i += 1){
            if (this.get(i) != obj.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<String>  lld2 = new LinkedListDeque<String>();
        LinkedListDeque<String>  lld3;

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
