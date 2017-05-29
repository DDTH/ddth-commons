package com.github.ddth.commons.test.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.ddth.commons.utils.SerializationUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SerializationJsonNodeTest extends TestCase {

    public SerializationJsonNodeTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(SerializationJsonNodeTest.class);
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
    public void testJsonNode1() {
        JsonNode json;

        Object objNull = null;
        json = SerializationUtils.toJson(objNull);
        assertTrue(json instanceof NullNode);

        Map<String, Object> emptyMap = new HashMap<String, Object>();
        json = SerializationUtils.toJson(emptyMap);
        assertTrue(json instanceof ObjectNode);
        assertEquals(0, json.size());

        Map<String, Object> singleMap = new HashMap<String, Object>() {
            {
                put("key", "value");
            }
        };
        json = SerializationUtils.toJson(singleMap);
        assertTrue(json instanceof ObjectNode);
        assertEquals(1, json.size());
        assertEquals("value", json.get("key").asText());

        List<Object> emptyList = new ArrayList<Object>();
        json = SerializationUtils.toJson(emptyList);
        assertTrue(json instanceof ArrayNode);
        assertEquals(0, json.size());

        List<Object> nonEmptyList = new ArrayList<Object>() {
            {
                add(1);
                add("2");
                add(3.0);
            }
        };
        json = SerializationUtils.toJson(nonEmptyList);
        assertTrue(json instanceof ArrayNode);
        assertEquals(3, json.size());
        assertEquals(1, json.get(0).asInt());
        assertEquals("2", json.get(1).asText());
        assertEquals(3.0, json.get(2).asDouble());

        int[] emptyArray = new int[0];
        json = SerializationUtils.toJson(emptyArray);
        assertTrue(json instanceof ArrayNode);
        assertEquals(0, json.size());

        int[] nonEmptyArray = { 1, 2, 3, 4 };
        json = SerializationUtils.toJson(nonEmptyArray);
        assertTrue(json instanceof ArrayNode);
        assertEquals(4, json.size());
        for (int i = 0; i < nonEmptyArray.length; i++) {
            assertEquals(nonEmptyArray[i], json.get(i).asInt());
        }

        Set<String> emptySet = new HashSet<String>();
        json = SerializationUtils.toJson(emptySet);
        assertTrue(json instanceof ArrayNode);
        assertEquals(0, json.size());

        Set<String> singleSet = new HashSet<String>() {
            {
                add("value");
            }
        };
        json = SerializationUtils.toJson(singleSet);
        assertTrue(json instanceof ArrayNode);
        assertEquals(1, json.size());
        assertEquals("value", json.get(0).asText());
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void testJsonNode2() {
        JsonNode json = SerializationUtils.toJson(COMPANY);
        assertTrue(json instanceof ObjectNode);

        Map<String, Object> company = SerializationUtils.fromJson(json, Map.class);
        assertNotNull(company);

        assertEquals(company, COMPANY);
        assertFalse(company == COMPANY);
    }

}
