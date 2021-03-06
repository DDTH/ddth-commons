# com.github.ddth.commons.redis

Utility to work with [Redis](https://redis.io) server using [Jedis](https://github.com/xetorthio/jedis).

_**Available since v0.8.0.**_

## Maven

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-jedis</artifactId>
    <version>${ddth_commons_version}</version>
    <type>pom</type>
</dependency>
```

**Class `JedisUtils`:**

Helper class to:
- Obtain default instance of `redis.clients.jedis.JedisPoolConfig`.
- Create `redis.clients.jedis.JedisPool`, `redis.clients.jedis.JedisCluster`, `redis.clients.jedis.ShardedJedisPool` instances.

**Class `JedisConnector`:**

Wrapper class to manage connections to Redis' servers, clusters or sharded servers.

## Examples

Working wirh Redis using Jedis library: see [Jedis documentations](https://github.com/xetorthio/jedis).

```java
package qnd;

import com.github.ddth.commons.redis.JedisConnector;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;

public class QndJedisConnector {
    public static void main(String[] args) {
        // create a JedisPoolConfig instance
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        {
            // configure the JedisPoolConfig
            int maxTotal = Runtime.getRuntime().availableProcessors();
            int maxIdle = maxTotal / 2;
            poolConfig.setMaxTotal(maxTotal);
            poolConfig.setMinIdle(1);
            poolConfig.setMaxIdle(maxIdle > 0 ? maxIdle : 1);
            poolConfig.setMaxWaitMillis(5000);
            poolConfig.setTestOnBorrow(false);
            poolConfig.setTestWhileIdle(true);
        }

        // create & initialize JedisConnector
        try (JedisConnector connector = new JedisConnector()) {
            connector.setJedisPoolConfig(poolConfig);
            connector.setRedisHostsAndPorts("localhost");
            // connector.setRedisHostsAndPorts("192.168.1.1:6379;192.168.1.2:16379;192.168.1.1:26379");
            // connector.setRedisPassword("secret");
            connector.init();

            // obtain a Jedis instance
            try (Jedis jedis = connector.getJedis()) {
                // do some business here
            }

            // obtain a ShardedJedis instance
            try (ShardedJedis sharded = connector.getShardedJedis()) {
                // do some business here
            }

            // obtain a JedisCluster instance
            try (JedisCluster cluster = connector.getJedisCluster()) {
                // do some business here
            }
        }
    }
}
```
