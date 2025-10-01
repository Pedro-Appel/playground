package com.appel.experiments.sorting;

import java.util.Random;

public class ArrayGenerator {

    public static int[] generateArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(1000);
        }

        return arr;
    }
}