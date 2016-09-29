package com.github.ddth.commons.qnd.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.esotericsoftware.kryo.KryoException;
import com.github.ddth.commons.utils.SerializationUtils;

public class QndSerializationKryo {

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
            byte[] data = SerializationUtils.toByteArrayKryo(classBase);
            try {
                System.out.println(SerializationUtils.fromByteArrayKryo(data, BaseClass.class));
            } catch (KryoException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(SerializationUtils.fromByteArrayKryo(data, AClass.class));
            } catch (KryoException e) {
                e.printStackTrace();
            }
        }

        {
            BaseClass classA = new AClass();
            byte[] data = SerializationUtils.toByteArrayKryo(classA);
            try {
                System.out.println(SerializationUtils.fromByteArrayKryo(data, BaseClass.class));
            } catch (KryoException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(SerializationUtils.fromByteArrayKryo(data, AClass.class));
            } catch (KryoException e) {
                e.printStackTrace();
            }
        }

        {
            BClass classB = new BClass();
            classB.obj = new BaseClass();
            byte[] data = SerializationUtils.toByteArrayKryo(classB);
            try {
                System.out.println(SerializationUtils.fromByteArrayKryo(data, BClass.class));
            } catch (KryoException e) {
                e.printStackTrace();
            }
        }

        {
            BClass classB = new BClass();
            classB.obj = new AClass();
            byte[] data = SerializationUtils.toByteArrayKryo(classB);
            try {
                System.out.println(SerializationUtils.fromByteArrayKryo(data, BClass.class));
            } catch (KryoException e) {
                e.printStackTrace();
            }
        }
    }
}
