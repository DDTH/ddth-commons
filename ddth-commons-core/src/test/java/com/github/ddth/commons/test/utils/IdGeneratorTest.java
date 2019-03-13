package com.github.ddth.commons.test.utils;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Supplier;

import org.junit.Assert;

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
        long timestamp = System.currentTimeMillis();
        long nextMs = IdGenerator.waitTillNextMillisec(timestamp);
        long now = System.currentTimeMillis();
        Assert.assertTrue(nextMs - timestamp > 0);
        Assert.assertTrue(now >= nextMs);
    }

    @org.junit.Test
    public void testWaitTillNextSecond() {
        long timestamp = System.currentTimeMillis();
        long nextSec = IdGenerator.waitTillNextSecond(timestamp / 1000);
        long now = System.currentTimeMillis();
        Assert.assertTrue(nextSec - timestamp / 1000 > 0);
        Assert.assertTrue(now / 1000 >= nextSec);
    }

    @org.junit.Test
    public void testWaitTillNextTick() {
        long tickSize = 4096;
        long timestamp = System.currentTimeMillis();
        long nextTick = IdGenerator.waitTillNextTick(timestamp / tickSize, tickSize);
        long now = System.currentTimeMillis();
        Assert.assertTrue(nextTick - timestamp / tickSize > 0);
        Assert.assertTrue(now / tickSize >= nextTick);
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testGetInstance1() throws Exception {
        IdGenerator idGen1 = IdGenerator.getInstance();
        IdGenerator idGen2 = IdGenerator.getInstance();
        Assert.assertEquals(idGen1, idGen2);
    }

    @org.junit.Test
    public void testGetInstance2() throws Exception {
        int numThreads = 32;
        IdGenerator[] idGenList = new IdGenerator[numThreads];
        Thread[] threadList = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            final int j = i;
            threadList[i] = new Thread(() -> idGenList[j] = IdGenerator.getInstance());
        }
        for (Thread t : threadList) {
            t.start();
        }
        for (Thread t : threadList) {
            t.join();
        }
        for (int i = 0; i < numThreads - 1; i++) {
            Assert.assertEquals(idGenList[i], idGenList[i + 1]);
        }
    }

    @org.junit.Test
    public void testGetInstance3() throws Exception {
        final long nodeId = System.currentTimeMillis();
        IdGenerator idGen1 = IdGenerator.getInstance(nodeId);
        IdGenerator idGen2 = IdGenerator.getInstance(nodeId);
        Assert.assertEquals(idGen1, idGen2);
    }

    @org.junit.Test
    public void testGetInstance4() throws Exception {
        final long nodeId = System.currentTimeMillis();
        int numThreads = 32;
        IdGenerator[] idGenList = new IdGenerator[numThreads];
        Thread[] threadList = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            final int j = i;
            threadList[i] = new Thread(() -> idGenList[j] = IdGenerator.getInstance(nodeId));
        }
        for (Thread t : threadList) {
            t.start();
        }
        for (Thread t : threadList) {
            t.join();
        }
        for (int i = 0; i < numThreads - 1; i++) {
            Assert.assertEquals(idGenList[i], idGenList[i + 1]);
        }
    }

    /* ====================================================================== */
    final static int NUM_THREADS = 16;
    final static int NUM_LOOPS = 100_000;

    private void runTest(int numThreads, int numLoopsPerThread, Supplier<Object> func)
            throws Exception {
        Set<Object> ids = new ConcurrentSkipListSet<>();
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < numLoopsPerThread; j++) {
                    ids.add(func.get());
                }
            });
        }
        for (Thread t : threads) {
            t.start();
        }
        for (Thread t : threads) {
            t.join();
        }
        Assert.assertEquals(numThreads * numLoopsPerThread, ids.size());
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testIdTiny() throws Exception {
        runTest(NUM_THREADS / 2, NUM_LOOPS / 4, IdGenerator.getInstance()::generateIdTiny);
    }

    @org.junit.Test
    public void testIdTinyHex() throws Exception {
        runTest(NUM_THREADS / 2, NUM_LOOPS / 4, IdGenerator.getInstance()::generateIdTinyHex);
    }

    @org.junit.Test
    public void testIdTinyAscii() throws Exception {
        runTest(NUM_THREADS / 2, NUM_LOOPS / 4, IdGenerator.getInstance()::generateIdTinyAscii);
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testIdMini() throws Exception {
        runTest(NUM_THREADS, NUM_LOOPS, IdGenerator.getInstance()::generateIdMini);
    }

    @org.junit.Test
    public void testIdMiniHex() throws Exception {
        runTest(NUM_THREADS, NUM_LOOPS, IdGenerator.getInstance()::generateIdMiniHex);
    }

    @org.junit.Test
    public void testIdMiniAscii() throws Exception {
        runTest(NUM_THREADS, NUM_LOOPS, IdGenerator.getInstance()::generateIdMiniAscii);
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testId48() throws Exception {
        runTest(NUM_THREADS / 2, NUM_LOOPS / 4, IdGenerator.getInstance()::generateId48);
    }

    @org.junit.Test
    public void testId48Hex() throws Exception {
        runTest(NUM_THREADS / 2, NUM_LOOPS / 4, IdGenerator.getInstance()::generateId48Hex);
    }

    @org.junit.Test
    public void testId48Ascii() throws Exception {
        runTest(NUM_THREADS / 2, NUM_LOOPS / 4, IdGenerator.getInstance()::generateId48Ascii);
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testId64() throws Exception {
        runTest(NUM_THREADS, NUM_LOOPS, IdGenerator.getInstance()::generateId64);
    }

    @org.junit.Test
    public void testId64Hex() throws Exception {
        runTest(NUM_THREADS, NUM_LOOPS, IdGenerator.getInstance()::generateId64Hex);
    }

    @org.junit.Test
    public void testId64Ascii() throws Exception {
        runTest(NUM_THREADS, NUM_LOOPS, IdGenerator.getInstance()::generateId64Ascii);
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testId128() throws Exception {
        runTest(NUM_THREADS, NUM_LOOPS, IdGenerator.getInstance()::generateId128);
    }

    @org.junit.Test
    public void testId128Hex() throws Exception {
        runTest(NUM_THREADS, NUM_LOOPS, IdGenerator.getInstance()::generateId128Hex);
    }

    @org.junit.Test
    public void testId128Ascii() throws Exception {
        runTest(NUM_THREADS, NUM_LOOPS, IdGenerator.getInstance()::generateId128Ascii);
    }
}
