import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int numberOfStringsToDisplay = Integer.parseInt(args[0]);
        int counter = 0;
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            queue.enqueue(s);
        }
        for (String s : queue) {
            if (counter < numberOfStringsToDisplay) {
                System.out.println(s);
                counter++;
            }
        }

    }
}
