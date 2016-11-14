package com.github.ddth.commons.qnd.utils;

import com.github.ddth.commons.utils.SerializationUtils;

public class QndSerializationJboss extends QndSerializationBase {

    public static void main(String[] args) {
        {
            BaseClass classBase = new BaseClass();
            System.out.println("Original class: " + classBase.getClass());
            byte[] data = SerializationUtils.toByteArrayJboss(classBase);
            System.out.println("SerializedSize: " + data.length);
            System.out.println("Bare deser: " + SerializationUtils.fromByteArrayJboss(data));
            System.out.println(
                    "Base deser: " + SerializationUtils.fromByteArrayJboss(data, BaseClass.class));
            System.out.println(
                    "SubC deser: " + SerializationUtils.fromByteArrayJboss(data, AClass.class));
        }
        System.out.println("==============================");
        {
            BaseClass classA = new AClass();
            System.out.println("Original class: " + classA.getClass());
            byte[] data = SerializationUtils.toByteArrayJboss(classA);
            System.out.println("SerializedSize: " + data.length);
            System.out.println("Bare deser: " + SerializationUtils.fromByteArrayJboss(data));
            System.out.println(
                    "Base deser: " + SerializationUtils.fromByteArrayJboss(data, BaseClass.class));
            System.out.println(
                    "SubC deser: " + SerializationUtils.fromByteArrayJboss(data, AClass.class));
        }
        System.out.println("==============================");
        {
            BClass classB = new BClass();
            classB.obj = new BaseClass();
            System.out.println("Original class: " + classB.getClass());
            byte[] data = SerializationUtils.toByteArrayJboss(classB);
            System.out.println("SerializedSize: " + data.length);
            System.out.println("Bare deser: " + SerializationUtils.fromByteArrayJboss(data));
            System.out.println(
                    "Base deser: " + SerializationUtils.fromByteArrayJboss(data, Object.class));
            System.out.println(
                    "OrgC deser: " + SerializationUtils.fromByteArrayJboss(data, BClass.class));
        }
        System.out.println("==============================");
        {
            BClass classB = new BClass();
            classB.obj = new AClass();
            System.out.println("Original class: " + classB.getClass());
            byte[] data = SerializationUtils.toByteArrayJboss(classB);
            System.out.println("SerializedSize: " + data.length);
            System.out.println("Bare deser: " + SerializationUtils.fromByteArrayJboss(data));
            System.out.println(
                    "Base deser: " + SerializationUtils.fromByteArrayJboss(data, Object.class));
            System.out.println(
                    "OrgC deser: " + SerializationUtils.fromByteArrayJboss(data, BClass.class));
        }
        System.out.println("==============================");
        {
            LockClass classL = new LockClass();
            System.out.println("Original class: " + classL.getClass());
            byte[] data = SerializationUtils.toByteArrayJboss(classL);
            System.out.println("SerializedSize: " + data.length);
            System.out.println("Bare deser: " + SerializationUtils.fromByteArrayJboss(data));
            System.out.println(
                    "Base deser: " + SerializationUtils.fromByteArrayJboss(data, Object.class));
            System.out.println(
                    "OrgC deser: " + SerializationUtils.fromByteArrayJboss(data, LockClass.class));
        }
        System.out.println("==============================");
        {
            LockClass2 classL = new LockClass2();
            System.out.println("Original class: " + classL.getClass());
            byte[] data = SerializationUtils.toByteArrayJboss(classL);
            System.out.println("SerializedSize: " + data.length);
            System.out.println("Bare deser: " + SerializationUtils.fromByteArrayJboss(data));
            System.out.println(
                    "Base deser: " + SerializationUtils.fromByteArrayJboss(data, Object.class));
            System.out.println(
                    "OrgC deser: " + SerializationUtils.fromByteArrayJboss(data, LockClass2.class));
        }
    }
}
