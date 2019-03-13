package com.github.ddth.commons.qnd;

import java.util.concurrent.locks.LockSupport;

// https://www.geeksforgeeks.org/java-concurrency-yield-sleep-and-join-methods/

class MyThread1 extends Thread {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " in control.");
        }
    }
}

class MyThread2 extends Thread {
    public void run() {
        for (int i = 0; i < 10; i++) {
            Thread.yield();
            System.out.println(Thread.currentThread().getName() + " in control.");
        }
    }
}

public class QndPause {
    public static void main(String[] args) throws InterruptedException {
        // MyThread1 t1 = new MyThread1();
        // t1.start();
        // MyThread2 t2 = new MyThread2();
        // t2.start();
        // for (int i = 0; i < 10; i++) {
        // Thread.yield();
        // System.out.println(Thread.currentThread().getName() + " in control.");
        // }

        int repeat = 10000;
        for (int i = 0; i < 3; i++) {
            long time0 = System.nanoTime();
            for (int j = 0; j < repeat; j++)
                Thread.yield();
            long time1 = System.nanoTime();
            for (int j = 0; j < repeat; j++)
                Thread.sleep(0);
            long time2 = System.nanoTime();
            synchronized (Thread.class) {
                for (int j = 0; j < repeat / 10; j++)
                    Thread.class.wait(0, 1);
            }
            long time3 = System.nanoTime();
            for (int j = 0; j < repeat / 10; j++)
                LockSupport.parkNanos(1);
            long time4 = System.nanoTime();

            System.out.printf(
                    "The average time to yield %.1f μs, sleep(0) %.1f μs, "
                            + "wait(0,1) %.1f μs and LockSupport.parkNanos(1) %.1f μs%n",
                    (time1 - time0) / repeat / 1e3, (time2 - time1) / repeat / 1e3,
                    (time3 - time2) / (repeat / 10) / 1e3, (time4 - time3) / (repeat / 10) / 1e3);
        }
    }
}
