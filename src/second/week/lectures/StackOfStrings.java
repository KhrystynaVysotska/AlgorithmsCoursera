package second.week.lectures;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class StackOfStrings {
    private Node first;
    private int size;

    private class Node {
        String item;
        Node previous;
    }

    public StackOfStrings() {
        this.first = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(String item) {
        Node oldFirst = this.first;
        first = new Node();
        first.item = item;
        first.previous = oldFirst;
        this.size++;
    }

    public String pop() {
        String stringToDelete = first.item;
        this.first = first.previous;
        size--;
        return stringToDelete;
    }

    public static void main(String[] args) {
        StackOfStrings stack = new StackOfStrings();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(stack.pop());
            else
                stack.push(s);
        }
    }

}
