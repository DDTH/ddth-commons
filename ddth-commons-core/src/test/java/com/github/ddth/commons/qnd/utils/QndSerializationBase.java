package com.github.ddth.commons.qnd.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class QndSerializationBase {

    public static class BaseClass {
        public int version = 0;
        public String name = "base";

        /**
         * {@inheritDoc}
         */
        public String toString() {
            ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
            tsb.append("version", version);
            tsb.append("name", name);
            return tsb.toString();
        }
    }

    public static class AClass extends BaseClass {
        public String pname = "A";

        /**
         * {@inheritDoc}
         */
        public String toString() {
            ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
            tsb.appendSuper(super.toString());
            tsb.append("pname", pname);
            return tsb.toString();
        }
    }

    public static class BClass {
        public BaseClass obj;

        /**
         * {@inheritDoc}
         */
        public String toString() {
            ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
            tsb.append("obj", obj);
            return tsb.toString();
        }
    }

    public static class LockClass {
        protected Map<String, Object> initAttributes(Map<String, Object> initData) {
            return initData != null ? new ConcurrentHashMap<String, Object>(initData)
                    : new ConcurrentHashMap<String, Object>();
        }

        private Map<String, Object> attributes = initAttributes(null);
        private boolean dirty = false;
        private Lock lock = new ReentrantLock();

        /**
         * {@inheritDoc}
         */
        public String toString() {
            ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
            tsb.append("attributes", attributes).append("dirty", dirty).append("lock", lock);
            return tsb.toString();
        }
    }

    public static class LockClass2 {
        protected Map<String, Object> initAttributes(Map<String, Object> initData) {
            return initData != null ? new ConcurrentHashMap<String, Object>(initData)
                    : new ConcurrentHashMap<String, Object>();
        }

        private Map<String, Object> attributes;
        private boolean dirty;
        private Lock lock;

        public LockClass2() {
            attributes = initAttributes(null);
            dirty = false;
            lock = new ReentrantLock();
        }

        /**
         * {@inheritDoc}
         */
        public String toString() {
            ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
            tsb.append("attributes", attributes).append("dirty", dirty).append("lock", lock);
            return tsb.toString();
        }
    }
}
