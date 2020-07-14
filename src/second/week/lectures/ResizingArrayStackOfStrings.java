package second.week.lectures;

import java.util.Iterator;

public class ResizingArrayStackOfStrings<T> implements Iterable<T>{
    private T[] stack = (T[])new Object[1];
    private int N = 0;

    public void push(T item) {
        if (N == stack.length) {
            resize(2 * stack.length);
        }
        stack[N++] = item;
    }
    
    public T pop() {
        T oldFirst = stack[--N];
        stack[N] = null;
        if(N > 0 && N == stack.length/4) {
            resize(stack.length/2);
        }
        return oldFirst;
    }

    public void resize(int capacity) {
        T[] newArray = (T[])new Object[capacity];
        for(int i = 0; i < N; i++) {
            newArray[i] = stack[i];
        }
        stack = newArray;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new ReverseArrayIterator();
    }
    
    private class ReverseArrayIterator implements Iterator<T> {
        int current = N;
        @Override
        public boolean hasNext() {
            return current > 0;
        }

        @Override
        public T next() {
            return stack[--current];
        }
    }
    
    public static void main(String...args) {
        ResizingArrayStackOfStrings<String> stack = new ResizingArrayStackOfStrings<>();
        for(String s:stack) {
            System.out.println();
        }
    }
}
