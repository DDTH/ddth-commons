package com.github.ddth.commons.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Jedis utility class.
 *
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @see https://github.com/xetorthio/jedis
 * @since 0.8.0
 */
public class JedisUtils {
    private static JedisPoolConfig defaultJedisPoolConfig;

    static {
        final int maxTotal = Runtime.getRuntime().availableProcessors();
        final int maxIdle = maxTotal / 2;
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMinIdle(1);
        poolConfig.setMaxIdle(maxIdle > 0 ? maxIdle : 1);
        poolConfig.setMaxWaitMillis(5000);
        poolConfig.setTestOnBorrow(false);
        poolConfig.setTestWhileIdle(true);
        defaultJedisPoolConfig = poolConfig;
    }

    /**
     * Create/Get a {@link JedisPoolConfig} with default settings.
     *
     * @return
     */
    public static JedisPoolConfig defaultJedisPoolConfig() {
        return defaultJedisPoolConfig;
    }

    /*----------------------------------------------------------------------*/

    /**
     * Create a new {@link JedisPool} with default pool configurations.
     *
     * @param hostAndPort
     *            format {@code host:port} or {@code host}, default Redis port is used if not
     *            specified
     * @return
     */
    public static JedisPool newJedisPool(String hostAndPort) {
        return newJedisPool(defaultJedisPoolConfig(), hostAndPort);
    }

    /**
     * Create a new {@link JedisPool} with specific pool configurations.
     *
     * @param poolConfig
     * @param hostAndPort
     *            format {@code host:port} or {@code host}, default Redis port is used if not
     *            specified
     * @return
     */
    public static JedisPool newJedisPool(JedisPoolConfig poolConfig, String hostAndPort) {
        return newJedisPool(poolConfig, hostAndPort, null, Protocol.DEFAULT_DATABASE,
                Protocol.DEFAULT_TIMEOUT);
    }

    /**
     * Create a new {@link JedisPool} with default pool configurations.
     *
     * @param hostAndPort
     *            format {@code host:port} or {@code host}, default Redis port is used if not
     *            specified
     * @param timeoutMs
     * @return
     */
    public static JedisPool newJedisPool(String hostAndPort, int timeoutMs) {
        return newJedisPool(defaultJedisPoolConfig(), hostAndPort, timeoutMs);
    }

    /**
     * Create a new {@link JedisPool} with specific pool configurations.
     *
     * @param poolConfig
     * @param hostAndPort
     *            format {@code host:port} or {@code host}, default Redis port is used if not
     *            specified
     * @param timeoutMs
     * @return
     */
    public static JedisPool newJedisPool(JedisPoolConfig poolConfig, String hostAndPort,
            int timeoutMs) {
        return newJedisPool(poolConfig, hostAndPort, null, Protocol.DEFAULT_DATABASE, timeoutMs);
    }

    /**
     * Create a new {@link JedisPool} with default pool configurations.
     *
     * @param hostAndPort
     *            format {@code host:port} or {@code host}, default Redis port is used if not
     *            specified
     * @param password
     * @return
     */
    public static JedisPool newJedisPool(String hostAndPort, String password) {
        return newJedisPool(defaultJedisPoolConfig(), hostAndPort, password);
    }

    /**
     * Create a new {@link JedisPool} with specific pool configurations.
     *
     * @param poolConfig
     * @param hostAndPort
     *            format {@code host:port} or {@code host}, default Redis port is used if not
     *            specified
     * @param password
     * @return
     */
    public static JedisPool newJedisPool(JedisPoolConfig poolConfig, String hostAndPort,
            String password) {
        return newJedisPool(poolConfig, hostAndPort, password, Protocol.DEFAULT_DATABASE,
                Protocol.DEFAULT_TIMEOUT);
    }

    /**
     * Create a new {@link JedisPool} with default pool configurations.
     *
     * @param hostAndPort
     *            format {@code host:port} or {@code host}, default Redis port is used if not
     *            specified
     * @param password
     * @param timeoutMs
     * @return
     */
    public static JedisPool newJedisPool(String hostAndPort, String password, int timeoutMs) {
        return newJedisPool(defaultJedisPoolConfig(), hostAndPort, password, timeoutMs);
    }

    /**
     * Create a new {@link JedisPool} with specific pool configurations.
     *
     * @param poolConfig
     * @param hostAndPort
     *            format {@code host:port} or {@code host}, default Redis port is used if not
     *            specified
     * @param password
     * @param timeoutMs
     * @return
     */
    public static JedisPool newJedisPool(JedisPoolConfig poolConfig, String hostAndPort,
            String password, int timeoutMs) {
        return newJedisPool(poolConfig, hostAndPort, password, Protocol.DEFAULT_DATABASE,
                timeoutMs);
    }

