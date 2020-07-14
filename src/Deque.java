import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;;
    private Node last = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        } else {
            Node oldFirst = first;
            first = new Node();
            if (oldFirst != null) {
                oldFirst.previous = first;
            }
            first.item = item;
            first.next = oldFirst;
            first.previous = null;
            size++;
        }
        if (last == null) {
            last = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        } else {
            Node oldLast = last;
            last = new Node();
            oldLast.next = last;
            last.item = item;
            last.next = null;
            last.previous = oldLast;
            size++;
        }
        if (first == null) {
            first = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            Item itemToDelete = first.item;
            first = null;
            last = null;
            size = 0;
            return itemToDelete;
        } else {
            Node firstToDelete = first;
            Item itemToDelete = first.item;
            first = firstToDelete.next;
            first.previous = null;
            size--;
            return itemToDelete;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else if (size == 1) {
            Item itemToDelete = last.item;
            first = null;
            last = null;
            size = 0;
            return itemToDelete;
        } else {
            Node lastToDelete = last;
            Item itemToDelete = last.item;
            last = lastToDelete.previous;
            last.next = null;
            size--;
            return itemToDelete;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (isEmpty()) {
                throw new NoSuchElementException();
            } else {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addFirst("first");
        deque.addLast("second");
        deque.addFirst("third");
        deque.addLast("forth");
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println("Is deque empty?  " + deque.isEmpty());
        System.out.println("Size of deque is " + deque.size());
        for (String s : deque) {
            System.out.println(s);
        }
    }

}
