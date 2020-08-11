package sixth.week.lectures;

import java.util.TreeSet;

public class FourSumProblem {
    private static TreeSet<Integer> set = new TreeSet<>();

    public static boolean exist(int[] array) {
        for(int i = 0; i < array.length; i++) {
            for(int j = i + 1; j < array.length; j++) {
                if(!set.isEmpty()) {
                    if(set.contains(array[i] + array[j])) {
                        return true;
                    }
                }
                set.add(array[i] + array[j]);
            }
        }
        return false;
    }
}