    /**
     * Create a new {@link JedisPool} with default pool configurations.
     *
     * @param hostAndPort
     *            format {@code host:port} or {@code host}, default Redis port is used if not
     *            specified
     * @param password
     * @param db
     * @param timeoutMs
     * @return
     * @since 0.9.4
     */
    public static JedisPool newJedisPool(String hostAndPort, String password, int db,
            int timeoutMs) {
        return newJedisPool(defaultJedisPoolConfig, hostAndPort, password, db, timeoutMs);
    }

    /**
     * Create a new {@link JedisPool} with specific pool configurations.
     *
     * @param poolConfig
     * @param hostAndPort
     *            format {@code host:port} or {@code host}, default Redis port is used if not
     *            specified
     * @param password
     * @param db
     * @param timeoutMs
     * @return
     */
    public static JedisPool newJedisPool(JedisPoolConfig poolConfig, String hostAndPort,
            String password, int db, int timeoutMs) {
        String[] tokens = hostAndPort.split(":");
        String host = tokens.length > 0 ? tokens[0] : Protocol.DEFAULT_HOST;
        int port = tokens.length > 1 ? Integer.parseInt(tokens[1]) : Protocol.DEFAULT_PORT;
        JedisPool jedisPool = new JedisPool(
                poolConfig != null ? poolConfig : defaultJedisPoolConfig(), host, port, timeoutMs,
                StringUtils.isBlank(password) ? null : password, db);
        return jedisPool;
    }

    /*----------------------------------------------------------------------*/

    /**
     * Create a new {@link JedisCluster} with default pool configurations.
     *
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @return
     */
    public static JedisCluster newJedisCluster(String hostsAndPorts) {
        return newJedisCluster(defaultJedisPoolConfig(), hostsAndPorts);
    }

    /**
     * Create a new {@link JedisCluster} with specific pool configurations.
     *
     * @param poolConfig
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @return
     */
    public static JedisCluster newJedisCluster(JedisPoolConfig poolConfig, String hostsAndPorts) {
        return newJedisCluster(poolConfig, hostsAndPorts, null, Protocol.DEFAULT_TIMEOUT, 3);
    }

    /**
     * Create a new {@link JedisCluster} with default pool configurations.
     *
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param timeoutMs
     * @return
     */
    public static JedisCluster newJedisCluster(String hostsAndPorts, int timeoutMs) {
        return newJedisCluster(defaultJedisPoolConfig(), hostsAndPorts, timeoutMs);
    }

    /**
     * Create a new {@link JedisCluster} with specific pool configurations.
     *
     * @param poolConfig
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param timeoutMs
     * @return
     */
    public static JedisCluster newJedisCluster(JedisPoolConfig poolConfig, String hostsAndPorts,
            int timeoutMs) {
        return newJedisCluster(poolConfig, hostsAndPorts, null, timeoutMs, 3);
    }

    /**
     * Create a new {@link JedisCluster} with default pool configurations.
     *
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param password
     * @return
     */
    public static JedisCluster newJedisCluster(String hostsAndPorts, String password) {
        return newJedisCluster(defaultJedisPoolConfig(), hostsAndPorts, password);
    }

    /**
     * Create a new {@link JedisCluster} with specific pool configurations.
     *
     * @param poolConfig
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param password
     * @return
     */
    public static JedisCluster newJedisCluster(JedisPoolConfig poolConfig, String hostsAndPorts,
            String password) {
        return newJedisCluster(poolConfig, hostsAndPorts, password, Protocol.DEFAULT_TIMEOUT, 3);
    }

    /**
     * Create a new {@link JedisCluster} with default pool configurations.
     *
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param password
     * @param timeoutMs
     * @return
     */
    public static JedisCluster newJedisCluster(String hostsAndPorts, String password,
            int timeoutMs) {
        return newJedisCluster(defaultJedisPoolConfig(), hostsAndPorts, password, timeoutMs);
    }

    /**
     * Create a new {@link JedisCluster} with specific pool configurations.
     *
     * @param poolConfig
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param password
     * @param timeoutMs
     * @return
     */
    public static JedisCluster newJedisCluster(JedisPoolConfig poolConfig, String hostsAndPorts,
            String password, int timeoutMs) {
        return newJedisCluster(poolConfig, hostsAndPorts, password, timeoutMs, 3);
    }

    /**
     * Create a new {@link JedisCluster} with default pool configurations.
     * 
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param password
     * @param timeoutMs
     * @param maxAttempts
     * @return
     * @since 0.9.4
     */
    public static JedisCluster newJedisCluster(String hostsAndPorts, String password, int timeoutMs,
            int maxAttempts) {
        return newJedisCluster(defaultJedisPoolConfig, hostsAndPorts, password, timeoutMs,
                maxAttempts);
    }

