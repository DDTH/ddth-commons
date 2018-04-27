package com.github.ddth.commons.test.utils;

import com.github.ddth.commons.utils.Ipv4Utils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class Ipv4UtilsTest extends TestCase {

    public Ipv4UtilsTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(Ipv4UtilsTest.class);
    }

    @org.junit.Test
    public void testIsValidIp() {
        assertTrue(Ipv4Utils.isValidIp("192.168.0.1"));
        assertFalse(Ipv4Utils.isValidIp("192.168.0.257"));
    }

    @org.junit.Test
    public void testIpToLongToIp() {
        String ip = "192.168.0.1";
        long ipLong = Ipv4Utils.ipToLong(ip);
        String ipStr = Ipv4Utils.longToIp(ipLong);
        assertEquals(ip, ipStr);
    }

    @org.junit.Test
    public void testBelongToSubnet() {
        assertTrue(Ipv4Utils.isBelongToSubnet("192.168.0.1", "192.168.0.0/24"));
        assertTrue(Ipv4Utils.isBelongToSubnet("192.168.0.10", "192.168.0.5-100"));
        assertFalse(Ipv4Utils.isBelongToSubnet("192.168.0.4", "192.168.0.5-100"));
        assertFalse(Ipv4Utils.isBelongToSubnet("192.168.0.101", "192.168.0.5-100"));
    }
}
