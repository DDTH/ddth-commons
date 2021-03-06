package com.github.ddth.commons.test.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.ddth.commons.utils.DateFormatUtils;
import com.github.ddth.commons.utils.JacksonUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class JacksonUtilsTest extends TestCase {

    public JacksonUtilsTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(JacksonUtilsTest.class);
    }

    private JsonNode COMPANY;
    private final String COMPANY_NAME = "Monster Corp.";
    private final int COMPANY_YEAR = 2003;

    private final String EMPLOYEE1_FIRST_NAME = "Mike";
    private final String EMPLOYEE1_LAST_NAME = "Wazowski";
    private final String EMPLOYEE1_EMAIL = "mike.wazowski@monster.com";
    private final int EMPLOYEE1_AGE = 29;
    private final static String EMPLOYEE1_JOIN_DATE = "Apr 29, 2011";
    private final static String EMPLOYEE1_JOIN_DATE_DF = "MMM d, yyyy";

    private final String EMPLOYEE2_FIRST_NAME = "Sulley";
    private final String EMPLOYEE2_LAST_NAME = "Sullivan";
    private final String EMPLOYEE2_EMAIL = "sulley.sullivan@monster.com";
    private final int EMPLOYEE2_AGE = 30;
    private final static String EMPLOYEE2_JOIN_DATE = "2012-03-01 01:30:00 PM";
    private final static String EMPLOYEE2_JOIN_DATE_DF = "yyyy-MM-dd hh:mm:ss a";

    @Before
    public void setUp() {
        Map<String, Object> company = new HashMap<String, Object>();
        company.put("name", COMPANY_NAME);
        company.put("year", "2003");

        List<Map<String, Object>> employees = new ArrayList<Map<String, Object>>();
        company.put("employees", employees);

        Map<String, Object> employee1 = new HashMap<String, Object>();
        employee1.put("first_name", EMPLOYEE1_FIRST_NAME);
        employee1.put("last_name", EMPLOYEE1_LAST_NAME);
        employee1.put("email", EMPLOYEE1_EMAIL);
        employee1.put("age", EMPLOYEE1_AGE);
        employee1.put("join_date", EMPLOYEE1_JOIN_DATE);
        employees.add(employee1);

        Map<String, Object> employee2 = new HashMap<String, Object>();
        employee2.put("first_name", EMPLOYEE2_FIRST_NAME);
        employee2.put("last_name", EMPLOYEE2_LAST_NAME);
        employee2.put("email", EMPLOYEE2_EMAIL);
        employee2.put("age", EMPLOYEE2_AGE);
        employee2.put("join_date", EMPLOYEE2_JOIN_DATE);
        employees.add(employee2);

        COMPANY = JacksonUtils.toJson(company);
    }

    @After
    public void tearDown() {
    }

    @org.junit.Test
    public void testGetValue() {
        String companyName = JacksonUtils.getValue(COMPANY, "name", String.class);
        assertEquals(COMPANY_NAME, companyName);

        Long companyYear = JacksonUtils.getValue(COMPANY, "year", Long.class);
        assertEquals(COMPANY_YEAR, companyYear.intValue());

        Integer employee1Age = JacksonUtils.getValue(COMPANY, "employees.[0].age", Integer.class);
        assertEquals(EMPLOYEE1_AGE, employee1Age.intValue());

        Object employee2Email = JacksonUtils.getValue(COMPANY, "employees[1].email");
        assertTrue(employee2Email instanceof TextNode);
        assertEquals(EMPLOYEE2_EMAIL, ((TextNode) employee2Email).asText());

        Date employee1JoinDate = JacksonUtils.getDate(COMPANY, "employees[0].join_date",
                EMPLOYEE1_JOIN_DATE_DF);
        assertNotNull(employee1JoinDate);
        assertEquals(DateFormatUtils.toString(employee1JoinDate, EMPLOYEE1_JOIN_DATE_DF),
                EMPLOYEE1_JOIN_DATE);

        Date employee2JoinDate = JacksonUtils.getDate(COMPANY, "employees[1].join_date",
                EMPLOYEE2_JOIN_DATE_DF);
        assertNotNull(employee2JoinDate);
        assertEquals(DateFormatUtils.toString(employee2JoinDate, EMPLOYEE2_JOIN_DATE_DF),
                EMPLOYEE2_JOIN_DATE);

        Throwable t = null;
        try {
            JacksonUtils.getValue(COMPANY, "employees[-1]", Map.class);
        } catch (IllegalArgumentException e) {
            t = e.getCause();
        } catch (IndexOutOfBoundsException e) {
            t = e;
        }
        assertNotNull(t);
    }

    @org.junit.Test
    public void testSetValue() {
        JsonNode notExists = JacksonUtils.getValue(COMPANY, "employees.[0].not_found");
        assertNull(notExists);

        JacksonUtils.setValue(COMPANY, "employees[0].not_found", "not_found");
        JsonNode exists = JacksonUtils.getValue(COMPANY, "employees[0].not_found");
        assertTrue(exists instanceof TextNode);
        assertEquals("not_found", ((TextNode) exists).asText());
    }

    @org.junit.Test
    public void testSetValue2() {
        JacksonUtils.setValue(COMPANY, "employees[0]", null);
        JsonNode nullNow = JacksonUtils.getValue(COMPANY, "employees[0]");
        assertTrue(
                nullNow == null || nullNow instanceof NullNode || nullNow instanceof MissingNode);
    }

    @org.junit.Test
    public void testSetValue3() {
        Throwable t = null;
        try {
            JacksonUtils.getValue(COMPANY, "employees[2].email");
        } catch (IllegalArgumentException e) {
            t = e.getCause();
        } catch (IndexOutOfBoundsException e) {
            t = e;
        }
        assertNotNull(t);

        JacksonUtils.setValue(COMPANY, "employees[2].email", "email3@monster.com", true);

        String employee3Email = JacksonUtils.getValue(COMPANY, "employees[2].email", String.class);
        assertEquals("email3@monster.com", employee3Email);
    }

    @org.junit.Test
    public void testDeleteValue() {
        JacksonUtils.deleteValue(COMPANY, "employees[0]");
        List<?> employees = JacksonUtils.getValue(COMPANY, "employees", List.class);
        assertEquals(1, employees.size());
    }

    @org.junit.Test
    public void testDeleteValue2() {
        String employee1Email = JacksonUtils.getValue(COMPANY, "employees.[0].email", String.class);
        assertNotNull(employee1Email);

        JacksonUtils.deleteValue(COMPANY, "employees[0].email");
        assertNull(JacksonUtils.getValue(COMPANY, "employees.[0].email"));
    }
}