    /**
     * Create a new {@link JedisCluster} with specific pool configurations.
     * 
     * @param poolConfig
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param password
     * @param timeoutMs
     * @param maxAttempts
     * @return
     */
    public static JedisCluster newJedisCluster(JedisPoolConfig poolConfig, String hostsAndPorts,
            String password, int timeoutMs, int maxAttempts) {
        Set<HostAndPort> clusterNodes = new HashSet<>();
        String[] hapList = hostsAndPorts.split("[,;\\s]+");
        for (String hostAndPort : hapList) {
            String[] tokens = hostAndPort.split(":");
            String host = tokens.length > 0 ? tokens[0] : Protocol.DEFAULT_HOST;
            int port = tokens.length > 1 ? Integer.parseInt(tokens[1]) : Protocol.DEFAULT_PORT;
            clusterNodes.add(new HostAndPort(host, port));
        }
        JedisCluster jedisCluster = new JedisCluster(clusterNodes, timeoutMs, timeoutMs,
                maxAttempts, StringUtils.isBlank(password) ? null : password,
                poolConfig != null ? poolConfig : defaultJedisPoolConfig());
        return jedisCluster;
    }

    /*----------------------------------------------------------------------*/
    /**
     * Create a new {@link ShardedJedisPool} with default pool configurations.
     *
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @return
     */
    public static ShardedJedisPool newShardedJedisPool(String hostsAndPorts) {
        return newShardedJedisPool(defaultJedisPoolConfig(), hostsAndPorts);
    }

    /**
     * Create a new {@link ShardedJedisPool} with specific pool configurations.
     *
     * @param poolConfig
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @return
     */
    public static ShardedJedisPool newShardedJedisPool(JedisPoolConfig poolConfig,
            String hostsAndPorts) {
        return newShardedJedisPool(poolConfig, hostsAndPorts, null, Protocol.DEFAULT_TIMEOUT);
    }

    /**
     * Create a new {@link ShardedJedisPool} with default pool configurations.
     *
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param timeoutMs
     * @return
     */
    public static ShardedJedisPool newShardedJedisPool(String hostsAndPorts, int timeoutMs) {
        return newShardedJedisPool(defaultJedisPoolConfig(), hostsAndPorts, timeoutMs);
    }

    /**
     * Create a new {@link ShardedJedisPool} with specific pool configurations.
     *
     * @param poolConfig
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param timeoutMs
     * @return
     */
    public static ShardedJedisPool newShardedJedisPool(JedisPoolConfig poolConfig,
            String hostsAndPorts, int timeoutMs) {
        return newShardedJedisPool(poolConfig, hostsAndPorts, null, timeoutMs);
    }

    /**
     * Create a new {@link ShardedJedisPool} with default pool configurations.
     *
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param password
     * @return
     */
    public static ShardedJedisPool newShardedJedisPool(String hostsAndPorts, String password) {
        return newShardedJedisPool(defaultJedisPoolConfig(), hostsAndPorts, password);
    }

    /**
     * Create a new {@link ShardedJedisPool} with specific pool configurations.
     *
     * @param poolConfig
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param password
     * @return
     */
    public static ShardedJedisPool newShardedJedisPool(JedisPoolConfig poolConfig,
            String hostsAndPorts, String password) {
        return newShardedJedisPool(poolConfig, hostsAndPorts, password, Protocol.DEFAULT_TIMEOUT);
    }

    /**
     * Create a new {@link ShardedJedisPool} with default pool configurations.
     *
     * @param hostsAndPorts
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param password
     * @param timeoutMs
     * @return
     */
    public static ShardedJedisPool newShardedJedisPool(String hostsAndPorts, String password,
            int timeoutMs) {
        return newShardedJedisPool(defaultJedisPoolConfig(), hostsAndPorts, password, timeoutMs);
    }

    /**
     * Create a new {@link ShardedJedisPool}.
     * 
     * @param poolConfig
     *            format {@code host1:port1,host2:port2,...}, default Redis port is used if not
     *            specified
     * @param password
     * @param timeoutMs
     * @return
     */
    public static ShardedJedisPool newShardedJedisPool(JedisPoolConfig poolConfig,
            String hostsAndPorts, String password, int timeoutMs) {
        List<JedisShardInfo> shards = new ArrayList<>();
        String[] hapList = hostsAndPorts.split("[,;\\s]+");
        for (String hostAndPort : hapList) {
            String[] tokens = hostAndPort.split(":");
            String host = tokens.length > 0 ? tokens[0] : Protocol.DEFAULT_HOST;
            int port = tokens.length > 1 ? Integer.parseInt(tokens[1]) : Protocol.DEFAULT_PORT;
            JedisShardInfo shardInfo = new JedisShardInfo(host, port, timeoutMs);
            shardInfo.setPassword(StringUtils.isBlank(password) ? null : password);
            shards.add(shardInfo);
        }
        ShardedJedisPool jedisPool = new ShardedJedisPool(
                poolConfig != null ? poolConfig : defaultJedisPoolConfig(), shards);
        return jedisPool;
    }

    /*----------------------------------------------------------------------*/
}
