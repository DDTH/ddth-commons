package com.github.ddth.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.nustaq.serialization.FSTConfiguration;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoCallback;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.github.ddth.commons.serialization.DeserializationException;
import com.github.ddth.commons.serialization.ISerializationSupport;
import com.github.ddth.commons.serialization.SerializationException;

/**
 * Serialization helper class.
 * 
 * <ul>
 * <li>JSON serialization: use {@code com.fasterxml.jackson} library.</li>
 * <li>Binary serialization: 3 choices of API
 * <ul>
 * <li>{@code jboss-serialization} library (deprecated since v0.6.0!), or</li>
 * <li>{@code Kryo} library or</li>
 * <li>{@code FST} library</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.2.0
 */
public class SerializationUtils {
    /*----------------------------------------------------------------------*/
    /**
     * Serialize an object to byte array.
     * 
     * <p>
     * If the target object implements {@link ISerializationSupport}, this
     * method calls its {@link ISerializationSupport#toBytes()} method;
     * otherwise FST library is used to serialize the object.
     * </p>
     * 
     * @param obj
     * @return
     */
    public static byte[] toByteArray(Object obj) {
        return toByteArray(obj, null);
    }

    /**
     * Serialize an object to byte array, with a custom class loader.
     * 
     * <p>
     * If the target object implements {@link ISerializationSupport}, this
     * method calls its {@link ISerializationSupport#toBytes()} method;
     * otherwise FST library is used to serialize the object.
     * </p>
     * 
     * @param obj
     * @param classLoader
     * @return
     */
    public static byte[] toByteArray(Object obj, ClassLoader classLoader) {
        if (obj instanceof ISerializationSupport) {
            ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader != null) {
                Thread.currentThread().setContextClassLoader(classLoader);
            }
            try {
                return ((ISerializationSupport) obj).toBytes();
            } finally {
                Thread.currentThread().setContextClassLoader(oldClassLoader);
            }
        } else {
            return toByteArrayFst(obj, classLoader);
        }
    }

    /**
     * Deserialize a byte array back to an object.
     * 
     * <p>
     * If the target class implements {@link ISerializationSupport}, this method
     * calls its {@link ISerializationSupport#toBytes()} method; otherwise FST
     * library is used to serialize the object.
     * </p>
     * 
     * @param data
     * @param clazz
     * @return
     */
    public static <T> T fromByteArray(byte[] data, Class<T> clazz) {
        return fromByteArray(data, clazz, null);
    }

    /**
     * Deserialize a byte array back to an object, with custom class loader.
     * 
     * <p>
     * If the target class implements {@link ISerializationSupport}, this method
     * calls its {@link ISerializationSupport#toBytes()} method; otherwise FST
     * library is used to serialize the object.
     * </p>
     * 
     * @param data
     * @param clazz
     * @param classLoader
     * @return
     */
    public static <T> T fromByteArray(byte[] data, Class<T> clazz, ClassLoader classLoader) {
        if (data == null) {
            return null;
        }
        if (ReflectionUtils.hasInterface(clazz, ISerializationSupport.class)) {
            ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader != null) {
                Thread.currentThread().setContextClassLoader(classLoader);
            }
            try {
                Constructor<T> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                T obj = constructor.newInstance();
                ((ISerializationSupport) obj).fromBytes(data);
                return obj;
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException
                    | SecurityException | IllegalArgumentException | InvocationTargetException e) {
                throw new DeserializationException(e);
            } finally {
                Thread.currentThread().setContextClassLoader(oldClassLoader);
            }
        }
        return SerializationUtils.fromByteArrayFst(data, clazz, classLoader);
    }

    /*----------------------------------------------------------------------*/
    private static KryoPool kryoPool;
    static {
        KryoFactory factory = new KryoFactory() {
            public Kryo create() {
                Kryo kryo = new Kryo();
                return kryo;
            }
        };
        Queue<Kryo> queue = new LinkedBlockingQueue<Kryo>(100);
        kryoPool = new KryoPool.Builder(factory).queue(queue).softReferences().build();
    }

    /**
     * Serialize an object to byte array.
     * 
     * <p>
     * This method uses Kryo lib.
     * </p>
     * 
     * @param obj
     * @return
     */
    public static byte[] toByteArrayKryo(Object obj) {
        return toByteArrayKryo(obj, null);
    }

    /**
     * Serialize an object to byte array, with a custom class loader.
     * 
     * <p>
     * This method uses Kryo lib.
     * </p>
     * 
     * @param obj
     * @param classLoader
     * @return
     */
    public static byte[] toByteArrayKryo(final Object obj, final ClassLoader classLoader) {
        if (obj == null) {
            return null;
        }
        return kryoPool.run(new KryoCallback<byte[]>() {
            @Override
            public byte[] execute(Kryo kryo) {
                ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
                if (classLoader != null) {
                    Thread.currentThread().setContextClassLoader(classLoader);
                }
                try {
                    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                        try (Output output = new Output(baos)) {
                            kryo.setClassLoader(classLoader != null ? classLoader : oldClassLoader);
                            // kryo.writeObject(output, obj);
                            kryo.writeClassAndObject(output, obj);
                            output.flush();
                            return baos.toByteArray();
                        }
                    } catch (Exception e) {
                        throw e instanceof SerializationException ? (SerializationException) e
                                : new SerializationException(e);
                    }
                } finally {
                    Thread.currentThread().setContextClassLoader(oldClassLoader);
                }
            }
        });
    }

    /**
     * Deserialize a byte array back to an object.
     * 
     * <p>
     * This method uses Kryo lib.
     * </p>
     * 
     * @param data
     * @return
     */
    public static Object fromByteArrayKryo(byte[] data) {
        return fromByteArrayKryo(data, Object.class, null);
    }

    /**
     * Deserialize a byte array back to an object, with custom class loader.
     * 
     * <p>
     * This method uses Kryo lib.
     * </p>
     * 
     * @param data
     * @param classLoader
     * @return
     */
    public static Object fromByteArrayKryo(byte[] data, ClassLoader classLoader) {
        return fromByteArrayKryo(data, Object.class, classLoader);
    }

    /**
     * Deserialize a byte array back to an object.
     * 
     * <p>
     * This method uses Kryo lib.
     * </p>
     * 
     * @param data
     * @param clazz
     * @return
     */
    public static <T> T fromByteArrayKryo(byte[] data, Class<T> clazz) {
        return fromByteArrayKryo(data, clazz, null);
    }

    /**
     * Deserialize a byte array back to an object, with custom class loader.
     * 
     * <p>
     * This method uses Kryo lib.
     * </p>
     * 
     * @param data
     * @param clazz
     * @param classLoader
     * @return
     */
    public static <T> T fromByteArrayKryo(byte[] data, final Class<T> clazz,
            final ClassLoader classLoader) {
        if (data == null) {
            return null;
        }
        return kryoPool.run(new KryoCallback<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T execute(Kryo kryo) {
                ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
                if (classLoader != null) {
                    Thread.currentThread().setContextClassLoader(classLoader);
                }
                try {
                    try (Input input = new Input(new ByteArrayInputStream(data))) {
                        kryo.setClassLoader(classLoader != null ? classLoader : oldClassLoader);
                        // return kryo.readObject(input, clazz);
                        Object result = kryo.readClassAndObject(input);
                        if (result != null && clazz.isAssignableFrom(result.getClass())) {
                            return (T) result;
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        throw e instanceof DeserializationException ? (DeserializationException) e
                                : new DeserializationException(e);
                    }
                } finally {
                    Thread.currentThread().setContextClassLoader(oldClassLoader);
                }
            }
        });
    }

    /*----------------------------------------------------------------------*/
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
    static {
        GenericObjectPool<ObjectMapper> pool = (GenericObjectPool<ObjectMapper>) poolMapper;
        pool.setMaxIdle(1);
        pool.setMaxTotal(100);
        pool.setMaxWaitMillis(5000);
        pool.setBlockWhenExhausted(true);
    }

    /**
     * Serialize an object to JSON string.
     * 
     * @param obj
     * @return
     */
    public static String toJsonString(Object obj) {
        return toJsonString(obj, null);
    }

    /**
     * Serialize an object to JSON string, with a custom class loader.
     * 
     * @param obj
     * @param classLoader
     * @return
     */
    public static String toJsonString(Object obj, ClassLoader classLoader) {
        if (obj == null) {
            return "null";
        }
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
        try {
            ObjectMapper mapper = poolMapper.borrowObject();
            if (mapper != null) {
                try {
                    return mapper.writeValueAsString(obj);
                } finally {
                    poolMapper.returnObject(mapper);
                }
            }
            throw new SerializationException("No ObjectMapper instance avaialble!");
        } catch (Exception e) {
            throw e instanceof SerializationException ? (SerializationException) e
                    : new SerializationException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * Serialize an object to {@link JsonNode}.
     * 
     * @param obj
     * @return
     * @since 0.6.2
     */
    public static JsonNode toJson(Object obj) {
        return toJson(obj, null);
    }

    /**
     * Serialize an object to {@link JsonNode}, with a custom class loader.
     * 
     * @param obj
     * @param classLoader
     * @return
     * @since 0.6.2
     */
    public static JsonNode toJson(Object obj, ClassLoader classLoader) {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
        try {
            if (obj == null) {
                return NullNode.instance;
            }
            ObjectMapper mapper = poolMapper.borrowObject();
            if (mapper != null) {
                try {
                    return mapper.valueToTree(obj);
                } finally {
                    poolMapper.returnObject(mapper);
                }
            }
            throw new SerializationException("No ObjectMapper instance avaialble!");
        } catch (Exception e) {
            throw e instanceof SerializationException ? (SerializationException) e
                    : new SerializationException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance.
     * 
     * @param source
     * @return
     * @since 0.6.2
     */
    public static JsonNode readJson(String source) {
        return readJson(source, null);
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance, with a custom class loader.
     * 
     * @param source
     * @param classLoader
     * @return
     * @since 0.6.2
     */
    public static JsonNode readJson(String source, ClassLoader classLoader) {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
        try {
            if (StringUtils.isBlank(source)) {
                return NullNode.instance;
            }
            ObjectMapper mapper = poolMapper.borrowObject();
            if (mapper != null) {
                try {
                    return mapper.readTree(source);
                } finally {
                    poolMapper.returnObject(mapper);
                }
            }
            throw new SerializationException("No ObjectMapper instance avaialble!");
        } catch (Exception e) {
            throw e instanceof SerializationException ? (SerializationException) e
                    : new SerializationException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance.
     * 
     * @param source
     * @return
     * @since 0.6.2
     */
    public static JsonNode readJson(byte[] source) {
        return readJson(source, null);
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance, with a custom class loader.
     * 
     * @param source
     * @param classLoader
     * @return
     * @since 0.6.2
     */
    public static JsonNode readJson(byte[] source, ClassLoader classLoader) {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
        try {
            if (source == null || source.length == 0) {
                return NullNode.instance;
            }
            ObjectMapper mapper = poolMapper.borrowObject();
            if (mapper != null) {
                try {
                    return mapper.readTree(source);
                } finally {
                    poolMapper.returnObject(mapper);
                }
            }
            throw new SerializationException("No ObjectMapper instance avaialble!");
        } catch (Exception e) {
            throw e instanceof SerializationException ? (SerializationException) e
                    : new SerializationException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance.
     * 
     * @param source
     * @return
     * @since 0.6.2
     */
    public static JsonNode readJson(InputStream source) {
        return readJson(source, null);
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance, with a custom class loader.
     * 
     * @param source
     * @param classLoader
     * @return
     * @since 0.6.2
     */
    public static JsonNode readJson(InputStream source, ClassLoader classLoader) {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
        try {
            if (source == null) {
                return NullNode.instance;
            }
            ObjectMapper mapper = poolMapper.borrowObject();
            if (mapper != null) {
                try {
                    return mapper.readTree(source);
                } finally {
                    poolMapper.returnObject(mapper);
                }
            }
            throw new SerializationException("No ObjectMapper instance avaialble!");
        } catch (Exception e) {
            throw e instanceof SerializationException ? (SerializationException) e
                    : new SerializationException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance.
     * 
     * @param source
     * @return
     * @since 0.6.2
     */
    public static JsonNode readJson(Reader source) {
        return readJson(source, null);
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance, with a custom class loader.
     * 
     * @param source
     * @param classLoader
     * @return
     * @since 0.6.2
     */
    public static JsonNode readJson(Reader source, ClassLoader classLoader) {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
        try {
            if (source == null) {
                return NullNode.instance;
            }
            ObjectMapper mapper = poolMapper.borrowObject();
            if (mapper != null) {
                try {
                    return mapper.readTree(source);
                } finally {
                    poolMapper.returnObject(mapper);
                }
            }
            throw new SerializationException("No ObjectMapper instance avaialble!");
        } catch (Exception e) {
            throw e instanceof SerializationException ? (SerializationException) e
                    : new SerializationException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * Deserialize a JSON string.
     * 
     * @param jsonString
     * @return
     */
    public static Object fromJsonString(String jsonString) {
        return fromJsonString(jsonString, Object.class, null);
    }

    /**
     * Deserialize a JSON string, with custom class loader.
     * 
     * @param jsonString
     * @param classLoader
     * @return
     */
    public static Object fromJsonString(String jsonString, ClassLoader classLoader) {
        return fromJsonString(jsonString, Object.class, classLoader);
    }

    /**
     * Deserialize a JSON string.
     * 
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T fromJsonString(String jsonString, Class<T> clazz) {
        return fromJsonString(jsonString, clazz, null);
    }

    /**
     * Deserialize a JSON string, with custom class loader.
     * 
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T fromJsonString(String jsonString, Class<T> clazz, ClassLoader classLoader) {
        if (jsonString == null) {
            return null;
        }
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
        try {
            ObjectMapper mapper = poolMapper.borrowObject();
            if (mapper != null) {
                try {
                    return mapper.readValue(jsonString, clazz);
                } finally {
                    poolMapper.returnObject(mapper);
                }
            }
            throw new DeserializationException("No ObjectMapper instance avaialble!");
        } catch (Exception e) {
            throw e instanceof DeserializationException ? (DeserializationException) e
                    : new DeserializationException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * Deserialize a {@link JsonNode}.
     * 
     * @param json
     * @return
     * @since 0.6.2
     */
    public static Object fromJson(JsonNode json) {
        return fromJson(json, Object.class, null);
    }

    /**
     * Deserialize a {@link JsonNode}, with custom class loader.
     * 
     * @param json
     * @param classLoader
     * @return
     * @since 0.6.2
     */
    public static Object fromJson(JsonNode json, ClassLoader classLoader) {
        return fromJson(json, Object.class, classLoader);
    }

    /**
     * Deserialize a {@link JsonNode}.
     * 
     * @param json
     * @param clazz
     * @return
     * @since 0.6.2
     */
    public static <T> T fromJson(JsonNode json, Class<T> clazz) {
        return fromJson(json, clazz, null);
    }

    /**
     * Deserialize a {@link JsonNode}, with custom class loader.
     * 
     * @param json
     * @param clazz
     * @return
     * @since 0.6.2
     */
    public static <T> T fromJson(JsonNode json, Class<T> clazz, ClassLoader classLoader) {
        if (json == null) {
            return null;
        }
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
        try {
            ObjectMapper mapper = poolMapper.borrowObject();
            if (mapper != null) {
                try {
                    return mapper.readValue(json.toString(), clazz);
                } finally {
                    poolMapper.returnObject(mapper);
                }
            }
            throw new DeserializationException("No ObjectMapper instance avaialble!");
        } catch (Exception e) {
            throw e instanceof DeserializationException ? (DeserializationException) e
                    : new DeserializationException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /*----------------------------------------------------------------------*/
    private static ThreadLocal<FSTConfiguration> fstConf = new ThreadLocal<FSTConfiguration>() {
        public FSTConfiguration initialValue() {
            FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
            conf.setForceSerializable(true);
            return conf;
        }
    };

    /**
     * Serialize an object to byte array.
     * 
     * <p>
     * This method uses FST lib.
     * </p>
     * 
     * @param obj
     * @return
     * @since 0.6.0
     */
    public static byte[] toByteArrayFst(Object obj) {
        return toByteArrayFst(obj, null);
    }

    /**
     * Serialize an object to byte array, with a custom class loader.
     * 
     * <p>
     * This method uses FST lib.
     * </p>
     * 
     * @param obj
     * @param classLoader
     * @return
     * @since 0.6.0
     */
    public static byte[] toByteArrayFst(final Object obj, final ClassLoader classLoader) {
        if (obj == null) {
            return null;
        }
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
        try {
            FSTConfiguration conf = fstConf.get();
            conf.setClassLoader(classLoader != null ? classLoader : oldClassLoader);
            return conf.asByteArray(obj);
        } catch (Exception e) {
            throw e instanceof SerializationException ? (SerializationException) e
                    : new SerializationException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    /**
     * Deserialize a byte array back to an object.
     * 
     * <p>
     * This method uses FST lib.
     * </p>
     * 
     * @param data
     * @return
     * @since 0.6.0
     */
    public static Object fromByteArrayFst(byte[] data) {
        return fromByteArrayFst(data, Object.class, null);
    }

    /**
     * Deserialize a byte array back to an object, with custom class loader.
     * 
     * <p>
     * This method uses FST lib.
     * </p>
     * 
     * @param data
     * @param classLoader
     * @return
     * @since 0.6.0
     */
    public static Object fromByteArrayFst(byte[] data, ClassLoader classLoader) {
        return fromByteArrayFst(data, Object.class, classLoader);
    }

    /**
     * Deserialize a byte array back to an object.
     * 
     * <p>
     * This method uses FST lib.
     * </p>
     * 
     * @param data
     * @param clazz
     * @return
     * @since 0.6.0
     */
    public static <T> T fromByteArrayFst(byte[] data, Class<T> clazz) {
        return fromByteArrayFst(data, clazz, null);
    }

    /**
     * Deserialize a byte array back to an object, with custom class loader.
     * 
     * <p>
     * This method uses FST lib.
     * </p>
     * 
     * @param data
     * @param clazz
     * @param classLoader
     * @return
     * @since 0.6.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromByteArrayFst(final byte[] data, final Class<T> clazz,
            final ClassLoader classLoader) {
        if (data == null) {
            return null;
        }
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            Thread.currentThread().setContextClassLoader(classLoader);
        }
        try {
            FSTConfiguration conf = fstConf.get();
            conf.setClassLoader(classLoader != null ? classLoader : oldClassLoader);
            Object result = conf.asObject(data);
            if (result != null && clazz.isAssignableFrom(result.getClass())) {
                return (T) result;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e instanceof DeserializationException ? (DeserializationException) e
                    : new DeserializationException(e);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }
}
