package third.week.lectures;

import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {
    private static final int CUTOFF = 10;
    public static void sort(Comparable[] array) {
        StdRandom.shuffle(array);
        sort(array, 0, array.length - 1);
    }

    private static void sort(Comparable[] array, int left, int right) {
        if(right <= left + CUTOFF -1) {
            Insertion.sort(array);
            return;
        }
        int j = partition(array, left, right);
        sort(array, left, j - 1);
        sort(array, j + 1, right);
    }
    private static void swap(Comparable[] array, int i, int j) {
        Comparable swap = array[j];
        array[j] = array[i];
        array[i] = swap;

    }

    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static int partition(Comparable[] array, int left, int right) {
        int i = left;
        int j = right + 1;
        while (true) {
            while (less(array[++i], array[left])) {
                if (i == right) {
                    break;
                }
            }
            while (less(array[left], array[--j])) {
                if (j == left) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            swap(array, i, j);
        }
        swap(array, left, j);
        return j;
    }
    public static Comparable select(Comparable[] array, int k) {
        StdRandom.shuffle(array);
        int left = 0;
        int right = array.length - 1;
        while(right > left) {
            int j = partition(array, left, right);
            if(j > k) {
                right = j - 1;
            } else if(j < k) {
                left = j + 1;
            } else {
                return array[k];
            }
        }
        return array[k];
    }
}
