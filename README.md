ddth-commons
============

DDTH's Java Common Libraries and Utilities.

Project home:
[https://github.com/DDTH/ddth-commons](https://github.com/DDTH/ddth-commons)

**`ddth-commons` requires Java 8+ since v0.5.0**


## License ##

See LICENSE.txt for details. Copyright (c) 2013-2017 Thanh Ba Nguyen.

Third party libraries are distributed under their own license(s).


## Installation ##

Latest release version: `0.6.3`. See [RELEASE-NOTES.md](RELEASE-NOTES.md).

Maven dependency: if only a sub-set of `ddth-commons` functionality is used, choose the corresponding
dependency artifact(s) to reduce the number of unused jar files.

*ddth-commons-core*: all Spring, Apache Thrift, Jboss-serialization, FasterXML Jackson, Kryo and FST dependencies are *optional*

```xml
<dependency>
	<groupId>com.github.ddth</groupId>
	<artifactId>ddth-commons-core</artifactId>
	<version>0.6.3</version>
</dependency>
```

*ddth-commons-spring*: include all *ddth-commons-core* and Spring dependencies.

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-spring</artifactId>
    <version>0.6.3</version>
    <type>pom</type>
</dependency>
```

*ddth-commons-thrift*: include all *ddth-commons-core* and Apache Thrift dependencies.

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-thrift</artifactId>
    <version>0.6.3</version>
    <type>pom</type>
</dependency>
```

*ddth-commons-serialization*: include all *ddth-commons-core* and Jboss-serialization, FasterXML Jackson, Kryo and FST dependencies.

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-serialization</artifactId>
    <version>0.6.3</version>
    <type>pom</type>
</dependency>
```


## Utility/Helper Classes ##

* See: [com.github.ddth.commons.utils](src/main/java/com/github/ddth/commons/utils/README.md).
* See: [com.github.ddth.commons.osgi](src/main/java/com/github/ddth/commons/osgi/README.md).
