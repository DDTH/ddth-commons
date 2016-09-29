package com.github.ddth.commons.utils;

/**
 * Reflection-related utility class
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.5.0
 */
public class ReflectionUtils {

    /**
     * Tells if a class is a sub-class of a super-class.
     * 
     * Note:
     * <ul>
     * <li>Sub-class against super-class: this method returns {@code true}.</li>
     * <li>Sub-interface against super-interface: this method returns
     * {@code true}.</li>
     * <li>Class against interface: this method returns {@code false}.</li>
     * </ul>
     * 
     * @param clazz
     *            class to check
     * @param superClazz
     *            the super-class to check
     * @return {@code true} if {@code superClazz} is indeed the super-class of
     *         {@code clazz}
     */
    public static boolean hasSuperClass(Class<?> clazz, Class<?> superClazz) {
        if (clazz == null || superClazz == null || clazz == superClazz) {
            return false;
        }

        if (clazz.isInterface()) {
            return superClazz.isAssignableFrom(clazz);
        }

        Class<?> parent = clazz.getSuperclass();
        while (parent != null) {
            if (parent == superClazz) {
                return true;
            }
            parent = parent.getSuperclass();
        }
        return false;
    }

    /**
     * Tells if a class (or one of its super-classes) implements an interface;
     * or an interface is a sub-interface of a super-interface.
     * 
     * Note:
     * <ul>
     * <li>Sub-interface against super-interface: this method returns
     * {@code true}.</li>
     * <li>Class against interface: this method returns {@code true}.</li>
     * <li>Class against super-interface: this method returns {@code true}.</li>
     * </ul>
     * 
     * @param clazz
     * @param iface
     * @return
     */
    public static boolean hasInterface(Class<?> clazz, Class<?> iface) {
        if (clazz == null || iface == null || clazz == iface) {
            return false;
        }

        return iface.isInterface() && iface.isAssignableFrom(clazz);
    }

    public static void main(String[] args) {
        System.out.println(hasSuperClass(String.class, Object.class));
        System.out.println(hasSuperClass(String.class, String.class));
        System.out.println(hasSuperClass(String.class, Integer.class));
        System.out.println(hasSuperClass(Integer.class, Number.class));
    }
}
