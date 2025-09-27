package sorting.experiments;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.Executors;


public class ThreadSort {

    List<Integer> arraySizes = List.of(10,20,50,100,200,500,1000,3000,5000,10000,50000);
    List<Analysis> analysisList = new ArrayList<>();

    void main() {
        for(Integer size : arraySizes) {
            analysisList.add(new Analysis(size));
        }
        while (true){
            analysisList.forEach((v)->{
                int[] unsortedArray = ArrayGenerator.generateArray(v.size);
                Instant start = Instant.now();
                int loops = sort(unsortedArray);
                Instant end = Instant.now();
                v.increment(loops, end.toEpochMilli()-start.toEpochMilli());
                IO.println("Array with size %d sorted in [ %d ] steps and [ %s ]ms".formatted(v.size, v.getSteps(), v.getMillis()));
            });
            resetConsole();
        }
    }
    public static void resetConsole() {
        System.out.print("\033[11A");
    }

    int[] sortable;
    final Object sortableLock = new Object();

    int sort(int[] sortable) {
        this.sortable = sortable;
        int loops = 0;
        while (!sorted()) {
            try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (int i = 0; i < sortable.length; i++) {
                    executor.submit(new ArraySorter(i, this));
                }
            }
            loops++;
        }
        return loops;
    }

    private boolean sorted() {
        for (int i = 0; i < this.sortable.length - 1; i++) {
            if (this.sortable[i] > this.sortable[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private void updateIndexes(int i) {
        synchronized (this.sortableLock){
            int i1 = sortable[i];
            int i2 = sortable[i + 1];
            sortable[i] = i2;
            sortable[i + 1] = i1;
            this.sortableLock.notifyAll();
        }
    }

    private class ArraySorter implements Runnable {
        private int i;
        private final ThreadSort threadSort;

        public ArraySorter(int i, ThreadSort threadSort) {
            this.i = i;
            this.threadSort = threadSort;
        }

        @Override
        public void run() {
            if (sortable[i] > sortable[i + 1]) {
                try {
                    threadSort.updateIndexes(i);
                } catch (Exception e) {
                    throw new  RuntimeException(e);
                }
            }
        }
    }

    static class Analysis {
        int size;
        int steps;
        long millis;
        int n;

        public Analysis(Integer size) {
            this.size = size;
        }


        public int getSteps() {
            return steps/n;
        }

        public long getMillis() {
            return millis/n;
        }

        void increment(int steps, long millis) {
            this.steps += steps;
            this.millis += millis;
            this.n++;
        }
    }
}