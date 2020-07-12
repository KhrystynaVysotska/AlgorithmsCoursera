package second.week.lectures;

public class ResizingArrayStackOfStrings {
    private String[] stack = new String[1];
    private int N = 0;

    public void push(String item) {
        if (N == stack.length) {
            resize(2 * stack.length);
        }
        stack[N++] = item;
    }
    
    public String pop() {
        String oldFirst = stack[--N];
        stack[N] = null;
        if(N > 0 && N == stack.length/4) {
            resize(stack.length/2);
        }
        return oldFirst;
    }

    public void resize(int capacity) {
        String[] newArray = new String[capacity];
        for(int i = 0; i < N; i++) {
            newArray[i] = stack[i];
        }
        stack = newArray;
    }
}
