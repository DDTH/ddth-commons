package com.github.ddth.commons.qnd.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.ddth.commons.utils.SerializationUtils;

public class QndJson {

    public static class ClassA {
        @JsonProperty
        private String attr1 = "attribute 1";

        @JsonProperty
        private double attr2 = 0.123;

    }

    public static void main(String[] args) {
        ClassA obj = new ClassA();
        String json = SerializationUtils.toJsonString(obj);
        System.out.println(json);

        Object result = SerializationUtils.fromJsonString(json, ClassA.class);
        System.out.println(result.getClass());
    }

}
