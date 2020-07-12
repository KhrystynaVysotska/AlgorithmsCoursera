package first.week.lectures;

public class ThreeSumFaster {
    public void merge(int[] array, int middleIndex, int leftIndex, int rightIndex) {
        int sizeOfFirstArray = (middleIndex - leftIndex) + 1;
        int sizeOfSecondArray = rightIndex - middleIndex;
        int[] firstTempArray = new int[sizeOfFirstArray];
        int[] secondTempArray = new int[sizeOfSecondArray];
        for (int x = 0; x < sizeOfFirstArray; x++) {
            firstTempArray[x] = array[leftIndex + x];
        }
        for (int z = 0; z < sizeOfSecondArray; z++) {
            secondTempArray[z] = array[middleIndex + 1 + z];
        }
        int i = 0;
        int j = 0;
        int k = leftIndex;
        while (i < sizeOfFirstArray && j < sizeOfSecondArray) {
            if (firstTempArray[i] <= secondTempArray[j]) {
                array[k] = firstTempArray[i];
                i++;
            } else {
                array[k] = secondTempArray[j];
                j++;
            }
            k++;
        }
        while (i < sizeOfFirstArray) {
            array[k] = firstTempArray[i];
            i++;
            k++;
        }
        while (j < sizeOfSecondArray) {
            array[k] = secondTempArray[j];
            j++;
            k++;
        }
    }

    public void sort(int[] array, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;
            sort(array, leftIndex, middleIndex);
            sort(array, middleIndex + 1, rightIndex);
            merge(array, middleIndex, leftIndex, rightIndex);
        }
    }

    public int binarySearch(int[] array, int leftIndex, int rightIndex, int key) {
        if (leftIndex <= rightIndex) {
            int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;
            if (key < array[middleIndex]) {
                return binarySearch(array, leftIndex, middleIndex - 1, key);
            } else if (key > array[middleIndex]) {
                return binarySearch(array, middleIndex + 1, rightIndex, key);
            } else {
                return middleIndex;
            }
        }
        return -1;
    }

    public int count(int[] arrayOfIntegers) {
        sort(arrayOfIntegers, 0, arrayOfIntegers.length - 1);
        int numberOfTriples = 0;
        int oppositeToSum = 0;
        for (int i = 0; i < arrayOfIntegers.length; i++) {
            for (int j = i + 1; j < arrayOfIntegers.length; j++) {
                oppositeToSum = binarySearch(arrayOfIntegers, 0, arrayOfIntegers.length - 1,
                        -(arrayOfIntegers[i] + arrayOfIntegers[j]));
                if (oppositeToSum == -1) {
                    continue;
                } else {
                    if (arrayOfIntegers[i] < arrayOfIntegers[j]
                            && arrayOfIntegers[j] < arrayOfIntegers[oppositeToSum]) {
                        numberOfTriples++;
                    }
                }
            }
        }
        return numberOfTriples;
    }

    public static void main(String[] args) {
        int[] arrayOfIntegers = {30, 0, 40, -30, -10};
        ThreeSumFaster threeSum = new ThreeSumFaster();
        System.out.println(threeSum.count(arrayOfIntegers));
    }
}
