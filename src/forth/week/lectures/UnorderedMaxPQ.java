package forth.week.lectures;

//unordered array implementation
public class UnorderedMaxPQ<Key extends Comparable<Key>> {
    private Key[] priorityQueue;
    private int size = 0;
    public UnorderedMaxPQ(int capacity) {
        priorityQueue = (Key[]) new Comparable[capacity];
    }
    public boolean isEmpty() {
        return size == 0;
    }
    public int size() {
        return size;
    }
    public void insert(Key value) {
        priorityQueue[size++] = value;
    }
    private static void exchange(Comparable[] array, int i, int j) {
        Comparable swap =array[i];
        array[i] = array[j];
        array[j] = swap;
    }
    
    public Key delMax() {
        int max = 0;
        for(int i = 1; i < size; i++) {
            if(priorityQueue[max].compareTo(priorityQueue[i]) < 0) {
                max = i;
            }
        }
        exchange(priorityQueue, max, size - 1);
        Key deletedItem = priorityQueue[size-1];
        priorityQueue[--size] = null;
        return deletedItem;
    }
    public static void main(String[] args) {
        UnorderedMaxPQ<Integer> queue = new UnorderedMaxPQ<>(10);
        queue.insert(1);
        queue.insert(3);
        queue.insert(6);
        System.out.println(queue.delMax());
        queue.insert(2);
        System.out.println(queue.delMax());
        System.out.println(queue.size);
    }
}
