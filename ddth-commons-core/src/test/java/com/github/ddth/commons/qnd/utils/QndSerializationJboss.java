package com.github.ddth.commons.qnd.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.github.ddth.commons.utils.SerializationUtils;

public class QndSerializationJboss {

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

    public static class BClass {
        public BaseClass obj;

        /**
         * {@inheritDoc}
         */
        public String toString() {
            ToStringBuilder tsb = new ToStringBuilder(this);
            tsb.append("obj", obj);
            return tsb.toString();
        }
    }

    public static void main(String[] args) {
        {
            BaseClass classBase = new BaseClass();
            byte[] data = SerializationUtils.toByteArrayJboss(classBase);
            System.out.println(SerializationUtils.fromByteArrayJboss(data, BaseClass.class));
            System.out.println(SerializationUtils.fromByteArrayJboss(data, AClass.class));
        }

        {
            BaseClass classA = new AClass();
            byte[] data = SerializationUtils.toByteArrayJboss(classA);
            System.out.println(SerializationUtils.fromByteArrayJboss(data, BaseClass.class));
            System.out.println(SerializationUtils.fromByteArrayJboss(data, AClass.class));
        }

        {
            BClass classB = new BClass();
            classB.obj = new BaseClass();
            byte[] data = SerializationUtils.toByteArrayJboss(classB);
            System.out.println(SerializationUtils.fromByteArrayJboss(data, BClass.class));
        }

        {
            BClass classB = new BClass();
            classB.obj = new AClass();
            byte[] data = SerializationUtils.toByteArrayJboss(classB);
            System.out.println(SerializationUtils.fromByteArrayJboss(data, BClass.class));
        }
    }
}
