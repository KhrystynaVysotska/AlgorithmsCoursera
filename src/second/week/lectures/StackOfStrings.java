package second.week.lectures;

import java.util.Iterator;

public class StackOfStrings<T> implements Iterable<T> {
    private Node first;
    private int size;

    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T item = current.item;
            current = current.previous;
            return item;
        }

    }

    private class Node {
        T item;
        Node previous;
    }

    public StackOfStrings() {
        this.first = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(T item) {
        Node oldFirst = this.first;
        first = new Node();
        first.item = item;
        first.previous = oldFirst;
        this.size++;
    }

    public T pop() {
        T itemToDelete = first.item;
        this.first = first.previous;
        size--;
        return itemToDelete;
    }

    public static void main(String[] args) {
        StackOfStrings<String> stack = new StackOfStrings<>();
        stack.push("i");
        stack.push("love");
        stack.push("you");
//        while (!StdIn.isEmpty()) {
//            String s = StdIn.readString();
//            if (s.equals("-"))
//                StdOut.print(stack.pop());
//            else
//                stack.push(s);
//        }
        for(String s:stack) {
            System.out.println(s);
        }
    }
}
