package com.github.ddth.commons.qnd.utils;

import java.util.HashMap;
import java.util.Map;

import com.github.ddth.commons.utils.DPathUtils;

public class QndDPathUtils {

    public static void main(String[] args) {
        Map<String, Object> data = new HashMap<String, Object>();
        DPathUtils.getValue(data, "temp", int.class);
        
        System.out.println(data);

        byte[] content = "content".getBytes();
        DPathUtils.setValue(data, "content", content);
        System.out.println(data);

        System.out.println(DPathUtils.getValue(data, "content"));
        System.out.println(DPathUtils.getValue(data, "content", byte[].class));
    }

}
