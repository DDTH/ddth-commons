package com.github.ddth.commons.test.serialization;

import com.github.ddth.commons.serialization.KryoSerDeser;

import junit.framework.Test;
import junit.framework.TestSuite;

/*
 * mvn test -DskipTests=false -Dtest=com.github.ddth.commons.test.serialization.KryoSerDeserTest
 */
public class KryoSerDeserTest extends BaseSerDeserTest {

    public KryoSerDeserTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(KryoSerDeserTest.class);
    }

    @org.junit.Test
    public void testKryo() {
        doTest(new KryoSerDeser());
    }
}
