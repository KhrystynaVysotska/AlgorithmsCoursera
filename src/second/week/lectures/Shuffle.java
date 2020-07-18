package second.week.lectures;

import edu.princeton.cs.algs4.StdRandom;

public class Shuffle {
    public static void exchange(Comparable[] array, int i, int j) {
        Comparable swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }
    public static void shuffle(Comparable[] a) {
        for(int i = 0; i < a.length; i++) {
            int r = StdRandom.uniform(i + 1);
            exchange(a, r, i);
        }
    }
    public static void main(String[] args) {
        Integer[] array = {1,2,3,4,5,6,7,8,9,10};
        shuffle(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
