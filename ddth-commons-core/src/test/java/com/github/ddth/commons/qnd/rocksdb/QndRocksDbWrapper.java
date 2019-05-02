package com.github.ddth.commons.qnd.rocksdb;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.github.ddth.commons.rocksdb.RocksDbException;
import com.github.ddth.commons.rocksdb.RocksDbWrapper;

public class QndRocksDbWrapper {
    public static void main(String[] args) throws RocksDbException, IOException {
        // cleanup
        String dataDir = "./temp";
        FileUtils.deleteDirectory(new File(dataDir));

        try (RocksDbWrapper rocksDb = RocksDbWrapper.openReadWrite(dataDir)) {
            // RocksDbWrapper instance has been initialized by RocksDbWrapper.openXXX() method.
            // Calling rocksDb.init() is not needed.

            System.out.println("Number of keys: "
                    + rocksDb.getEstimateNumKeys(RocksDbWrapper.DEFAULT_COLUMN_FAMILY));

            // fetch a value from default column family
            byte[] value = rocksDb.get("key");
            System.out.println("Value of [key]: " + (value != null ? new String(value) : "[null]"));

            // write a value
            rocksDb.put("key", "a value");
            System.out.println("Number of keys: "
                    + rocksDb.getEstimateNumKeys(RocksDbWrapper.DEFAULT_COLUMN_FAMILY));

            // fetch it back
            value = rocksDb.get("key");
            System.out.println("Value of [key]: " + (value != null ? new String(value) : "[null]"));
        }
    }
}
