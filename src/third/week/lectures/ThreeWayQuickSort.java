package third.week.lectures;

import edu.princeton.cs.algs4.StdRandom;

public class ThreeWayQuickSort {
    public static void sort(Comparable[] array) {
        StdRandom.shuffle(array);
        sort(array, 0, array.length - 1);
    }

    private static void exchange(Comparable[] array, int i, int j) {
        Comparable swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }

    private static void sort(Comparable[] array, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        Comparable pivot = array[lo];
        int lt = lo;
        int gt = hi;
        int i = lo;
        while (i <= gt) {
            int cmp = array[i].compareTo(pivot);
            if (cmp < 0) {
                exchange(array, lt++, i++);
            } else if (cmp > 0) {
                exchange(array, i, gt--);
            } else {
                i++;
            }
        }
        sort(array, lo, lt - 1);
        sort(array, gt + 1, hi);
    }

    public static void main(String[] args) {
        Integer[] array = { 0, 2, 6, 3, 4, 0, 3, 9, 6, 7, 3, 5, 2, 9 };
        ThreeWayQuickSort.sort(array);
        for(Integer i:array) {
            System.out.println(i);
        }
        
    }
}
