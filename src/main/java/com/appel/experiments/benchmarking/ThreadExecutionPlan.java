package com.appel.experiments.benchmarking;

import com.appel.experiments.sorting.ArrayGenerator;
import com.appel.experiments.sorting.ParallelBubbleSort;
import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
public class ThreadExecutionPlan {

    @Param({ "500", "1000", "5000", "10000", "50000" })
    public int size;
    public int[] unsortedArray;
    public ParallelBubbleSort parallelBubbleSort;

    @Setup(Level.Invocation)
    public void setUp() {
        parallelBubbleSort = new ParallelBubbleSort();
        unsortedArray = ArrayGenerator.generateArray(size);
    }
}