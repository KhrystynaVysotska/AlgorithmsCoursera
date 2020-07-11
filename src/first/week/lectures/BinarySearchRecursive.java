package first.week.lectures;

public class BinarySearchRecursive {
    public static int binarySearch(int[] array, int leftIndex, int rightIndex, int key) {
        if(leftIndex <= rightIndex) {
            int middle = leftIndex + (rightIndex - leftIndex)/2;
            if(key < array[middle]) {
                return binarySearch(array, leftIndex, middle - 1, key);
            } else if(key > array[middle]) {
                return binarySearch(array, middle + 1, rightIndex, key);
            } else {
                return middle;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        int[] array = {6, 13, 14, 25, 33, 43, 51, 53, 64, 72, 84, 93, 95, 96, 97};
        System.out.println(binarySearch(array, 0, array.length - 1, 84));
    }

}
