package second.week.lectures;

public class Selection {
    public static void sort(Comparable[] array) {
        int length = array.length;
        for (int i = 0; i < length; i++) {
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if (less(array[j], array[min])) {
                    min = j;
                }
            }
            exchange(array, i, min);
        }
    }

    public static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    public static void exchange(Comparable[] array, int i, int j) {
        Comparable swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }

    public static void main(String[] args) {
        Integer[] array = { 2, 7, 3, 9, 2, 5, 1, 0, -3, 43, 34 };
        sort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
