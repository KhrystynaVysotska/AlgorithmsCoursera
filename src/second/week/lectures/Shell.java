package second.week.lectures;

public class Shell {
    public static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }
    
    public static void exchange(Comparable[] array, int i, int j) {
        Comparable swap = array[i];
        array[i] = array[j];
        array[j] = swap;
    }

    public static void sort(Comparable[] array) {
        int N = array.length;
        int h = 1;
        while (h < N / 3) {
            h = 3 * h + 1;
        }
        while(h >= 1) {
            for(int i = h; i < N; i++) {
                for(int j = i; j >= h && less(array[j], array[j - h]); j-=h){
                    exchange(array, j, j - h);
                }
            }
            h = h/3;
        }
    }
    public static void main(String[] args) {
        Integer[] array = { 2, 7, 3, 9, 2, 5, 1, 0, -3, 43, 34 };
        sort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
