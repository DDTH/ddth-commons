package com.github.ddth.commons.qnd.utils;

import java.util.Date;

import com.github.ddth.commons.utils.IdGenerator;

public class QndIdDecode {
    public static void main(String[] args) {
        long timestamp = IdGenerator.extractTimestamp128("166ebcdb2300000000000000000");
        System.out.println(timestamp);
        System.out.println(new Date(timestamp));
    }
}
