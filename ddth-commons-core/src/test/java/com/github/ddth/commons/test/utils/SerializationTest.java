package com.github.ddth.commons.test.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;

import com.github.ddth.commons.utils.SerializationUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/*
 * mvn test -DskipTests=false -Dtest=com.github.ddth.commons.test.utils.SerializationTest
 */
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

    @SuppressWarnings("serial")
    @org.junit.Test
    public void testJson1() {
        String json;

        Object objNull = null;
        json = SerializationUtils.toJsonString(objNull);
        assertEquals("null", json);

        Map<String, Object> emptyMap = new HashMap<String, Object>();
        json = SerializationUtils.toJsonString(emptyMap);
        assertEquals("{}", json);

        Map<String, Object> singleMap = new HashMap<String, Object>() {
            {
                put("key", "value");
            }
        };
        json = SerializationUtils.toJsonString(singleMap);
        assertEquals("{\"key\":\"value\"}", json);

        List<Object> emptyList = new ArrayList<Object>();
        json = SerializationUtils.toJsonString(emptyList);
        assertEquals("[]", json);

        List<Object> nonEmptyList = new ArrayList<Object>() {
            {
                add(1);
                add("2");
                add(3.0);
            }
        };
        json = SerializationUtils.toJsonString(nonEmptyList);
        assertEquals("[1,\"2\",3.0]", json);

        int[] emptyArray = new int[0];
        json = SerializationUtils.toJsonString(emptyArray);
        assertEquals("[]", json);

        int[] nonEmptyArray = { 1, 2, 3, 4 };
        json = SerializationUtils.toJsonString(nonEmptyArray);
        assertEquals("[1,2,3,4]", json);

        Set<String> emptySet = new HashSet<String>();
        json = SerializationUtils.toJsonString(emptySet);
        assertEquals("[]", json);

        Set<String> singleSet = new HashSet<String>() {
            {
                add("value");
            }
        };
        json = SerializationUtils.toJsonString(singleSet);
        assertEquals("[\"value\"]", json);
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testJson2() {
        String json = SerializationUtils.toJsonString(COMPANY);
        assertNotNull(json);

        Map<String, Object> company = SerializationUtils.fromJsonString(json, Map.class);
        assertNotNull(company);

        // two objects must be content-equaled, but not instances.
        assertEquals(company, COMPANY);
        assertFalse(company == COMPANY);
    }

    @org.junit.Test
    public void testKryo1() {
        byte[] bytearr;
        Object obj;

        Object objNull = null;
        bytearr = SerializationUtils.toByteArrayKryo(objNull);
        obj = SerializationUtils.fromByteArrayKryo(bytearr);
        assertNull(obj);

        Map<String, Object> emptyMap = new HashMap<String, Object>();
        bytearr = SerializationUtils.toByteArrayKryo(emptyMap);
        obj = SerializationUtils.fromByteArrayKryo(bytearr);
        assertTrue(obj instanceof Map);
        obj = SerializationUtils.fromByteArrayKryo(bytearr, Map.class);
        assertTrue(obj instanceof Map);

        List<Object> emptyList = new ArrayList<Object>();
        bytearr = SerializationUtils.toByteArrayKryo(emptyList);
        obj = SerializationUtils.fromByteArrayKryo(bytearr);
        assertTrue(obj instanceof List);
        obj = SerializationUtils.fromByteArrayKryo(bytearr, List.class);
        assertTrue(obj instanceof List);

        int[] emptyArray = new int[0];
        bytearr = SerializationUtils.toByteArrayKryo(emptyArray);
        obj = SerializationUtils.fromByteArrayKryo(bytearr);
        assertTrue(obj instanceof int[]);
        obj = SerializationUtils.fromByteArrayKryo(bytearr, int[].class);
        assertTrue(obj instanceof int[]);

        Set<String> emptySet = new HashSet<String>();
        bytearr = SerializationUtils.toByteArrayKryo(emptySet);
        obj = SerializationUtils.fromByteArrayKryo(bytearr);
        assertTrue(obj instanceof Set);
        obj = SerializationUtils.fromByteArrayKryo(bytearr, Set.class);
        assertTrue(obj instanceof Set);
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testKryo2() {
        byte[] bytearr = SerializationUtils.toByteArrayKryo(COMPANY);
        Map<String, Object> company = SerializationUtils.fromByteArrayKryo(bytearr, Map.class);
        assertNotNull(company);

        // two objects must be content-equaled, but not instances.
        assertEquals(company, COMPANY);
        assertFalse(company == COMPANY);
    }

    @org.junit.Test
    public void testFst1() {
        byte[] bytearr;
        Object obj;

        Object objNull = null;
        bytearr = SerializationUtils.toByteArrayFst(objNull);
        obj = SerializationUtils.fromByteArrayFst(bytearr);
        assertNull(obj);

        Map<String, Object> emptyMap = new HashMap<String, Object>();
        bytearr = SerializationUtils.toByteArrayFst(emptyMap);
        obj = SerializationUtils.fromByteArrayFst(bytearr);
        assertTrue(obj instanceof Map);
        obj = SerializationUtils.fromByteArrayFst(bytearr, Map.class);
        assertTrue(obj instanceof Map);

        List<Object> emptyList = new ArrayList<Object>();
        bytearr = SerializationUtils.toByteArrayFst(emptyList);
        obj = SerializationUtils.fromByteArrayFst(bytearr);
        assertTrue(obj instanceof List);
        obj = SerializationUtils.fromByteArrayFst(bytearr, List.class);
        assertTrue(obj instanceof List);

        int[] emptyArray = new int[0];
        bytearr = SerializationUtils.toByteArrayFst(emptyArray);
        obj = SerializationUtils.fromByteArrayFst(bytearr);
        assertTrue(obj instanceof int[]);
        obj = SerializationUtils.fromByteArrayFst(bytearr, int[].class);
        assertTrue(obj instanceof int[]);

        Set<String> emptySet = new HashSet<String>();
        bytearr = SerializationUtils.toByteArrayFst(emptySet);
        obj = SerializationUtils.fromByteArrayFst(bytearr);
        assertTrue(obj instanceof Set);
        obj = SerializationUtils.fromByteArrayFst(bytearr, Set.class);
        assertTrue(obj instanceof Set);
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testFst2() {
        byte[] bytearr = SerializationUtils.toByteArrayFst(COMPANY);
        Map<String, Object> company = SerializationUtils.fromByteArrayFst(bytearr, Map.class);
        assertNotNull(company);

        // two objects must be content-equaled, but not instances.
        assertEquals(company, COMPANY);
        assertFalse(company == COMPANY);
    }

}
