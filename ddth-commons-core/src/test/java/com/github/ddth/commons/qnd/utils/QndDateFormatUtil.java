package com.github.ddth.commons.qnd.utils;

import java.util.Date;

import com.github.ddth.commons.utils.DateFormatUtils;

public class QndDateFormatUtil {

    public static void main(String[] args) {
        final String DF = "yyyy-MM-dd";
        final String INPUT = "2014-09-29";

        for (int i = 0; i < 16; i++) {
            Thread t = new Thread() {
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        Date d = DateFormatUtils.fromString(INPUT, DF);
                        System.out.println(d);
                    }
                }
            };
            t.start();
        }
    }
}
