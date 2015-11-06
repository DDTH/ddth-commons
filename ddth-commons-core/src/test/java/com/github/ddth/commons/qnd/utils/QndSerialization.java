package com.github.ddth.commons.qnd.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.github.ddth.commons.utils.SerializationUtils;

public class QndSerialization {
    public static class BaseClass {
        public int version = 0;
        public String name = "base";

        /**
         * {@inheritDoc}
         */
        public String toString() {
            ToStringBuilder tsb = new ToStringBuilder(this);
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
            ToStringBuilder tsb = new ToStringBuilder(this);
            tsb.appendSuper(super.toString());
            tsb.append("pname", pname);
            return tsb.toString();
        }
    }

    public static void main(String[] args) {
        {
            BaseClass classBase = new BaseClass();
            byte[] data = SerializationUtils.toByteArray(classBase);
            System.out.println(SerializationUtils.fromByteArray(data, BaseClass.class));
            System.out.println(SerializationUtils.fromByteArray(data, AClass.class));
        }

        {
            BaseClass classA = new AClass();
            byte[] data = SerializationUtils.toByteArray(classA);
            System.out.println(SerializationUtils.fromByteArray(data, BaseClass.class));
            System.out.println(SerializationUtils.fromByteArray(data, AClass.class));
        }
    }
}
