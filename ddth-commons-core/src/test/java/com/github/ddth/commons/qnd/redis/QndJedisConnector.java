package com.github.ddth.commons.qnd.redis;

import com.github.ddth.commons.redis.JedisConnector;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;

/*
 * mvn exec:java -Dexec.mainClass="com.github.ddth.commons.qnd.redis.QndJedisConnector"
 */
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
