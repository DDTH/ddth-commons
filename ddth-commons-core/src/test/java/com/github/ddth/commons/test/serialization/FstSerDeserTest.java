package com.github.ddth.commons.test.serialization;

import com.github.ddth.commons.serialization.FstSerDeser;

import junit.framework.Test;
import junit.framework.TestSuite;

/*
 * mvn test -DskipTests=false -Dtest=com.github.ddth.commons.test.serialization.FstSerDeserTest
 */
public class FstSerDeserTest extends BaseSerDeserTest {

    public FstSerDeserTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(FstSerDeserTest.class);
    }

    @org.junit.Test
    public void testFst() {
        doTest(new FstSerDeser());
    }
}
