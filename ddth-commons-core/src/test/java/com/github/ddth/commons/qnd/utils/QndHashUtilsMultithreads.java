package com.github.ddth.commons.qnd.utils;

import com.github.ddth.commons.utils.HashUtils;

public class QndHashUtilsMultithreads {

    public static class TestThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(HashUtils.md5(String.valueOf(System.currentTimeMillis())));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread t = new TestThread();
            t.start();
        }
    }

}
