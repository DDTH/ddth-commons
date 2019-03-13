package com.github.ddth.commons.qnd.utils;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class QndKryo {

    public static void main(String[] args) {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.setWarnUnregisteredClasses(false);

        Map<String, Object> emptyMap = new HashMap<String, Object>();
        Output output = new Output(1024, -1);
        kryo.writeClassAndObject(output, emptyMap);
        output.close();

        byte[] data = output.toBytes();
        System.out.println(data.length);
        System.out.println(new String(data));

        Input input = new Input(new ByteArrayInputStream(data));
        Object obj = kryo.readClassAndObject(input);
        input.close();
        System.out.println(obj.getClass());
        System.out.println(obj);
    }

}
