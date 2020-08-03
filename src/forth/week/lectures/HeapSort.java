package forth.week.lectures;

public class HeapSort {
    public static void sort(Comparable[] array) {
        int N = array.length;
        for (int k = N / 2; k >= 1; k--) {
            sink(array, k, N);
        }
        while (N > 1) {
            exchange(array, 1, N--);
            sink(array, 1, N);
        }
    }

    private static void sink(Comparable[] array, int k, int N) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(array, j, j + 1)) {
                j++;
            }
            if (!less(array, k, j)) {
                break;
            }
            exchange(array, k, j);
            k = j;
        }
    }

    private static boolean less(Comparable[] array, int i, int j) {
        return array[i - 1].compareTo(array[j - 1]) < 0;
    }

    private static void exchange(Comparable[] array, int k, int j) {
        Comparable swap = array[k - 1];
        array[k - 1] = array[j - 1];
        array[j - 1] = swap;
    }

    public static void main(String[] args) {
        Integer[] array = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        sort(array);
        for(Integer i:array) {
            System.out.println(i);
        }
    }
}
