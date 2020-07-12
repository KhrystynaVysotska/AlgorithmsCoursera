package second.week.lectures;

public class QueueOfStrings {
    private Node first = null;
    private Node last = null;
    
    private class Node {
        String item;
        Node next;
    }

    public void enqueue(String item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if(isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;   
        }
    }

    public String dequeue() {
        String oldFirstItem = first.item;
        first = first.next;
        if(isEmpty()) {
            last = null;
        }
        return oldFirstItem;
    }
    
    public boolean isEmpty() {
        return first == null;
    }
}
