package com.appel.experiments.benchmarking;

import com.appel.experiments.sorting.ArrayGenerator;
import com.appel.experiments.sorting.QuickSort;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
public class QuickSortExecutionPlan {

    @Param({ "10000", "50000", "100000", "200000" })
    public int size;
    public int[] unsortedArray;
    public QuickSort quickSort;

    @Setup(Level.Invocation)
    public void setUp() {
        quickSort = new QuickSort();
        unsortedArray = ArrayGenerator.generateArray(size);
    }
}