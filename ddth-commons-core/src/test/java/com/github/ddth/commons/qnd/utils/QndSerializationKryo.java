package com.github.ddth.commons.qnd.utils;

import com.github.ddth.commons.utils.SerializationUtils;

public class QndSerializationKryo extends QndSerializationBase {

    public static void main(String[] args) {
        {
            BaseClass classBase = new BaseClass();
            System.out.println("Original class: " + classBase.getClass());
            byte[] data = SerializationUtils.toByteArrayKryo(classBase);
            System.out.println("SerializedSize: " + data.length);
            System.out.println("Bare deser: " + SerializationUtils.fromByteArrayKryo(data));
            System.out.println(
                    "Base deser: " + SerializationUtils.fromByteArrayKryo(data, BaseClass.class));
            System.out.println(
                    "SubC deser: " + SerializationUtils.fromByteArrayKryo(data, AClass.class));
        }
        System.out.println("==============================");
        {
            BaseClass classA = new AClass();
            System.out.println("Original class: " + classA.getClass());
            byte[] data = SerializationUtils.toByteArrayKryo(classA);
            System.out.println("SerializedSize: " + data.length);
            System.out.println("Bare deser: " + SerializationUtils.fromByteArrayKryo(data));
            System.out.println(
                    "Base deser: " + SerializationUtils.fromByteArrayKryo(data, BaseClass.class));
            System.out.println(
                    "SubC deser: " + SerializationUtils.fromByteArrayKryo(data, AClass.class));
        }
        System.out.println("==============================");
        {
            BClass classB = new BClass();
            classB.obj = new BaseClass();
            System.out.println("Original class: " + classB.getClass());
            byte[] data = SerializationUtils.toByteArrayKryo(classB);
            System.out.println("SerializedSize: " + data.length);
            System.out.println("Bare deser: " + SerializationUtils.fromByteArrayKryo(data));
            System.out.println(
                    "Base deser: " + SerializationUtils.fromByteArrayKryo(data, Object.class));
            System.out.println(
                    "OrgC deser: " + SerializationUtils.fromByteArrayKryo(data, BClass.class));
        }
        System.out.println("==============================");
        {
            BClass classB = new BClass();
            classB.obj = new AClass();
            System.out.println("Original class: " + classB.getClass());
            byte[] data = SerializationUtils.toByteArrayKryo(classB);
            System.out.println("SerializedSize: " + data.length);
            System.out.println("Bare deser: " + SerializationUtils.fromByteArrayKryo(data));
            System.out.println(
                    "Base deser: " + SerializationUtils.fromByteArrayKryo(data, Object.class));
            System.out.println(
                    "OrgC deser: " + SerializationUtils.fromByteArrayKryo(data, BClass.class));
        }
        System.out.println("==============================");
        {
            LockClass classL = new LockClass();
            System.out.println("Original class: " + classL.getClass());
            byte[] data = SerializationUtils.toByteArrayKryo(classL);
            System.out.println("SerializedSize: " + data.length);
            System.out.println("Bare deser: " + SerializationUtils.fromByteArrayKryo(data));
            System.out.println(
                    "Base deser: " + SerializationUtils.fromByteArrayKryo(data, Object.class));
            System.out.println(
                    "OrgC deser: " + SerializationUtils.fromByteArrayKryo(data, LockClass.class));
        }
        System.out.println("==============================");
        {
            LockClass2 classL = new LockClass2();
            System.out.println("Original class: " + classL.getClass());
            byte[] data = SerializationUtils.toByteArrayKryo(classL);
            System.out.println("SerializedSize: " + data.length);
            System.out.println("Bare deser: " + SerializationUtils.fromByteArrayKryo(data));
            System.out.println(
                    "Base deser: " + SerializationUtils.fromByteArrayKryo(data, Object.class));
            System.out.println(
                    "OrgC deser: " + SerializationUtils.fromByteArrayKryo(data, LockClass2.class));
        }
    }
}
