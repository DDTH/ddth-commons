package com.github.ddth.commons.test.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

import com.github.ddth.commons.utils.DateFormatUtils;
import com.github.ddth.commons.utils.HashUtils;
import com.github.ddth.commons.utils.MapUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MapUtilsTest extends TestCase {

    public MapUtilsTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(MapUtilsTest.class);
    }

    private static Map<String, Object> EMPLOYEE;
    private final static String EMPLOYEE_FIRST_NAME = "Mike";
    private final static String EMPLOYEE_LAST_NAME = "Wazowski";
    private final static String EMPLOYEE_EMAIL = "mike.wazowski@monster.com";
    private final static int EMPLOYEE_AGE = 29;
    private final static String EMPLOYEE_JOIN_DATE = "Apr 29, 2011";
    private final static String EMPLOYEE_JOIN_DATE_DF = "MMM d, yyyy";

    @Before
    public void setUp() {
        Map<String, Object> employee = new HashMap<String, Object>();
        employee.put("first_name", EMPLOYEE_FIRST_NAME);
        employee.put("last_name", EMPLOYEE_LAST_NAME);
        employee.put("email", EMPLOYEE_EMAIL);
        employee.put("age", EMPLOYEE_AGE);
        employee.put("join_date", EMPLOYEE_JOIN_DATE);

        EMPLOYEE = employee;
    }

    @After
    public void tearDown() {
    }

    @org.junit.Test
    public void testCreateMap() {
        Map<String, Object> map = MapUtils.createMap("first_name", EMPLOYEE_FIRST_NAME, "last_name",
                EMPLOYEE_LAST_NAME, "email", EMPLOYEE_EMAIL, "age", EMPLOYEE_AGE, "join_date",
                EMPLOYEE_JOIN_DATE);
        assertNotNull(map);

        assertEquals(map, EMPLOYEE);
    }

    @org.junit.Test
    public void testChecksum() {
        Map<String, Object> map = MapUtils.createMap("first_name", EMPLOYEE_FIRST_NAME, "last_name",
                EMPLOYEE_LAST_NAME, "email", EMPLOYEE_EMAIL, "age", EMPLOYEE_AGE, "join_date",
                EMPLOYEE_JOIN_DATE);
        assertNotNull(map);
        assertEquals(HashUtils.checksum(map), HashUtils.checksum(EMPLOYEE));
        assertEquals(HashUtils.checksumCrc32(map), HashUtils.checksumCrc32(EMPLOYEE));
        assertEquals(HashUtils.checksumMd5(map), HashUtils.checksumMd5(EMPLOYEE));
        assertEquals(HashUtils.checksumMurmur3(map), HashUtils.checksumMurmur3(EMPLOYEE));
        assertEquals(HashUtils.checksumSha1(map), HashUtils.checksumSha1(EMPLOYEE));
        assertEquals(HashUtils.checksumSha256(map), HashUtils.checksumSha256(EMPLOYEE));
        assertEquals(HashUtils.checksumSha512(map), HashUtils.checksumSha512(EMPLOYEE));
    }

    @org.junit.Test
    public void testGetValue() {
        Number employeeAge = MapUtils.getValue(EMPLOYEE, "age", Number.class);
        assertEquals(EMPLOYEE_AGE, employeeAge.intValue());

        Object employeeEmail = MapUtils.getValue(EMPLOYEE, "email", String.class);
        assertEquals(EMPLOYEE_EMAIL, employeeEmail.toString());

        Date employeeJoinDate = MapUtils.getDate(EMPLOYEE, "join_date", EMPLOYEE_JOIN_DATE_DF);
        assertNotNull(employeeJoinDate);
        assertEquals(DateFormatUtils.toString(employeeJoinDate, EMPLOYEE_JOIN_DATE_DF),
                EMPLOYEE_JOIN_DATE);
    }

}
