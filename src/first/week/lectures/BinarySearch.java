package first.week.lectures;

public class BinarySearch {
    public static int binarySearch(int[] array, int key) {
        int lowIndex = 0;
        int highIndex = array.length - 1;
        while (lowIndex <= highIndex) {
            int middleIndex = lowIndex + (highIndex - lowIndex) / 2;
            if (key < array[middleIndex]) {
                highIndex = middleIndex - 1;
            } else if (key > array[middleIndex]) {
                lowIndex = middleIndex + 1;
            } else {
                return middleIndex;
            }
        }
        return -1;
    }
    public static void main(String...args) {
        int[] array = {6, 13, 14, 25, 33, 43, 51, 53, 64, 72, 84, 93, 95, 96, 97};
        System.out.println(binarySearch(array, 33));
    }
}

