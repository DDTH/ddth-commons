[![Build Status](https://travis-ci.org/DDTH/ddth-commons.svg?branch=master)](https://travis-ci.org/DDTH/ddth-commons)

# ddth-commons

DDTH's Java Common Libraries and Utilities.

Project home:
[https://github.com/DDTH/ddth-commons](https://github.com/DDTH/ddth-commons)

**`ddth-commons` requires Java 11+ since v1.0.0, for Java 8, use v0.9.x**


## License

See LICENSE.txt for details. Copyright (c) 2013-2019 Thanh Ba Nguyen.

Third party libraries are distributed under their own license(s).


## Installation

Latest release version: `1.0.0`. See [RELEASE-NOTES.md](RELEASE-NOTES.md).

Maven dependency: if only a sub-set of `ddth-commons` functionality is used, choose the corresponding
dependency artifact(s) to reduce the number of unused jar files.

*ddth-commons-core*: all Spring, Apache Thrift, Serialization/JSON (FasterXML Jackson, Kryo and FST) dependencies are *optional*

```xml
<dependency>
	<groupId>com.github.ddth</groupId>
	<artifactId>ddth-commons-core</artifactId>
	<version>1.0.0</version>
</dependency>
```

*ddth-commons-jedis*: include all *ddth-commons-core* and Jedis dependencies.

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-jedis</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```

*ddth-commons-jsonrpc*: include all *ddth-commons-core* annd *ddth-commons-serialization* dependencies.

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-jsonrpc</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```


*ddth-commons-rocksdb*: include all *ddth-commons-core* and RocksDB JNI dependencies.

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-rocksdb</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```

*ddth-commons-spring*: include all *ddth-commons-core* and Spring dependencies.

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-spring</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```

*ddth-commons-thrift*: include all *ddth-commons-core* and Apache Thrift dependencies.

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-thrift</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```

*ddth-commons-typesafeconfig*: include all *ddth-commons-core* and Typesafe Config dependencies.

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-typesafeconfig</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```

*ddth-commons-serialization*: include all *ddth-commons-core* and FasterXML Jackson, Kryo and FST dependencies.

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-serialization</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```


## Utility/Helper Classes

* See: [com.github.ddth.commons.jsonrpc](ddth-commons-core/src/main/java/com/github/ddth/commons/jsonrpc/README.md).
* See: [com.github.ddth.commons.osgi](ddth-commons-core/src/main/java/com/github/ddth/commons/osgi/README.md).
* See: [com.github.ddth.commons.redis](ddth-commons-core/src/main/java/com/github/ddth/commons/redis/README.md).
* See: [com.github.ddth.commons.rocksdb](ddth-commons-core/src/main/java/com/github/ddth/commons/rocksdb/README.md).
* See: [com.github.ddth.commons.utils](ddth-commons-core/src/main/java/com/github/ddth/commons/utils/README.md).
