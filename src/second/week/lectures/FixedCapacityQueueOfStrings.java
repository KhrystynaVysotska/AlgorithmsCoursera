package second.week.lectures;

public class FixedCapacityQueueOfStrings {
    private String[] array;
    public int headOfArray = 0;
    public int tailOfArray = 0;
    public FixedCapacityQueueOfStrings(int capacity) {
        array = new String[capacity];
    }
    
    public void enqueue(String item) {
        if(tailOfArray == array.length) {
            this.reset();
        }
        array[tailOfArray++] = item; 
    }
    
    public String dequeue() {
        String oldHead = array[headOfArray];
        array[headOfArray] = null;
        headOfArray++;
        return oldHead;
    }
    public boolean isEmpty() {
        return tailOfArray == 0;
    }
    private void reset() {
        tailOfArray = array.length - headOfArray;
        String[] copy = new String[array.length];
        for(int i = 0; i < array.length; i++) {
            if(headOfArray < array.length) {
                copy[i] = array[headOfArray];
                headOfArray++;
            } 
        }
        array = copy;
        headOfArray = 0;
    }
    
    public static void main(String...args) {
        FixedCapacityQueueOfStrings queue = new FixedCapacityQueueOfStrings(10);
        queue.enqueue("you");
        queue.enqueue("are");
        queue.enqueue("the");
        queue.enqueue("best");
        queue.enqueue("of");
        queue.enqueue("times");
        queue.enqueue("times");
        queue.enqueue("times");
        queue.enqueue("times");
        queue.enqueue("times");
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        queue.enqueue("times");
        System.out.println(queue.headOfArray);
        System.out.println(queue.tailOfArray);
    }
}
