package com.github.ddth.commons.test.utils;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Supplier;

import org.junit.Assert;

import com.github.ddth.commons.utils.IdGenerator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IdGeneratorDistributedTest extends TestCase {

    public IdGeneratorDistributedTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(IdGeneratorDistributedTest.class);
    }

    /* ====================================================================== */
    final static int NUM_THREADS = 16;
    final static int NUM_LOOPS = 100_000;

    private void runTest(int numLoopsPerThread, Supplier<Object>[] funcList) throws Exception {
        int numThreads = funcList.length;
        Set<Object> ids = new ConcurrentSkipListSet<>();
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < numLoopsPerThread; j++) {
                    ids.add(funcList[index].get());
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

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testId48Distributed() throws Exception {
        Supplier<Object>[] funcList = new Supplier[4];
        for (int i = 0; i < 4; i++) {
            funcList[i] = IdGenerator.getInstance(i + 1)::generateId48;
        }
        runTest(NUM_LOOPS, funcList);
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testId48HexDistributed() throws Exception {
        Supplier<Object>[] funcList = new Supplier[4];
        for (int i = 0; i < 4; i++) {
            funcList[i] = IdGenerator.getInstance(i + 1)::generateId48Hex;
        }
        runTest(NUM_LOOPS, funcList);
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testId48AsciiDistributed() throws Exception {
        Supplier<Object>[] funcList = new Supplier[4];
        for (int i = 0; i < 4; i++) {
            funcList[i] = IdGenerator.getInstance(i + 1)::generateId48Ascii;
        }
        runTest(NUM_LOOPS, funcList);
    }

    /*----------------------------------------------------------------------*/

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testId64Distributed() throws Exception {
        Supplier<Object>[] funcList = new Supplier[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            funcList[i] = IdGenerator.getInstance(i + 1)::generateId64;
        }
        runTest(NUM_LOOPS, funcList);
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testId64HexDistributed() throws Exception {
        Supplier<Object>[] funcList = new Supplier[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            funcList[i] = IdGenerator.getInstance(i + 1)::generateId64Hex;
        }
        runTest(NUM_LOOPS, funcList);
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testId64AsciiDistributed() throws Exception {
        Supplier<Object>[] funcList = new Supplier[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            funcList[i] = IdGenerator.getInstance(i + 1)::generateId64Ascii;
        }
        runTest(NUM_LOOPS, funcList);
    }

    /*----------------------------------------------------------------------*/

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testId128Distributed() throws Exception {
        Supplier<Object>[] funcList = new Supplier[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            funcList[i] = IdGenerator.getInstance(i + 1)::generateId128;
        }
        runTest(NUM_LOOPS, funcList);
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testId128HexDistributed() throws Exception {
        Supplier<Object>[] funcList = new Supplier[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            funcList[i] = IdGenerator.getInstance(i + 1)::generateId128Hex;
        }
        runTest(NUM_LOOPS, funcList);
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testId128AsciiDistributed() throws Exception {
        Supplier<Object>[] funcList = new Supplier[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            funcList[i] = IdGenerator.getInstance(i + 1)::generateId128Ascii;
        }
        runTest(NUM_LOOPS, funcList);
    }
}
