package first.week.lectures;

import edu.princeton.cs.algs4.Stopwatch;

public class ThreeSum {

    public static int count(int[] arrayOfIntegers) {
        int numberOfTriplesSumToZero = 0;
        int arrayLength = arrayOfIntegers.length;
        for (int i = 0; i < arrayLength; i++) {
            for (int j = i + 1; j < arrayLength; j++) {
                for (int k = j + 1; k < arrayLength; k++) {
                    if(arrayOfIntegers[i] + arrayOfIntegers[j] + arrayOfIntegers[k] == 0) {
                        numberOfTriplesSumToZero++;
                    }
                }
            }
        }
        return numberOfTriplesSumToZero;
    }

    public static void main(String[] args) {
        int[] integersToFindSum = {30, -30, 0, 40, -30, -10};
        Stopwatch stopwatch = new Stopwatch();
        System.out.println(count(integersToFindSum));
        double timeSinceCreation = stopwatch.elapsedTime();
        System.out.println(timeSinceCreation);
    }
}
