package com.github.ddth.commons.qnd.utils;

import java.util.Date;

import com.github.ddth.commons.utils.DateFormatUtils;
import com.github.ddth.commons.utils.IdGenerator;

public class QndIdGenDistributed {

    public static void main(String[] args) throws Exception {
        IdGenerator idGen = IdGenerator.getInstance();
        long id48 = idGen.generateId48();
        long timestamp = IdGenerator.extractTimestamp48(id48);
        Date date = new Date(timestamp);
        System.out.println(id48 + " / " + timestamp + " / "
                + DateFormatUtils.toString(date, "yyyy-MM-dd'T'HH:mm:ss.SSS"));

        // Random random = new Random(System.currentTimeMillis());
        //
        // int numThreads = 4;
        // IdGenerator[] idGenList = new IdGenerator[numThreads];
        // for (int i = 0; i < numThreads; i++) {
        // idGenList[i] = IdGenerator.getInstance(i);
        // }
        // for (int i = 0; i < 100; i++) {
        // String id = idGenList[i % numThreads].generateId48Hex();
        // long timestamp = IdGenerator.extractTimestamp48(id);
        // Date date = new Date(timestamp);
        // System.out.println(
        // id + " - " + DateFormatUtils.toString(date, "yyyy-MM-dd'T'HH:mm:ss.SSS"));
        // }
    }

}
