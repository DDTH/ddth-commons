package com.github.ddth.commons.test.utils;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Supplier;

import com.github.ddth.commons.utils.IdGenerator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IdGeneratorTest extends TestCase {

    public IdGeneratorTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(IdGeneratorTest.class);
    }

    @org.junit.Test
    public void testWaitTillNextMillisec() {
        long now = System.currentTimeMillis();
        long timestamp = now;
        IdGenerator.waitTillNextMillisec(timestamp);
        long t = System.currentTimeMillis();
        assertTrue(t - timestamp >= 1);
        System.out.println("testWaitTillNextMillisec:\t" + now + "\t" + t + "\t" + (t - now));
    }

    @org.junit.Test
    public void testWaitTillNextSecond() {
        long now = System.currentTimeMillis();
        long timestamp = now / 1000;
        IdGenerator.waitTillNextSecond(timestamp);
        long t = System.currentTimeMillis();
        assertTrue(t - timestamp * 1000 >= 1000);
        System.out.println("testWaitTillNextSecond:\t\t" + now + "\t" + t + "\t" + (t - now));
    }

    @org.junit.Test
    public void testWaitTillNextTick() {
        long now = System.currentTimeMillis();
        long tickSize = 4096;
        long timestamp = now / tickSize;
        IdGenerator.waitTillNextTick(timestamp, tickSize);
        long t = System.currentTimeMillis();
        assertTrue(t - timestamp * tickSize >= tickSize);
        System.out.println("testWaitTillNextTick:\t\t" + now + "\t" + t + "\t" + (t - now));
    }

    /*----------------------------------------------------------------------*/

    private void runTest(int numThreads, int numItemsPerThread, Supplier<Object> func)
            throws Exception {
        Set<Object> ids = new ConcurrentSkipListSet<>();
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread() {
                public void run() {
                    for (int i = 0; i < numItemsPerThread; i++) {
                        ids.add(func.get());
                    }
                }
            };
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        assertEquals(ids.size(), numThreads * numItemsPerThread);
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testIdTiny() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateIdTiny);
    }

    @org.junit.Test
    public void testIdTinyHex() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateIdTinyHex);
    }

    @org.junit.Test
    public void testIdTinyAscii() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateIdTinyAscii);
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testIdMini() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateIdMini);
    }

    @org.junit.Test
    public void testIdMiniHex() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateIdMiniHex);
    }

    @org.junit.Test
    public void testIdMiniAscii() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateIdMiniAscii);
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testId48() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateId48);
    }

    @org.junit.Test
    public void testId48Hex() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateId48Hex);
    }

    @org.junit.Test
    public void testId48Ascii() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateId48Ascii);
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testId64() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateId64);
    }

    @org.junit.Test
    public void testId64Hex() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateId64Hex);
    }

    @org.junit.Test
    public void testId64Ascii() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateId64Ascii);
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testId128() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateId128);
    }

    @org.junit.Test
    public void testId128Hex() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateId128Hex);
    }

    @org.junit.Test
    public void testId128Ascii() throws Exception {
        runTest(8, 10000, IdGenerator.getInstance()::generateId128Ascii);
    }

    /*----------------------------------------------------------------------*/
}
