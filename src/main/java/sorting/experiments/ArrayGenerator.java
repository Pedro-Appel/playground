package sorting.experiments;

import java.util.Random;

public class ArrayGenerator {

    // Generates an array of size n filled with random integers
    public static int[] generateArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(1000); // values from 0 to 999
        }

        return arr;
    }
}