package com.github.ddth.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.jboss.serial.io.JBossObjectInputStream;
import org.jboss.serial.io.JBossObjectOutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Serialization helper class.
 * 
 * <ul>
 * <li>JSON serialization: use {@code com.fasterxml.jackson} library.</li>
 * <li>Binary serialization: use {@code jboss-serialization} library.</li>
 * </ul>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.2.0
 */
public class SerializationUtils {

    private final static ObjectPool<ObjectMapper> poolMapper = new GenericObjectPool<ObjectMapper>(
            new BasePooledObjectFactory<ObjectMapper>() {
                @Override
                public ObjectMapper create() throws Exception {
                    return new ObjectMapper();
                }

                @Override
                public PooledObject<ObjectMapper> wrap(ObjectMapper objMapper) {
                    return new DefaultPooledObject<ObjectMapper>(objMapper);
                }
            });

    /**
     * Serializes an object to byte array.
     * 
     * @param obj
     * @return
     */
    public static byte[] toByteArray(Object obj) {
        return toByteArray(obj, null);
    }

    /**
     * Serializes an object to byte array, with a custom class loader.
     * 
     * @param obj
     * @param classLoader
     * @return
     */
    public static byte[] toByteArray(Object obj, ClassLoader classLoader) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            JBossObjectOutputStream oos = new JBossObjectOutputStream(baos);
            ClassLoader oldClassLoader = classLoader != null ? Thread.currentThread()
                    .getContextClassLoader() : null;
            if (classLoader != null) {
                Thread.currentThread().setContextClassLoader(classLoader);
            }
            try {
                oos.writeObject(obj);
                oos.flush();
                oos.close();
                return baos.toByteArray();
            } finally {
                if (oldClassLoader != null) {
                    Thread.currentThread().setContextClassLoader(oldClassLoader);
                }
            }
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Deserializes a byte array.
     * 
     * @param data
     * @return
     */
    public static Object fromByteArray(byte[] data) {
        return fromByteArray(data, Object.class, null);
    }

    /**
     * Deserializes a byte array, with custom class loader.
     * 
     * @param data
     * @param classLoader
     * @return
     */
    public static Object fromByteArray(byte[] data, ClassLoader classLoader) {
        return fromByteArray(data, Object.class, classLoader);
    }

    /**
     * Deserializes a byte array.
     * 
     * @param data
     * @param clazz
     * @return
     */
    public static <T> T fromByteArray(byte[] data, Class<T> clazz) {
        return fromByteArray(data, clazz, null);
    }

    /**
     * Deserializes a byte array, with custom class loader.
     * 
     * @param data
     * @param clazz
     * @param classLoader
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromByteArray(byte[] data, Class<T> clazz, ClassLoader classLoader) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            JBossObjectInputStream ois;
            if (classLoader != null) {
                ois = new JBossObjectInputStream(bais, classLoader);
            } else {
                ois = new JBossObjectInputStream(bais);
            }
            Object obj = ois.readObject();
            ois.close();
            if (obj != null && clazz.isAssignableFrom(obj.getClass())) {
                return (T) obj;
            } else {
                return null;
            }
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Serializes an object to JSON string.
     * 
     * @param obj
     * @return
     */
    public static String toJsonString(Object obj) {
        return toJsonString(obj, null);
    }

    /**
     * Serializes an object to JSON string, with a custom class loader.
     * 
     * @param obj
     * @param classLoader
     * @return
     */
    public static String toJsonString(Object obj, ClassLoader classLoader) {
        try {
            ObjectMapper mapper = poolMapper.borrowObject();
            ClassLoader oldClassLoader = classLoader != null ? Thread.currentThread()
                    .getContextClassLoader() : null;
            if (classLoader != null) {
                Thread.currentThread().setContextClassLoader(classLoader);
            }
            try {
                try {
                    return mapper.writeValueAsString(obj);
                    // return obj != null ? mapper.writeValueAsString(obj) :
                    // null;
                } finally {
                    poolMapper.returnObject(mapper);
                }
            } finally {
                if (oldClassLoader != null) {
                    Thread.currentThread().setContextClassLoader(oldClassLoader);
                }
            }
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Deserializes a JSON string.
     * 
     * @param jsonString
     * @return
     */
    public static Object fromJsonString(String jsonString) {
        return fromJsonString(jsonString, Object.class, null);
    }

    /**
     * Deserializes a JSON string, with custom class loader.
     * 
     * @param jsonString
     * @param classLoader
     * @return
     */
    public static Object fromJsonString(String jsonString, ClassLoader classLoader) {
        return fromJsonString(jsonString, Object.class, classLoader);
    }

    /**
     * Deserializes a JSON string.
     * 
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T fromJsonString(String jsonString, Class<T> clazz) {
        return fromJsonString(jsonString, clazz, null);
    }

    /**
     * Deserializes a JSON string, with custom class loader.
     * 
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T fromJsonString(String jsonString, Class<T> clazz, ClassLoader classLoader) {
        try {
            ObjectMapper mapper = poolMapper.borrowObject();
            ClassLoader oldClassLoader = classLoader != null ? Thread.currentThread()
                    .getContextClassLoader() : null;
            if (classLoader != null) {
                Thread.currentThread().setContextClassLoader(classLoader);
            }
            try {
                try {
                    return jsonString != null ? mapper.readValue(jsonString, clazz) : null;
                } finally {
                    poolMapper.returnObject(mapper);
                }
            } finally {
                if (oldClassLoader != null) {
                    Thread.currentThread().setContextClassLoader(oldClassLoader);
                }
            }
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
            }
        }
    }
}
