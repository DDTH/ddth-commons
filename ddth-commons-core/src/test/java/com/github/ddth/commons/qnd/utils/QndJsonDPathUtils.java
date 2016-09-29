package com.github.ddth.commons.qnd.utils;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.github.ddth.commons.utils.DPathUtils;
import com.github.ddth.commons.utils.SerializationUtils;

public class QndJsonDPathUtils {
    public static void main(String[] args) {
        Collection<Object> c = new HashSet<Object>();
        c.add(1);
        c.add("3");
        c.add(5.0);

        Map<String, Object> m = new HashMap<String, Object>();
        m.put("key1", 2.0);
        m.put("key2", "4");
        m.put("key3", 6);

        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("d", new Date());
        obj.put("a", new Object[] { 1, 2, "a", 3.0 });
        obj.put("c", c);
        obj.put("m", m);

        String json = SerializationUtils.toJsonString(obj);
        System.out.println("JSON: " + json);

        Map<?, ?> obj2 = SerializationUtils.fromJsonString(json, Map.class);
        System.out.println("Obj2: " + obj2);
        System.out.println("d-class: " + obj2.get("d").getClass());
        System.out.println("d-value: " + DPathUtils.getValue(obj2, "d", Date.class));

        System.out.println("m-value: " + DPathUtils.getValue(obj2, "m", Map.class));
        System.out.println("a-value: " + DPathUtils.getValue(obj2, "a", List.class));
        System.out.println("a-value: " + DPathUtils.getValue(obj2, "a", Object[].class));
        System.out.println("c-value: " + DPathUtils.getValue(obj2, "c", Collection.class));
        System.out.println("c-value: " + DPathUtils.getValue(obj2, "c", List.class));
    }
}
