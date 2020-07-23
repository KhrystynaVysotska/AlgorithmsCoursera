package third.week.lectures;

public class Merge {
    private static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    private static boolean isSorted(Comparable[] array, int leftBorder, int rightBorder) {
        for (int i = leftBorder; i < rightBorder; i++) {
            if (less(array[i + 1], array[i])) {
                return false;
            }
        }
        return true;
    }

    private static void merge(Comparable[] array, Comparable[] aux, int leftBorder, int middle, int rightBorder) {
        assert isSorted(array, leftBorder, middle);
        assert isSorted(array, middle + 1, rightBorder);

        // copy original array
        for (int i = 0; i < array.length; i++) {
            aux[i] = array[i];
        }
        int counterOfFirstArray = leftBorder;
        int counterOfSecondArray = middle + 1;
        for (int pointerOfOriginalArray = leftBorder; pointerOfOriginalArray < rightBorder; pointerOfOriginalArray++) {
            if (counterOfFirstArray > middle) {
                array[pointerOfOriginalArray] = aux[counterOfSecondArray++];
            } else if (counterOfSecondArray > rightBorder) {
                array[pointerOfOriginalArray] = aux[counterOfFirstArray++];
            } else if (less(aux[counterOfSecondArray], aux[counterOfFirstArray])) {
                array[pointerOfOriginalArray] = aux[counterOfSecondArray++];
            } else {
                array[pointerOfOriginalArray] = aux[counterOfFirstArray++];
            }
        }
        assert isSorted(array, leftBorder, rightBorder);
    }

    private static void sort(Comparable[] array, Comparable[] aux, int leftBorder, int rightBorder) {
        if (rightBorder <= leftBorder)
            return;
        int middle = leftBorder + (rightBorder - leftBorder)/2;
        sort(array, aux, leftBorder, middle);
        sort(array, aux, middle + 1, rightBorder);
        merge(array, aux, leftBorder, middle, rightBorder);
    }
    
    public static void sort(Comparable[] array) {
        Comparable[] aux = new Comparable[array.length];
        sort(array, aux, 0, array.length - 1);
    }
}
