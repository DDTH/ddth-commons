package com.github.ddth.commons.qnd.utils;

import java.util.Date;

import com.github.ddth.commons.utils.IdGenerator;

public class QndIdGen {

    public static void main(String[] args) {
        long id = 349682085114126336L;
        System.out.println(new Date(IdGenerator.extractTimestamp64(id)));
    }

}
