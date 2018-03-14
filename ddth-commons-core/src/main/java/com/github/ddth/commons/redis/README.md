# com.github.ddth.commons.redis

***New since v0.8.0***

Redis utility and helper classes, based on [Jedis](https://github.com/xetorthio/jedis) lib.

***Maven***

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-jedis</artifactId>
    <version>${ddth_commons_version}</version>
    <type>pom</type>
</dependency>
```

## Class `JedisConnector` and `JedisUtils`

Helper class(es) to work with [Redis](https://redis.io) servers using [Jedis](https://github.com/xetorthio/jedis).

- Helper methods to create Jedis' pool, cluster instance and sharded pool.
- Wrapper class to manage connections to Redis' server, cluster or sharded servers.
