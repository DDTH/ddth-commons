package com.github.ddth.commons.test.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.github.ddth.commons.utils.ReflectionUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Test {@link ReflectionUtils#getConstructor(Class, Class...)} and
 * {@link ReflectionUtils#getMethod(String, Class, Class...)}.
 */
public class ReflectionUtilsTest2 extends TestCase {

    public ReflectionUtilsTest2(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(ReflectionUtilsTest2.class);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /*----------------------------------------------------------------------*/

    public static interface ParentInterface {
        void methodA(String param);
    }

    public static interface ChildInterface extends ParentInterface {
        void methodA(int param);

        void methodB(Integer param);
    }

    public static abstract class ParentClass implements ParentInterface {
        public ParentClass(int param) {
        }

        public void publicMethod(String param) {
        }

        protected void protectedMethod(String param) {
        }

        @SuppressWarnings("unused")
        private void privateMethod(String param) {
        }

        public abstract void abstractMethod(String param);
    }

    public static abstract class ChildClass extends ParentClass implements ChildInterface {
        public ChildClass(long param) {
            super((int) param);
        }

        @Override
        public void abstractMethod(String param) {
        }
    }

    /*----------------------------------------------------------------------*/

    public static class ClassWithPrivateConstructor {
        private ClassWithPrivateConstructor() {
        }

        private ClassWithPrivateConstructor(int param) {
        }

        private ClassWithPrivateConstructor(Double param) {
        }

        private ClassWithPrivateConstructor(Number param) {
        }

        private ClassWithPrivateConstructor(int[] param) {
        }

        private ClassWithPrivateConstructor(Double[] param) {
        }
    }

    @org.junit.Test
    public void testGetPrivateConstructor() {
        {
            Constructor<?> c = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class);
            Assert.assertNotNull(c);
        }
        {
            Constructor<?> c1 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    int.class);
            Constructor<?> c2 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    long.class);
            Constructor<?> c3 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    Integer.class);
            Constructor<?> c4 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    Object.class);
            Assert.assertNotNull(c1);
            Assert.assertNull(c2);
            Assert.assertNull(c3);
            Assert.assertNull(c4);
        }
        {
            Constructor<?> c1 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    Double.class);
            Constructor<?> c2 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    double.class);
            Constructor<?> c3 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    Float.class);
            Constructor<?> c4 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    float.class);
            Assert.assertNotNull(c1);
            Assert.assertNull(c2);
            Assert.assertNull(c3);
            Assert.assertNull(c4);
        }
        {
            Constructor<?> c1 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    int[].class);
            Constructor<?> c2 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    Integer[].class);
            Constructor<?> c3 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    Object[].class);
            Constructor<?> c4 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    long[].class);
            Assert.assertNotNull(c1);
            Assert.assertNull(c2);
            Assert.assertNull(c3);
            Assert.assertNull(c4);
        }
        {
            Constructor<?> c1 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    Double[].class);
            Constructor<?> c2 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    double[].class);
            Constructor<?> c3 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    Float[].class);
            Constructor<?> c4 = ReflectionUtils.getConstructor(ClassWithPrivateConstructor.class,
                    float[].class);
            Assert.assertNotNull(c1);
            Assert.assertNull(c2);
            Assert.assertNull(c3);
            Assert.assertNull(c4);
        }
    }

    public static class ClassWithProtectedConstructor {
        private ClassWithProtectedConstructor() {
        }

        private ClassWithProtectedConstructor(int param) {
        }

        private ClassWithProtectedConstructor(Double param) {
        }

        private ClassWithProtectedConstructor(Number param) {
        }

        private ClassWithProtectedConstructor(int[] param) {
        }

        private ClassWithProtectedConstructor(Double[] param) {
        }
    }

    @org.junit.Test
    public void testGetProtectedConstructor() {
        {
            Constructor<?> c = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class);
            Assert.assertNotNull(c);
        }
        {
            Constructor<?> c1 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    int.class);
            Constructor<?> c2 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    long.class);
            Constructor<?> c3 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    Integer.class);
            Constructor<?> c4 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    Object.class);
            Assert.assertNotNull(c1);
            Assert.assertNull(c2);
            Assert.assertNull(c3);
            Assert.assertNull(c4);
        }
        {
            Constructor<?> c1 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    Double.class);
            Constructor<?> c2 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    double.class);
            Constructor<?> c3 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    Float.class);
            Constructor<?> c4 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    float.class);
            Assert.assertNotNull(c1);
            Assert.assertNull(c2);
            Assert.assertNull(c3);
            Assert.assertNull(c4);
        }
        {
            Constructor<?> c1 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    int[].class);
            Constructor<?> c2 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    Integer[].class);
            Constructor<?> c3 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    Object[].class);
            Constructor<?> c4 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    long[].class);
            Assert.assertNotNull(c1);
            Assert.assertNull(c2);
            Assert.assertNull(c3);
            Assert.assertNull(c4);
        }
        {
            Constructor<?> c1 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    Double[].class);
            Constructor<?> c2 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    double[].class);
            Constructor<?> c3 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    Float[].class);
            Constructor<?> c4 = ReflectionUtils.getConstructor(ClassWithProtectedConstructor.class,
                    float[].class);
            Assert.assertNotNull(c1);
            Assert.assertNull(c2);
            Assert.assertNull(c3);
            Assert.assertNull(c4);
        }
    }

    public static class ClassWithPublicConstructor {
        private ClassWithPublicConstructor() {
        }

        private ClassWithPublicConstructor(int param) {
        }

        private ClassWithPublicConstructor(Double param) {
        }

        private ClassWithPublicConstructor(Number param) {
        }

        private ClassWithPublicConstructor(int[] param) {
        }

        private ClassWithPublicConstructor(Double[] param) {
        }
    }

    @org.junit.Test
    public void testGetPublicConstructor() {
        {
            Constructor<?> c = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class);
            Assert.assertNotNull(c);
        }
        {
            Constructor<?> c1 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    int.class);
            Constructor<?> c2 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    long.class);
            Constructor<?> c3 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    Integer.class);
            Constructor<?> c4 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    Object.class);
            Assert.assertNotNull(c1);
            Assert.assertNull(c2);
            Assert.assertNull(c3);
            Assert.assertNull(c4);
        }
        {
            Constructor<?> c1 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    Double.class);
            Constructor<?> c2 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    double.class);
            Constructor<?> c3 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    Float.class);
            Constructor<?> c4 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    float.class);
            Assert.assertNotNull(c1);
            Assert.assertNull(c2);
            Assert.assertNull(c3);
            Assert.assertNull(c4);
        }
        {
            Constructor<?> c1 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    int[].class);
            Constructor<?> c2 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    Integer[].class);
            Constructor<?> c3 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    Object[].class);
            Constructor<?> c4 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    long[].class);
            Assert.assertNotNull(c1);
            Assert.assertNull(c2);
            Assert.assertNull(c3);
            Assert.assertNull(c4);
        }
        {
            Constructor<?> c1 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    Double[].class);
            Constructor<?> c2 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    double[].class);
            Constructor<?> c3 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    Float[].class);
            Constructor<?> c4 = ReflectionUtils.getConstructor(ClassWithPublicConstructor.class,
                    float[].class);
            Assert.assertNotNull(c1);
            Assert.assertNull(c2);
            Assert.assertNull(c3);
            Assert.assertNull(c4);
        }
    }

    @org.junit.Test
    public void testInterfaceHasNoConstructor() {
        Assert.assertNull(ReflectionUtils.getConstructor(ParentInterface.class));
    }

    public void testGetConstructorParentClass() {
        Constructor<?> c1 = ReflectionUtils.getConstructor(ChildClass.class, long.class);
        Constructor<?> c2 = ReflectionUtils.getConstructor(ChildClass.class, int.class);
        Assert.assertNotNull(c1);
        Assert.assertNull(c2);
    }

    /*----------------------------------------------------------------------*/

    @org.junit.Test
    public void testGetMethodFromInterface() {
        Assert.assertNotNull(
                ReflectionUtils.getMethod("methodA", ParentInterface.class, String.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", ChildInterface.class, int.class));
        Assert.assertNotNull(
                ReflectionUtils.getMethod("methodB", ChildInterface.class, Integer.class));
        Assert.assertNotNull(
                ReflectionUtils.getMethod("methodA", ChildInterface.class, String.class));
        Assert.assertNull(
                ReflectionUtils.getMethod("methodA", ChildInterface.class, Integer.class));
    }

    @org.junit.Test
    public void testGetMethodFromChildInterface() {
        Assert.assertNotNull(
                ReflectionUtils.getMethod("methodA", ChildInterface.class, String.class));
    }

    @org.junit.Test
    public void testGetMethodFromAbstractClass() {
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", ParentClass.class, String.class));
        Assert.assertNotNull(
                ReflectionUtils.getMethod("publicMethod", ParentClass.class, String.class));
        Assert.assertNotNull(
                ReflectionUtils.getMethod("protectedMethod", ParentClass.class, String.class));
        Assert.assertNotNull(
                ReflectionUtils.getMethod("privateMethod", ParentClass.class, String.class));
        Assert.assertNotNull(
                ReflectionUtils.getMethod("abstractMethod", ParentClass.class, String.class));
    }

    @org.junit.Test
    public void testGetMethodFromChildClass() {
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", ChildClass.class, String.class));
        Assert.assertNotNull(
                ReflectionUtils.getMethod("publicMethod", ChildClass.class, String.class));
        Assert.assertNotNull(
                ReflectionUtils.getMethod("protectedMethod", ChildClass.class, String.class));
        Assert.assertNotNull(
                ReflectionUtils.getMethod("privateMethod", ChildClass.class, String.class));
        Assert.assertNotNull(
                ReflectionUtils.getMethod("abstractMethod", ChildClass.class, String.class));

        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", ChildClass.class, String.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodA", ChildClass.class, int.class));
        Assert.assertNotNull(ReflectionUtils.getMethod("methodB", ChildClass.class, Integer.class));
    }

    /*----------------------------------------------------------------------*/
    public static interface SomeParentInterface {
        int add(int x, int y);
    }

    public static interface SomeChildInterface extends SomeParentInterface {
        int subtract(int x, int y);
    }

    public static class SomeParentClass implements SomeParentInterface {
        @Override
        public int add(int x, int y) {
            return x + y;
        }
    }

    public static class SomeChildClass extends SomeParentClass implements SomeChildInterface {
        @Override
        public int subtract(int x, int y) {
            return x - y;
        }
    }

    @org.junit.Test
    public void testInvokeMethodFromChildClass() throws Exception {
        Method m = ReflectionUtils.getMethod("add", SomeChildClass.class, int.class, int.class);
        Assert.assertNotNull(m);
        Object result = m.invoke(new SomeChildClass(), 1, 2);
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof Integer);
        Assert.assertEquals(3, result);
    }
}
