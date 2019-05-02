package com.github.ddth.commons.qnd.serialization;

import java.io.IOException;

import com.github.ddth.commons.rocksdb.RocksDbException;
import com.github.ddth.commons.serialization.FstSerDeser;
import com.github.ddth.commons.serialization.ISerDeser;
import com.github.ddth.commons.serialization.JsonSerDeser;
import com.github.ddth.commons.serialization.KryoSerDeser;

public class QndSerialization {

    static class ClassA {
        public String fieldString = "a string";
        public int fieldInt = 19;

        public ClassA() {
        }

        public ClassA(String valString, int valInt) {
            fieldString = valString;
            fieldInt = valInt;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ClassA{");
            sb.append("fieldString:").append(fieldString);
            sb.append(",fieldInt:").append(fieldInt);
            sb.append("}");
            return sb.toString();
        }
    }

    static class ClassB {
        public ClassA fieldA = new ClassA("another string", 81);
        public boolean fieldBool = true;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ClassB{");
            sb.append("fieldA:").append(fieldA.toString());
            sb.append(",fieldBool:").append(fieldBool);
            sb.append("}");
            return sb.toString();
        }
    }

    static void qndSerDeser(ISerDeser sd) {
        System.out.println("===== Serialize/Deserialize using " + sd.getClass());
        ClassA A = new ClassA();
        System.out.println("Original A  : " + A.toString());
        byte[] bytesA = sd.toBytes(A);
        System.out.println("Serialized A: " + bytesA.length + " bytes");
        ClassA deserA = sd.fromBytes(bytesA, ClassA.class);
        System.out.println("Desered A   : " + deserA.toString());

        ClassB B = new ClassB();
        System.out.println("Original B  : " + B.toString());
        byte[] bytesB = sd.toBytes(B);
        System.out.println("Serialized B: " + bytesB.length + " bytes");
        ClassB deserB = sd.fromBytes(bytesB, ClassB.class);
        System.out.println("Desered B   : " + deserB.toString());

        System.out.println();
    }

    public static void main(String[] args) throws RocksDbException, IOException {
        qndSerDeser(new JsonSerDeser());
        qndSerDeser(new FstSerDeser());
        qndSerDeser(new KryoSerDeser());
    }
}
