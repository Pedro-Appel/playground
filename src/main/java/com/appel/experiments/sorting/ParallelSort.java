package com.appel.experiments.sorting;

import java.util.Arrays;

public class ParallelSort {

    public int sort(int[] sortable) {
        Arrays.parallelSort(sortable);
        return 0;
    }
}