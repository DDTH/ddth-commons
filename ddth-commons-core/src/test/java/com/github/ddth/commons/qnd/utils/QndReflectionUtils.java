package com.github.ddth.commons.qnd.utils;

import com.github.ddth.commons.utils.ReflectionUtils;

public class QndReflectionUtils {

    public static interface ParentInterface {
        void method1(String param);
    }

    public static interface ChildInterface extends ParentInterface {
        void method1(int param);

        void method2(int param);
    }

    public static abstract class ParentClass implements ChildInterface {
        public ParentClass(String param) {
        }

        public void method1(String param) {
        }

        protected void method2(String param) {
        }

        private void method3(long param) {
        }

        public abstract void method4(Object param);
    }

    public static abstract class ChildClass extends ParentClass {
        public ChildClass() {
            super("child");
        }

        public void method5(String param) {
        }

        protected abstract void method6();
    }

    public static void main(String[] args) {
        System.out.println("Parent, constructor with args   : "
                + ReflectionUtils.getConstructor(ParentClass.class, String.class));
        System.out.println("Parent, constructor without args: "
                + ReflectionUtils.getConstructor(ParentClass.class));
        System.out.println("Child, constructor with args    : "
                + ReflectionUtils.getConstructor(ChildClass.class, String.class));
        System.out.println("Child, constructor without args : "
                + ReflectionUtils.getConstructor(ChildClass.class));
        System.out.println("==============================");
        System.out.println("PInterface - method1(String): "
                + ReflectionUtils.getMethod("method1", ParentInterface.class, String.class));
        System.out.println("CInterface - method1(String): "
                + ReflectionUtils.getMethod("method1", ChildInterface.class, String.class));
        System.out.println("CInterface - method1(int)   : "
                + ReflectionUtils.getMethod("method1", ChildInterface.class, int.class));
        System.out.println("CInterface - method2(int)   : "
                + ReflectionUtils.getMethod("method2", ChildInterface.class, int.class));

        // System.out.println("==============================");
        // System.out.println("Parent, method1: "
        // + ReflectionUtils.getMethod("method1", ParentClass.class, String.class));
        // System.out.println("Parent, method2: "
        // + ReflectionUtils.getMethod("method2", ParentClass.class, String.class));
        // System.out.println("Parent, method2: "
        // + ReflectionUtils.getMethod("method3", ParentClass.class, long.class));
        // System.out.println("Parent, method2: "
        // + ReflectionUtils.getMethod("method4", ParentClass.class, Object.class));

        // System.out.println("Child, method1 : "
        // + ReflectionUtils.getMethod("method1", ChildClass.class, String.class));
        // System.out.println("Child, method2: "
        // + ReflectionUtils.getMethod("method2", ChildClass.class, String.class, int.class));
    }

}
