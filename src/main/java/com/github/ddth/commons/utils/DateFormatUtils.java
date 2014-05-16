package com.github.ddth.commons.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * Date/Time format utility class.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.2.2
 */
public class DateFormatUtils {
    private final static class DateFormatFactory extends BasePooledObjectFactory<DateFormat> {
        private String format;

        public DateFormatFactory(String format) {
            this.format = format;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public DateFormat create() throws Exception {
            return new SimpleDateFormat(format);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public PooledObject<DateFormat> wrap(DateFormat df) {
            return new DefaultPooledObject<DateFormat>(df);
        }
    }

    private final static Cache<String, ObjectPool<DateFormat>> cachedDateFormat = CacheBuilder
            .newBuilder().expireAfterAccess(3600, TimeUnit.SECONDS).build();

    /**
     * Converts a {@link Date} to string, based on the specified {@code format}.
     * 
     * @param date
     * @param format
     * @return
     * @see {@link SimpleDateFormat}
     */
    public static String toString(final Date date, final String format) {
        try {
            ObjectPool<DateFormat> pool = cachedDateFormat.get(format,
                    new Callable<ObjectPool<DateFormat>>() {
                        @Override
                        public ObjectPool<DateFormat> call() throws Exception {
                            return new GenericObjectPool<DateFormat>(new DateFormatFactory(format));
                        }
                    });
            try {
                DateFormat df = pool.borrowObject();
                try {
                    return df.format(date);
                } finally {
                    pool.returnObject(df);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Parses a string to {@link Date}, based on the specified {@code format}.
     * 
     * @param source
     * @param format
     * @return
     */
    public static Date fromString(final String source, final String format) {
        try {
            ObjectPool<DateFormat> pool = cachedDateFormat.get(format,
                    new Callable<ObjectPool<DateFormat>>() {
                        @Override
                        public ObjectPool<DateFormat> call() throws Exception {
                            return new GenericObjectPool<DateFormat>(new DateFormatFactory(format));
                        }
                    });
            try {
                DateFormat df = pool.borrowObject();
                try {
                    return df.parse(source);
                } finally {
                    pool.returnObject(df);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
