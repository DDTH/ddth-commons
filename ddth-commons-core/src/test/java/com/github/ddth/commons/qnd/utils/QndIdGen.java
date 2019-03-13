package com.github.ddth.commons.qnd.utils;

import java.util.Date;

import com.github.ddth.commons.utils.DateFormatUtils;
import com.github.ddth.commons.utils.IdGenerator;

public class QndIdGen {

    public static void main(String[] args) throws Exception {
        {
            long timestamp = IdGenerator.extractTimestamp128("16814a112890000000007bd0001");
            Date date = new Date(timestamp);
            System.out.println("16814a112890000000007bd0001 - "
                    + DateFormatUtils.toString(date, "yyyy-MM-dd HH:mm:ss.SSS"));
        }
        {
            long timestamp = IdGenerator.extractTimestamp128Ascii("24tvw888bg5k210sn7if6");
            Date date = new Date(timestamp);
            System.out.println("24tvw888bg5k210sn7if6 - "
                    + DateFormatUtils.toString(date, "yyyy-MM-dd HH:mm:ss.SSS"));
        }
        {
            long timestamp = IdGenerator.extractTimestamp64(1958965805555715L);
            Date date = new Date(timestamp);
            System.out.println("1958965805555715 - "
                    + DateFormatUtils.toString(date, "yyyy-MM-dd HH:mm:ss.SSS"));
        }
        {
            long timestamp = IdGenerator.extractTimestamp64("6f5ab44f7a004");
            Date date = new Date(timestamp);
            System.out.println(
                    "6f5ab44f7a004 - " + DateFormatUtils.toString(date, "yyyy-MM-dd HH:mm:ss.SSS"));
        }
        {
            long timestamp = IdGenerator.extractTimestamp64Ascii("jae8gtmo05");
            Date date = new Date(timestamp);
            System.out.println(
                    "jae8gtmo05 - " + DateFormatUtils.toString(date, "yyyy-MM-dd HH:mm:ss.SSS"));
        }

        // Random random = new Random(System.currentTimeMillis());
        // IdGenerator idGen = IdGenerator.getInstance();
        // for (int i = 0; i < 100; i++) {
        // String id = idGen.generateIdTinyHex();
        // long timestamp = IdGenerator.extractTimestampTiny(id);
        // Date date = new Date(timestamp);
        // System.out.println(id + " - " + DateFormatUtils.toString(date, "yyyyMMdd:HHmmss"));
        // Thread.sleep(random.nextInt(1024));
        //
        // for (int j = 0, n = random.nextInt(10240); j < n; j++) {
        // idGen.generateIdTinyHex();
        // }
        // }
    }

}
