package com.github.ddth.commons.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;

public class SerializationTest extends TestCase {

    public SerializationTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(SerializationTest.class);
    }

    private Map<String, Object> COMPANY;
    private final String COMPANY_NAME = "Monster Corp.";
    private final int COMPANY_YEAR = 2003;

    private final String EMPLOYEE1_FIRST_NAME = "Mike";
    private final String EMPLOYEE1_LAST_NAME = "Wazowski";
    private final String EMPLOYEE1_EMAIL = "mike.wazowski@monster.com";
    private final int EMPLOYEE1_AGE = 29;

    private final String EMPLOYEE2_FIRST_NAME = "Sulley";
    private final String EMPLOYEE2_LAST_NAME = "Sullivan";
    private final String EMPLOYEE2_EMAIL = "sulley.sullivan@monster.com";
    private final int EMPLOYEE2_AGE = 30;

    @Before
    public void setUp() {
        Map<String, Object> company = new HashMap<String, Object>();
        company.put("name", COMPANY_NAME);
        company.put("year", COMPANY_YEAR);

        List<Map<String, Object>> employees = new ArrayList<Map<String, Object>>();
        company.put("employees", employees);

        Map<String, Object> employee1 = new HashMap<String, Object>();
        employee1.put("first_name", EMPLOYEE1_FIRST_NAME);
        employee1.put("last_name", EMPLOYEE1_LAST_NAME);
        employee1.put("email", EMPLOYEE1_EMAIL);
        employee1.put("age", EMPLOYEE1_AGE);
        employees.add(employee1);

        Map<String, Object> employee2 = new HashMap<String, Object>();
        employee2.put("first_name", EMPLOYEE2_FIRST_NAME);
        employee2.put("last_name", EMPLOYEE2_LAST_NAME);
        employee2.put("email", EMPLOYEE2_EMAIL);
        employee2.put("age", EMPLOYEE2_AGE);
        employees.add(employee2);

        COMPANY = company;
    }

    @After
    public void tearDown() {
    }

    @org.junit.Test
    public void testJson1() {
        String json;

        Object objNull = null;
        json = SerializationUtils.toJsonString(objNull);
        assertEquals("null", json);

        Map<String, Object> emptyMap = new HashMap<String, Object>();
        json = SerializationUtils.toJsonString(emptyMap);
        assertEquals("{}", json);

        List<Object> emptyList = new ArrayList<Object>();
        json = SerializationUtils.toJsonString(emptyList);
        assertEquals("[]", json);

        int[] emptyArray = new int[0];
        json = SerializationUtils.toJsonString(emptyArray);
        assertEquals("[]", json);

        Set<String> emptySet = new HashSet<String>();
        json = SerializationUtils.toJsonString(emptySet);
        assertEquals("[]", json);
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testJson2() {
        String json = SerializationUtils.toJsonString(COMPANY);
        assertNotNull(json);

        Map<String, Object> company = SerializationUtils.fromJsonString(json, Map.class);
        assertNotNull(company);

        assertEquals(company, COMPANY);
        assertFalse(company == COMPANY);
    }

    @org.junit.Test
    public void testBinary1() {
        byte[] bytearr;
        Object obj;

        Object objNull = null;
        bytearr = SerializationUtils.toByteArray(objNull);
        obj = SerializationUtils.fromByteArray(bytearr);
        assertNull(obj);

        Map<String, Object> emptyMap = new HashMap<String, Object>();
        bytearr = SerializationUtils.toByteArray(emptyMap);
        obj = SerializationUtils.fromByteArray(bytearr);
        assertTrue(obj instanceof Map);

        List<Object> emptyList = new ArrayList<Object>();
        bytearr = SerializationUtils.toByteArray(emptyList);
        obj = SerializationUtils.fromByteArray(bytearr);
        assertTrue(obj instanceof List);

        int[] emptyArray = new int[0];
        bytearr = SerializationUtils.toByteArray(emptyArray);
        obj = SerializationUtils.fromByteArray(bytearr);
        assertTrue(obj instanceof int[]);

        Set<String> emptySet = new HashSet<String>();
        bytearr = SerializationUtils.toByteArray(emptySet);
        obj = SerializationUtils.fromByteArray(bytearr);
        assertTrue(obj instanceof Set);
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testBinary2() {
        byte[] bytearr = SerializationUtils.toByteArray(COMPANY);
        Map<String, Object> company = SerializationUtils.fromByteArray(bytearr, Map.class);
        assertNotNull(company);

        assertEquals(company, COMPANY);
        assertFalse(company == COMPANY);
    }

    public static void main(String[] args) {
        System.out.println(SerializationUtils.fromJsonString(""));
        System.out.println(SerializationUtils.fromJsonString("{}"));
        System.out.println(SerializationUtils.fromJsonString("{a}"));
    }
}
