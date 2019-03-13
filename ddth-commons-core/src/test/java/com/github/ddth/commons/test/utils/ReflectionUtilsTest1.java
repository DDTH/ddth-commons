package com.github.ddth.commons.test.utils;

import org.junit.After;
import org.junit.Assert;
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

    /*--------------------------------------------------*/
    private static interface IA {
        @SuppressWarnings("unused")
        default void methodA() {
        }
    }

    private static interface IB extends IA {
        @SuppressWarnings("unused")
        default void methodB() {
        }
    }

    private static interface IC extends IB {
        @SuppressWarnings("unused")
        default void methodC() {
        }
    }

    private static interface ID {
        @SuppressWarnings("unused")
        default void methodD() {
        }
    }

    private static class A implements IA {
        @SuppressWarnings("unused")
        public void methodA(int param) {
        }
    }

    private static class B extends A implements IB {
        @SuppressWarnings("unused")
        public void methodB(int param) {
        }
    }

    private static class C extends A implements IC {
        @SuppressWarnings("unused")
        public void methodC(int param) {
        }
    }

    private static class CC extends B implements IC {
        @SuppressWarnings("unused")
        public void methodC(int param) {
        }
    }

    private static class D implements ID, IC {
        @SuppressWarnings("unused")
        public void methodD(int param) {
        }
    }

    @org.junit.Test
    public void testIBhasIA() {
        Assert.assertTrue(ReflectionUtils.hasInterface(IB.class, IA.class));
        Assert.assertTrue(ReflectionUtils.hasSuperClass(IB.class, IA.class));
    }

    @org.junit.Test
    public void testIChasIBandIA() {
        Assert.assertTrue(ReflectionUtils.hasInterface(IC.class, IB.class));
        Assert.assertTrue(ReflectionUtils.hasSuperClass(IC.class, IB.class));

        Assert.assertTrue(ReflectionUtils.hasInterface(IC.class, IA.class));
        Assert.assertTrue(ReflectionUtils.hasSuperClass(IC.class, IA.class));
    }

    @org.junit.Test
    public void testAhasIA() {
        Assert.assertTrue(ReflectionUtils.hasInterface(A.class, IA.class));
        Assert.assertFalse(ReflectionUtils.hasSuperClass(A.class, IA.class));
    }

    @org.junit.Test
    public void testBhasAandIBandIA() {
        Assert.assertTrue(ReflectionUtils.hasInterface(B.class, IA.class));
        Assert.assertTrue(ReflectionUtils.hasInterface(B.class, IB.class));
        Assert.assertTrue(ReflectionUtils.hasSuperClass(B.class, A.class));
        Assert.assertFalse(ReflectionUtils.hasSuperClass(B.class, IA.class));
        Assert.assertFalse(ReflectionUtils.hasSuperClass(B.class, IB.class));
    }

    @org.junit.Test
    public void testChasAandICandIBandIA() {
        Assert.assertTrue(ReflectionUtils.hasInterface(C.class, IA.class));
        Assert.assertTrue(ReflectionUtils.hasInterface(C.class, IB.class));
        Assert.assertTrue(ReflectionUtils.hasInterface(C.class, IC.class));
        Assert.assertTrue(ReflectionUtils.hasSuperClass(C.class, A.class));
        Assert.assertFalse(ReflectionUtils.hasSuperClass(C.class, B.class));
        Assert.assertFalse(ReflectionUtils.hasSuperClass(C.class, IA.class));
        Assert.assertFalse(ReflectionUtils.hasSuperClass(C.class, IB.class));
        Assert.assertFalse(ReflectionUtils.hasSuperClass(C.class, IC.class));
    }

    @org.junit.Test
    public void testCChasBhasAandICandIBandIA() {
        Assert.assertTrue(ReflectionUtils.hasInterface(CC.class, IA.class));
        Assert.assertTrue(ReflectionUtils.hasInterface(CC.class, IB.class));
        Assert.assertTrue(ReflectionUtils.hasInterface(CC.class, IC.class));
        Assert.assertTrue(ReflectionUtils.hasSuperClass(CC.class, A.class));
        Assert.assertTrue(ReflectionUtils.hasSuperClass(CC.class, B.class));
        Assert.assertFalse(ReflectionUtils.hasSuperClass(CC.class, IA.class));
        Assert.assertFalse(ReflectionUtils.hasSuperClass(CC.class, IB.class));
        Assert.assertFalse(ReflectionUtils.hasSuperClass(CC.class, IC.class));
    }

    @org.junit.Test
    public void testGetMethod() {
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", IA.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", A.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", A.class, int.class));

        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", IB.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", B.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", B.class, int.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodB", IB.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodB", B.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", B.class, int.class));

        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", IC.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", C.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", C.class, int.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodB", IC.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodB", C.class));
        Assert.assertNull(ReflectionUtils.getMethod("methodB", C.class, int.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodC", IC.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodC", C.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodC", C.class, int.class));

        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", CC.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", CC.class, int.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodB", CC.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodB", CC.class, int.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodC", CC.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodC", CC.class, int.class));

        Assert.assertNull(ReflectionUtils.getMethod("methodA", ID.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", D.class));
        Assert.assertNull(ReflectionUtils.getMethod("methodA", D.class, int.class));
        Assert.assertNull(ReflectionUtils.getMethod("methodB", ID.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodB", D.class));
        Assert.assertNull(ReflectionUtils.getMethod("methodB", D.class, int.class));
        Assert.assertNull(ReflectionUtils.getMethod("methodC", ID.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodC", D.class));
        Assert.assertNull(ReflectionUtils.getMethod("methodC", D.class, int.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodD", ID.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodD", D.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodD", D.class, int.class));
    }
}
