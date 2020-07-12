package second.week.lectures;

public class FixedCapacityStackOfStrings {
    private String[] stack;
    private int topOfTheStack = 0;
    
    public FixedCapacityStackOfStrings(int capacity) {
        stack = new String[capacity];
    }
    
    public void push(String item) {
        stack[topOfTheStack++] = item;
    }
    
    public String pop(){
        String oldTop = stack[--topOfTheStack];
        stack[topOfTheStack] = null;
        return oldTop;
    }
    
    public boolean isEmpty() {
        return topOfTheStack == 0;
    }
}
