package com.github.ddth.commons.utils;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class to generate IDs using Twitter Snowflake algorithm.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public class IdGenerator {

    private final static ConcurrentMap<Long, IdGenerator> cache = new ConcurrentHashMap<Long, IdGenerator>();
    private static long macAddr = 0;

    /**
     * Returns host's MAC address.
     * 
     * @return
     */
    public static long getMacAddr() {
        if (macAddr == 0) {
            try {
                InetAddress ip = InetAddress.getLocalHost();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                byte[] mac = network.getHardwareAddress();
                for (byte temp : mac) {
                    macAddr = (macAddr << 8) | ((int) temp & 0xFF);
                }
            } catch (Exception e) {
                macAddr = System.currentTimeMillis();
            }
        }
        return macAddr;
    }

    /**
     * Gets an {@link IdGenerator} instance for a node.
     * 
     * @param nodeId
     * @return
     */
    public static IdGenerator getInstance(long nodeId) {
        IdGenerator idGen = cache.get(nodeId);
        if (idGen == null) {
            idGen = new IdGenerator(nodeId);
            idGen.init();
            cache.putIfAbsent(nodeId, idGen);
        }
        return idGen;
    }

    /**
     * Disposes an unused {@link IdGenerator}.
     * 
     * @param idGen
     */
    public static void disposeInstance(IdGenerator idGen) {
        if (idGen != null) {
            long nodeId = idGen.nodeId;
            IdGenerator temp = cache.remove(nodeId);
            if (temp != null) {
                temp.destroy();
            }
        }
    }

    private final static long MASK_TIMESTAMP_MINI = 0x1FFFFFFFFFFL; // 41 bits
    private final static long MASK_NODE_ID_MINI = 0x0L; // 0 bits
    private final static long MASK_SEQUENCE_MINI = 0x7FL; // 7 bits
    private final static long MAX_SEQUENCE_MINI = 0x7FL; // 7 bits
    private final static long SHIFT_TIMESTAMP_MINI = 7L;
    private final static long SHIFT_NODE_ID_MINI = 7L;

    private final static long MASK_TIMESTAMP_48 = 0xFFFFFFFFL; // 32 bits
    private final static long MASK_NODE_ID_48 = 0x7L; // 3 bits
    private final static long MASK_SEQUENCE_48 = 0x1FFFL; // 13 bits
    private final static long MAX_SEQUENCE_48 = 0x1FFFL; // 13 bits
    private final static long SHIFT_TIMESTAMP_48 = 16L;
    private final static long SHIFT_NODE_ID_48 = 13L;

    private final static long MASK_TIMESTAMP_64 = 0x1FFFFFFFFFFL; // 41 bits
    private final static long MASK_NODE_ID_64 = 0x3FFL; // 10 bits
    private final static long MASK_SEQUENCE_64 = 0x1FFFL; // 13 bits
    private final static long MAX_SEQUENCE_64 = 0x1FFFL; // 13 bits
    private final static long SHIFT_TIMESTAMP_64 = 23L;
    private final static long SHIFT_NODE_ID_64 = 13L;
    // private final static long TIMESTAMP_EPOCH = 1330534800000L; // 1-Mar-2012
    public final static long TIMESTAMP_EPOCH = 1362070800000L; // 1-Mar-2013

    private final static long MASK_NODE_ID_128 = 0xFFFFFFFFFFFFL; // 48 bits
    private final static long MASK_SEQUENCE_128 = 0xFFFF; // 16 bits
    private final static long MAX_SEQUENCE_128 = 0xFFFF; // 16 bits
    private final static long SHIFT_TIMESTAMP_128 = 64L;
    private final static long SHIFT_NODE_ID_128 = 16L;

    private long nodeId;
    private long template48, template64, templateMini;
    private BigInteger template128;
    private AtomicLong sequenceMillisec = new AtomicLong();
    private AtomicLong sequenceSecond = new AtomicLong();
    private AtomicLong lastTimestampMillisec = new AtomicLong();
    private AtomicLong lastTimestampSecond = new AtomicLong();

    /**
     * Constructs a new {@link IdGenerator} instance with specified node id.
     * 
     * @param nodeId
     */
    protected IdGenerator(long nodeId) {
        this.nodeId = nodeId;
    }

    protected void init() {
        this.templateMini = (this.nodeId & MASK_NODE_ID_MINI) << SHIFT_NODE_ID_MINI;
        this.template64 = (this.nodeId & MASK_NODE_ID_64) << SHIFT_NODE_ID_64;
        this.template48 = (this.nodeId & MASK_NODE_ID_48) << SHIFT_NODE_ID_48;
        this.template128 = BigInteger
                .valueOf((this.nodeId & MASK_NODE_ID_128) << SHIFT_NODE_ID_128);
    }

    protected void destroy() {
        // EMPTY
    }

    /**
     * Waits till clock moves to the next millisecond.
     * 
     * @param currentMillisec
     * @return the "next" millisecond
     */
    public static long waitTillNextMillisec(long currentMillisec) {
        long nextMillisec = System.currentTimeMillis();
        for (; nextMillisec <= currentMillisec; nextMillisec = System.currentTimeMillis()) {
            Thread.yield();
        }
        return nextMillisec;
    }

    /**
     * Waits till clock moves to the next second.
     * 
     * @param currentSecond
     * @return the "next" second
     */
    public static long waitTillNextSecond(long currentSecond) {
        long nextSecond = System.currentTimeMillis() / 1000L;
        for (; nextSecond <= currentSecond; nextSecond = System.currentTimeMillis() / 1000) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return nextSecond;
    }

    /**
     * Waits till clock moves to the next tick.
     * 
     * @param currentTick
     * @param tickSize
     *            tick size in milliseconds
     * @return
     */
    public static long waitTillNextTick(long currentTick, long tickSize) {
        long nextBlock = System.currentTimeMillis() / tickSize;
        for (; nextBlock <= currentTick; nextBlock = System.currentTimeMillis() / tickSize) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        return nextBlock;
    }

    /* tiny id */
    /**
     * Extracts the (UNIX) timestamp from a tiny id.
     * 
     * @param idTiny
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestampTiny(long idTiny) {
        final long division = 10000L;
        final long seqBits = 16L;
        final long threshold = 0x800000000L;
        long timestamp = idTiny > threshold ? idTiny >>> seqBits : idTiny;
        return timestamp * division + TIMESTAMP_EPOCH;
    }

    /**
     * Extracts the (UNIX) timestamp from a tiny hex id.
     * 
     * @param idTinyHex
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestampTiny(String idTinyHex) {
        long idTiny = Long.parseLong(idTinyHex, 16);
        return extractTimestampTiny(idTiny);
    }

    /**
     * Extracts the (UNIX) timestamp from a tiny ascii id (radix
     * {@link Character#MAX_RADIX}).
     * 
     * @param idTinyAscii
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestampTinyAscii(String idTinyAscii) {
        long idTiny = Long.parseLong(idTinyAscii, Character.MAX_RADIX);
        return extractTimestampTiny(idTiny);
    }

    /**
     * Generates a tiny id (various bit long, node is not accounted).
     * 
     * Format: <41-bit: timestamp><0 or 16bit:sequence number>
     * 
     * Where timestamp is in second, minus the epoch.
     * 
     * Note: the generated id is NOT in order!
     * 
     * @return
     */
    synchronized public long generateIdTiny() {
        final long division = 10000L; // block 10000 ms
        final long seqBits = 16L;
        final long maxSeqTiny = 0xFFFFL; // 16 bits

        long timestamp = System.currentTimeMillis() / division;
        long sequence = 0;
        if (timestamp == this.lastTimestampSecond.get()) {
            // increase sequence
            sequence = this.sequenceSecond.incrementAndGet();
            if (sequence > maxSeqTiny) {
                // reset sequence
                this.sequenceSecond.set(0);
                waitTillNextTick(timestamp, division);
                return generateIdTiny();
            }
        } else {
            // reset sequence
            this.sequenceSecond.set(sequence);
            this.lastTimestampSecond.set(timestamp);
        }
        timestamp -= TIMESTAMP_EPOCH / division;
        long result = timestamp;
        if (sequence != 0) {
            result = (result << seqBits) | sequence;
        }
        return result;
    }

    /**
     * Generate a tiny id as hex string.
     * 
     * @return
     */
    public String generateIdTinyHex() {
        long id = generateIdTiny();
        return Long.toHexString(id).toUpperCase();
    }

    /**
     * Generate a tiny id as ASCII string (radix {@link Character#MAX_RADIX}).
     * 
     * @return
     */
    public String generateIdTinyAscii() {
        long id = generateIdTiny();
        return Long.toString(id, Character.MAX_RADIX).toUpperCase();
    }

    /* tiny id */

    /* 48-bit id */

    /**
     * Extracts the (UNIX) timestamp from a 48-bit id.
     * 
     * @param id48
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestamp48(long id48) {
        long timestamp = (id48 >>> SHIFT_TIMESTAMP_48) + TIMESTAMP_EPOCH;
        return timestamp;
    }

    /**
     * Extracts the (UNIX) timestamp from a 48-bit hex id.
     * 
     * @param id48hex
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestamp48(String id48hex) {
        long id48 = Long.parseLong(id48hex, 16);
        return extractTimestamp64(id48);
    }

    /**
     * Extracts the (UNIX) timestamp from a 48-bit ASCII id (radix
     * {@link Character#MAX_RADIX}).
     * 
     * @param id48ascii
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestamp48Ascii(String id48ascii) {
        long id48 = Long.parseLong(id48ascii, Character.MAX_RADIX);
        return extractTimestamp64(id48);
    }

    /**
     * Generates a 48-bit id.
     * 
     * Format: <32-bit: timestamp><3-bit: node id><13 bit: sequence number>
     * 
     * Where timestamp is in millisec, minus the epoch.
     * 
     * @return
     */
    synchronized public long generateId48() {
        long timestamp = System.currentTimeMillis();
        long sequence = 0;
        if (timestamp == this.lastTimestampMillisec.get()) {
            // increase sequence
            sequence = this.sequenceMillisec.incrementAndGet();
            if (sequence > MAX_SEQUENCE_48) {
                // reset sequence
                this.sequenceMillisec.set(0);
                waitTillNextMillisec(timestamp);
                return generateId48();
            }
        } else {
            // reset sequence
            this.sequenceMillisec.set(sequence);
            this.lastTimestampMillisec.set(timestamp);
        }
        timestamp = (timestamp - TIMESTAMP_EPOCH) & MASK_TIMESTAMP_48;
        long result = timestamp << SHIFT_TIMESTAMP_48 | template48 | (sequence & MASK_SEQUENCE_48);
        return result;
    }

    /**
     * Generate a 48-bit id as hex string.
     * 
     * @return
     */
    public String generateId48Hex() {
        long id = generateId48();
        return Long.toHexString(id).toUpperCase();
    }

    /**
     * Generate a 48-bit id as ASCII string (radix {@link Character#MAX_RADIX}).
     * 
     * @return
     */
    public String generateId48Ascii() {
        long id = generateId48();
        return Long.toString(id, Character.MAX_RADIX).toUpperCase();
    }

    /* 48-bit id */

    /* mini id */
    /**
     * Extracts the (UNIX) timestamp from a mini id.
     * 
     * @param idMini
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestampMini(long idMini) {
        long timestamp = (idMini >>> SHIFT_TIMESTAMP_MINI) + TIMESTAMP_EPOCH;
        return timestamp;
    }

    /**
     * Extracts the (UNIX) timestamp from a mini hex id.
     * 
     * @param idMinihex
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestampMini(String idMinihex) {
        long idMini = Long.parseLong(idMinihex, 16);
        return extractTimestampMini(idMini);
    }

    /**
     * Extracts the (UNIX) timestamp from a mini ASCII id (radix
     * {@link Character#MAX_RADIX}).
     * 
     * @param idMiniAscii
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestampMiniAscii(String idMiniAscii) {
        long idMini = Long.parseLong(idMiniAscii, Character.MAX_RADIX);
        return extractTimestampMini(idMini);
    }

    /**
     * Generates a mini id (48 bit long, node is not accounted).
     * 
     * Format: <41-bit: timestamp><0-bit: node id><7 bit: sequence number>
     * 
     * Where timestamp is in millisec, minus the epoch.
     * 
     * @return
     */
    synchronized public long generateIdMini() {
        long timestamp = System.currentTimeMillis();
        long sequence = 0;
        if (timestamp == this.lastTimestampMillisec.get()) {
            // increase sequence
            sequence = this.sequenceMillisec.incrementAndGet();
            if (sequence > MAX_SEQUENCE_MINI) {
                // reset sequence
                this.sequenceMillisec.set(0);
                waitTillNextMillisec(timestamp);
                return generateIdMini();
            }
        } else {
            // reset sequence
            this.sequenceMillisec.set(sequence);
            this.lastTimestampMillisec.set(timestamp);
        }
        timestamp = (timestamp - TIMESTAMP_EPOCH) & MASK_TIMESTAMP_MINI;
        long result = timestamp << SHIFT_TIMESTAMP_MINI | templateMini
                | (sequence & MASK_SEQUENCE_MINI);
        return result;
    }

    /**
     * Generate a mini id as hex string.
     * 
     * @return
     */
    public String generateIdMiniHex() {
        long id = generateIdMini();
        return Long.toHexString(id).toUpperCase();
    }

    /**
     * Generate a mini id as ASCII string (radix {@link Character#MAX_RADIX}).
     * 
     * @return
     */
    public String generateIdMiniAscii() {
        long id = generateIdMini();
        return Long.toString(id, Character.MAX_RADIX).toUpperCase();
    }

    /* mini id */

    /* 64-bit id */
    /**
     * Extracts the (UNIX) timestamp from a 64-bit id.
     * 
     * @param id64
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestamp64(long id64) {
        // use >>> for unsigned number
        long timestamp = (id64 >>> SHIFT_TIMESTAMP_64) + TIMESTAMP_EPOCH;
        return timestamp;
    }

    /**
     * Extracts the (UNIX) timestamp from a 64-bit hex id.
     * 
     * @param id64hex
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestamp64(String id64hex) {
        long id64 = Long.parseLong(id64hex, 16);
        return extractTimestamp64(id64);
    }

    /**
     * Extracts the (UNIX) timestamp from a 64-bit ASCII id (radix
     * {@link Character#MAX_RADIX}).
     * 
     * @param id64ascii
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestamp64Ascii(String id64ascii) {
        long id64 = Long.parseLong(id64ascii, Character.MAX_RADIX);
        return extractTimestamp64(id64);
    }

    /**
     * Generates a 64-bit id.
     * 
     * Format: <41-bit: timestamp><10-bit: node id><13 bit: sequence number>
     * 
     * Where timestamp is in millisec, minus the epoch.
     * 
     * @return
     */
    synchronized public long generateId64() {
        long timestamp = System.currentTimeMillis();
        long sequence = 0;
        if (timestamp == this.lastTimestampMillisec.get()) {
            // increase sequence
            sequence = this.sequenceMillisec.incrementAndGet();
            if (sequence > MAX_SEQUENCE_64) {
                // reset sequence
                this.sequenceMillisec.set(0);
                waitTillNextMillisec(timestamp);
                return generateId64();
            }
        } else {
            // reset sequence
            this.sequenceMillisec.set(sequence);
            this.lastTimestampMillisec.set(timestamp);
        }
        timestamp = (timestamp - TIMESTAMP_EPOCH) & MASK_TIMESTAMP_64;
        long result = timestamp << SHIFT_TIMESTAMP_64 | template64 | (sequence & MASK_SEQUENCE_64);
        return result;
    }

    /**
     * Generate a 64-bit id as hex string.
     * 
     * @return
     */
    public String generateId64Hex() {
        long id = generateId64();
        return Long.toHexString(id).toUpperCase();
    }

    /**
     * Generate a 64-bit id as ASCII string (radix {@link Character#MAX_RADIX}).
     * 
     * @return
     */
    public String generateId64Ascii() {
        long id = generateId64();
        return Long.toString(id, Character.MAX_RADIX).toUpperCase();
    }

    /* 64-bit id */

    /* 128-bit id */

    /**
     * Generates a 128-bit id.
     * 
     * Format: <64-bit: timestamp><48-bit: node id><16 bit: sequence number>
     * 
     * Where timestamp is in millisec.
     * 
     * @return
     */
    synchronized public BigInteger generateId128() {
        long timestamp = System.currentTimeMillis();
        long sequence = 0;
        if (timestamp == this.lastTimestampMillisec.get()) {
            // increase sequence
            sequence = this.sequenceMillisec.incrementAndGet();
            if (sequence > MAX_SEQUENCE_128) {
                // reset sequence
                this.sequenceMillisec.set(0);
                waitTillNextMillisec(timestamp);
                return generateId128();
            }
        } else {
            // reset sequence
            this.sequenceMillisec.set(sequence);
            this.lastTimestampMillisec.set(timestamp);
        }

        BigInteger biSequence = BigInteger.valueOf(sequence & MASK_SEQUENCE_128);
        BigInteger biResult = BigInteger.valueOf(timestamp);
        biResult = biResult.shiftLeft((int) SHIFT_TIMESTAMP_128);
        biResult = biResult.or(template128).or(biSequence);
        return biResult;
    }

    /**
     * Generate a 128-bit id as hex string.
     * 
     * @return
     */
    public String generateId128Hex() {
        BigInteger id = generateId128();
        return id.toString(16).toUpperCase();
    }

    /**
     * Generate a 128-bit id as ASCII string (radix {@link Character#MAX_RADIX}
     * ).
     * 
     * @return
     */
    public String generateId128Ascii() {
        BigInteger id = generateId128();
        return id.toString(Character.MAX_RADIX).toUpperCase();
    }

    /**
     * Extracts the (UNIX) timestamp from a 128-bit id.
     * 
     * @param id128
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestamp128(BigInteger id128) {
        BigInteger result = id128.shiftRight((int) SHIFT_TIMESTAMP_128);
        return result.longValue();
    }

    /**
     * Extracts the (UNIX) timestamp from a 128-bit hex id.
     * 
     * @param id128hex
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestamp128(String id128hex) {
        BigInteger id128 = new BigInteger(id128hex, 16);
        return extractTimestamp128(id128);
    }

    /**
     * Extracts the (UNIX) timestamp from a 128-bit ASCII id (radix
     * {@link Character#MAX_RADIX}).
     * 
     * @param id128ascii
     * @return the UNIX timestamp (milliseconds)
     */
    public static long extractTimestamp128Ascii(String id128ascii) {
        BigInteger id128 = new BigInteger(id128ascii, Character.MAX_RADIX);
        return extractTimestamp128(id128);
    }
}
