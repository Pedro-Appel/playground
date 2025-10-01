package com.appel.experiments.benchmarking;

import com.appel.experiments.sorting.ArrayGenerator;
import com.appel.experiments.sorting.ParallelSort;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
public class ParallelExecutionPlan {

    @Param({ "10000", "50000", "100000", "200000" })
    public int size;
    public int[] unsortedArray;
    public ParallelSort parallelSort;

    @Setup(Level.Invocation)
    public void setUp() {
        parallelSort = new ParallelSort();
        unsortedArray = ArrayGenerator.generateArray(size);
    }
}