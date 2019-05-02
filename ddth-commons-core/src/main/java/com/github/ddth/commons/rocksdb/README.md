# com.github.ddth.commons.rocksdb

Utility and helper classes to work with [RocksDB](http://rocksdb.org).

_**Available since v0.8.0.**_

## Maven

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-rocksdb</artifactId>
    <version>${ddth_commons_version}</version>
    <type>pom</type>
</dependency>
```

**Class `RocksDbUtils`**

- Helper methods to open RocksDB database.
- Wrapper class to GET/PUT/DELETE RocksDB data.


**Class `RocksDbWrapper`**

## Examples

```java
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
```

## History

**v0.9.3 - 2019-04-29**
- Upgrade to `rocksdbjni:6.0.1`.

**v0.8.0 - 2018-01-22**
- First release
