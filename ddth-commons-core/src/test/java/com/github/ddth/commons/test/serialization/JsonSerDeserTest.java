package com.github.ddth.commons.test.serialization;

import com.github.ddth.commons.serialization.JsonSerDeser;

import junit.framework.Test;
import junit.framework.TestSuite;

/*
 * mvn test -DskipTests=false -Dtest=com.github.ddth.commons.test.serialization.JsonSerDeserTest
 */
public class JsonSerDeserTest extends BaseSerDeserTest {

    public JsonSerDeserTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(JsonSerDeserTest.class);
    }

    @org.junit.Test
    public void testJson() {
        doTest(new JsonSerDeser());
    }
}
