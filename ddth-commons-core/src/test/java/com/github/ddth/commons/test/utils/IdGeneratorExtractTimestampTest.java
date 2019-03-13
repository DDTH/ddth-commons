package com.github.ddth.commons.test.utils;

import java.math.BigInteger;
import java.util.Date;
import java.util.Random;

import org.junit.Assert;

import com.github.ddth.commons.utils.IdGenerator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class IdGeneratorExtractTimestampTest extends TestCase {

    public IdGeneratorExtractTimestampTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(IdGeneratorExtractTimestampTest.class);
    }

    @org.junit.Test
    public void testIdTinyExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            long id = IdGenerator.getInstance().generateIdTiny();
            long timestamp = IdGenerator.extractTimestampTiny(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testIdTinyHexExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            String id = IdGenerator.getInstance().generateIdTinyHex();
            long timestamp = IdGenerator.extractTimestampTiny(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testIdTinyAsciiExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            String id = IdGenerator.getInstance().generateIdTinyAscii();
            long timestamp = IdGenerator.extractTimestampTinyAscii(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testIdMiniExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            long id = IdGenerator.getInstance().generateIdMini();
            long timestamp = IdGenerator.extractTimestampMini(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testIdMiniHexExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            String id = IdGenerator.getInstance().generateIdMiniHex();
            long timestamp = IdGenerator.extractTimestampMini(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testIdMiniAsciiExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            String id = IdGenerator.getInstance().generateIdMiniAscii();
            long timestamp = IdGenerator.extractTimestampMiniAscii(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testId48ExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            long id = IdGenerator.getInstance().generateId48();
            long timestamp = IdGenerator.extractTimestamp48(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testId48HexExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            String id = IdGenerator.getInstance().generateId48Hex();
            long timestamp = IdGenerator.extractTimestamp48(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testId48AsciiExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            String id = IdGenerator.getInstance().generateId48Ascii();
            long timestamp = IdGenerator.extractTimestamp48Ascii(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testId64ExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            long id = IdGenerator.getInstance().generateId64();
            long timestamp = IdGenerator.extractTimestamp64(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testId64HexExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            String id = IdGenerator.getInstance().generateId64Hex();
            long timestamp = IdGenerator.extractTimestamp64(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testId64AsciiExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            String id = IdGenerator.getInstance().generateId64Ascii();
            long timestamp = IdGenerator.extractTimestamp64Ascii(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testId128ExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            BigInteger id = IdGenerator.getInstance().generateId128();
            long timestamp = IdGenerator.extractTimestamp128(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testId128HexExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            String id = IdGenerator.getInstance().generateId128Hex();
            long timestamp = IdGenerator.extractTimestamp128(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }

    @org.junit.Test
    public void testId128AsciiExtractTimestamp() throws Exception {
        Random rand = new Random(System.currentTimeMillis());
        Date now = new Date();
        for (int i = 0; i < 10; i++) {
            String id = IdGenerator.getInstance().generateId128Ascii();
            long timestamp = IdGenerator.extractTimestamp128Ascii(id);
            Assert.assertTrue(Math.abs(timestamp - now.getTime()) <= 360000);
            Thread.sleep(rand.nextInt(1024));
        }
    }
}
