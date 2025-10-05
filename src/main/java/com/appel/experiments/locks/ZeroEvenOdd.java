package com.appel.experiments.locks;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class ZeroEvenOdd {
    private final int n;
    private int number = 1;
    private final Semaphore numberTurn;
    private final Semaphore zeroTurn;

    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(5);
        Thread threadZero = new Thread(() -> {
            try {
                zeroEvenOdd.zero(System.out::println);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread threadEven = new Thread(() -> {
            try {
                zeroEvenOdd.even(System.out::println);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread threadOdd = new Thread(() -> {
            try {
                zeroEvenOdd.odd(System.out::println);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        threadZero.start();
        threadEven.start();
        threadOdd.start();
    }

    public ZeroEvenOdd(int n) {
        this.n = n;
        this.numberTurn = new Semaphore(1);
        this.zeroTurn = new Semaphore(1);
        this.numberTurn.acquireUninterruptibly();
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        while (this.number <= this.n) {
            if (zeroTurn.tryAcquire()) {
                printNumber.accept(0);
                numberTurn.release();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        while (this.number <= this.n) {
            if (this.number % 2 == 0) {
                if (numberTurn.tryAcquire()) {
                    printNumber.accept(this.number++);
                    zeroTurn.release();
                }
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        while (this.number <= this.n) {
            if (this.number % 2 != 0) {
                if (numberTurn.tryAcquire()) {
                    printNumber.accept(this.number++);
                    zeroTurn.release();
                }
            }
        }
    }
}