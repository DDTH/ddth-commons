package com.github.ddth.commons.serialization;

import com.github.ddth.commons.utils.SerializationUtils;

/**
 * This implementation of {@link ISerDeser} use Jboss-serialization library for
 * serializing/deserializing.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.5.0
 */
public class JbossSerDeser implements ISerDeser {

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] toBytes(Object obj) throws SerializationException {
        return toBytes(obj, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] toBytes(Object obj, ClassLoader classLoader) throws SerializationException {
        return SerializationUtils.toByteArrayJboss(obj, classLoader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T fromBytes(byte[] data, Class<T> clazz) throws DeserializationException {
        return fromBytes(data, clazz, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T fromBytes(byte[] data, Class<T> clazz, ClassLoader classLoader)
            throws DeserializationException {
        return SerializationUtils.fromByteArrayJboss(data, clazz, classLoader);
    }

}
