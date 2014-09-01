package com.github.ddth.commons.test.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.github.ddth.commons.utils.DPathUtils;
import com.github.ddth.commons.utils.SerializationUtils;

public class QndJsonDPathUtils {
    public static void main(String[] args) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("d", new Date());
        String json = SerializationUtils.toJsonString(obj);
        System.out.println(json);
        Map obj2 = SerializationUtils.fromJsonString(json, Map.class);
        System.out.println(obj2);
        System.out.println(obj2.get("d").getClass());
        System.out.println(DPathUtils.getValue(obj2, "d", Date.class));
    }
}
