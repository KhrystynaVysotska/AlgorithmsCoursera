import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N = 0;
    private Item[] randomQueue = (Item[]) new Object[1];
    private int size = 0;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        } else {
            if (N < randomQueue.length) {
                randomQueue[N++] = item;
                size++;
            } else if (size == randomQueue.length) {
                resize(2 * randomQueue.length);
                randomQueue[N++] = item;
                size++;
            } else if (size < randomQueue.length) {
                reset();
                randomQueue[N++] = item;
                size++;
            }
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < randomQueue.length; i++) {
            copy[i] = randomQueue[i];
        }
        randomQueue = copy;
    }

    private void reset() {
        N = size;
        Item[] copy = (Item[]) new Object[randomQueue.length];
        int counter = 0;
        for (int i = 0; i < randomQueue.length; i++) {
            if (counter < randomQueue.length && randomQueue[counter] != null) {
                copy[i] = randomQueue[counter];
                counter++;
            } else {
                while (counter < randomQueue.length && randomQueue[counter] == null) {
                    counter++;
                }
                if (counter < randomQueue.length) {
                    copy[i] = randomQueue[counter++];
                }
            }
        }
        randomQueue = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            int index = StdRandom.uniform(randomQueue.length);
            while (randomQueue[index] == null) {
                index = StdRandom.uniform(randomQueue.length);
            }
            Item item = randomQueue[index];
            randomQueue[index] = null;
            size--;
            return item;
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            int index = StdRandom.uniform(randomQueue.length);
            while (randomQueue[index] == null) {
                index = StdRandom.uniform(randomQueue.length);
            }
            return randomQueue[index];
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        Item[] copy = randomQueue;
        int amountOfItems = size;

        @Override
        public boolean hasNext() {
            return amountOfItems != 0;
        }

        @Override
        public Item next() {
            if (amountOfItems == 0) {
                throw new NoSuchElementException();
            } else {
                int index = StdRandom.uniform(copy.length);
                while (copy[index] == null) {
                    index = StdRandom.uniform(copy.length);
                }
                Item item = copy[index];
                copy[index] = null;
                amountOfItems--;
                return item;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        System.out.println(queue.isEmpty());
        queue.enqueue("I");
        queue.enqueue("love");
        queue.enqueue("you");
        System.out.println(queue.sample());
        System.out.println(queue.size());
        System.out.println(queue.dequeue());
        System.out.println(queue.size());
        System.out.println(queue.isEmpty());
        for(String s:queue) {
            System.out.println(s);
        }
    }

}