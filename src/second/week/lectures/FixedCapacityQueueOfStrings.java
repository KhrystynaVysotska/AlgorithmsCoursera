package second.week.lectures;

public class FixedCapacityQueueOfStrings {
    private String[] array;
    private int headOfArray = 0;
    private int tailOfArray = 0;
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
}
