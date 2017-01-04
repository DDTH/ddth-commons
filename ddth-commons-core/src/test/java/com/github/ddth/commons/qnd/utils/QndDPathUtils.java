package com.github.ddth.commons.qnd.utils;

import java.util.HashMap;
import java.util.Map;

import com.github.ddth.commons.utils.DPathUtils;

public class QndDPathUtils {

    public static void main(String[] args) {
        Map<String, Object> data = new HashMap<String, Object>();
        System.out.println(data);

        System.out.println(DPathUtils.getValue(data, "temp", int.class));

        byte[] content = "content".getBytes();
        DPathUtils.setValue(data, "content", content);
        System.out.println(data);

        System.out.println(DPathUtils.getValue(data, "content"));
        System.out.println(DPathUtils.getValue(data, "content", byte[].class));

        data.clear();
        DPathUtils.setValue(data, "num1", 1);
        data.put("num2", 2.0);
        System.out.println(data);
        System.out.println(DPathUtils.getValue(data, "num1"));
        System.out.println(DPathUtils.getValue(data, "num2", Integer.class));
        System.out.println(DPathUtils.getValue(data, "num1", Number.class));
    }

}
