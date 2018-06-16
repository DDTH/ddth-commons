package com.github.ddth.commons.test.utils;

import org.junit.After;
import org.junit.Before;

import com.github.ddth.commons.utils.ReflectionUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test {@link ReflectionUtils#hasInterface(Class, Class)} and
 * {@link ReflectionUtils#hasSuperClass(Class, Class)}.
 */
public class ReflectionUtilsTest1 extends TestCase {

    public ReflectionUtilsTest1(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(ReflectionUtilsTest1.class);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @org.junit.Test
    public void testHasSuperClassSomeClass() {
        assertFalse(ReflectionUtils.hasSuperClass(SomeClass.class, SuperClass.class));
    }

    @org.junit.Test
    public void testHasSuperClassSubClassSuperClass() {
        assertTrue(ReflectionUtils.hasSuperClass(SubClass.class, SuperClass.class));
    }

    @org.junit.Test
    public void testHasSuperClassSubClassSubClass() {
        assertFalse(ReflectionUtils.hasSuperClass(SubClass.class, SubClass.class));
    }

    @org.junit.Test
    public void testHasSuperClassSomeInterface() {
        assertFalse(ReflectionUtils.hasSuperClass(SomeInterface.class, SuperInterface.class));
    }

    @org.junit.Test
    public void testHasSuperClassSubInterfaceSuperInterface() {
        assertTrue(ReflectionUtils.hasSuperClass(SubInterface.class, SuperInterface.class));
    }

    @org.junit.Test
    public void testHasSuperClassClassInterface() {
        assertFalse(ReflectionUtils.hasSuperClass(SubClass.class, SuperInterface.class));
    }

    @org.junit.Test
    public void testHasInterfaceClassSomeClass() {
        assertTrue(ReflectionUtils.hasInterface(SomeClass.class, SomeInterface.class));
        assertFalse(ReflectionUtils.hasInterface(SomeClass.class, SuperInterface.class));
    }

    @org.junit.Test
    public void testHasInterfaceSubClassSubInterface() {
        assertTrue(ReflectionUtils.hasInterface(SubClass.class, SubInterface.class));
    }

    @org.junit.Test
    public void testHasInterfaceSubClassSuperInterface() {
        assertTrue(ReflectionUtils.hasInterface(SubClass.class, SuperInterface.class));
    }

    @org.junit.Test
    public void testHasInterfaceSubInterfaceSuperInterface() {
        assertTrue(ReflectionUtils.hasInterface(SubInterface.class, SuperInterface.class));
    }

    @org.junit.Test
    public void testHasInterfaceSubInterfaceSubInterface() {
        assertFalse(ReflectionUtils.hasInterface(SubInterface.class, SubInterface.class));
    }

    private static interface SuperInterface {
    }

    private static interface SubInterface extends SuperInterface {
    }

    private static interface SomeInterface {
    }

    private static class SuperClass implements SuperInterface {
    }

    private static class SubClass extends SuperClass implements SubInterface {
    }

    private static class SomeClass implements SomeInterface {
    }

}
