package second.week.lectures;

public class ResizingArrayQueueOfStrings {
    private String[] array = new String[1];
    private int headOfArray = 0;
    private int tailOfArray = 0;

    public void enqueue(String item) {
        if (tailOfArray == array.length) {
            this.reset();
        }
        if (tailOfArray == array.length) {
            this.resize(array.length * 2);
        }
        array[tailOfArray++] = item;
    }

    public String dequeue() {
        if (headOfArray == tailOfArray) {
            headOfArray = 0;
            tailOfArray = 0;
        }
        if (isEmpty()) {
            return "The queue is empty! Nothing to dequeue";
        } else {
            String oldHead = array[headOfArray];
            array[headOfArray] = null;
            headOfArray++;
            return oldHead;
        }
    }

    public boolean isEmpty() {
        return tailOfArray == 0;
    }

    private void resize(int capacity) {
        String[] copy = new String[capacity];
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i];
        }
        array = copy;
    }

    private void reset() {
        tailOfArray = array.length - headOfArray;
        String[] copy = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            if (headOfArray < array.length) {
                copy[i] = array[headOfArray];
                headOfArray++;
            }
        }
        array = copy;
        headOfArray = 0;
    }
}
