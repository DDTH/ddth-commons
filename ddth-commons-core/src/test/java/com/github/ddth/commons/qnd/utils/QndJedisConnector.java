package com.github.ddth.commons.qnd.utils;

import java.io.IOException;

import com.github.ddth.commons.redis.JedisConnector;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

public class QndJedisConnector {
    public static void main(String[] args) throws IOException {
        try (JedisConnector jc = new JedisConnector()) {
            jc.setRedisHostsAndPorts("localhost:6379").setRedisPassword(null);
            jc.init();
            try (Jedis jedis = jc.getJedis()) {
                System.out.println(jedis.echo("hello redis"));
            }
        }

        try (JedisConnector jc = new JedisConnector()) {
            jc.setRedisHostsAndPorts("localhost:7000").setRedisPassword(null);
            jc.init();
            try (JedisCluster jedis = jc.getJedisCluster()) {
                System.out.println(jedis.echo("hello cluster"));
            }
        }

        try (JedisConnector jc = new JedisConnector()) {
            jc.setRedisHostsAndPorts("localhost:6379,localhost:7000").setRedisPassword(null);
            jc.init();
            try (ShardedJedis jedis = jc.getShardedJedis()) {
                System.out.println(jedis.echo("hello shard"));
            }
        }

    }
}
