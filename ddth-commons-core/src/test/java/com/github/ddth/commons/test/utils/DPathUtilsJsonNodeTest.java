package com.github.ddth.commons.test.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.ddth.commons.utils.DPathUtils;
import com.github.ddth.commons.utils.JacksonUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DPathUtilsJsonNodeTest extends TestCase {

    public DPathUtilsJsonNodeTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(DPathUtilsJsonNodeTest.class);
    }

    private JsonNode COMPANY;
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
        company.put("year", "2003");

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

        COMPANY = JacksonUtils.toJson(company);
    }

    @After
    public void tearDown() {
    }

    @org.junit.Test
    public void testSplitDpath() {
        {
            String dpath = "a.b.c.d";
            String[] paths = DPathUtils.splitDpath(dpath);
            assertEquals(4, paths.length);
            assertEquals("a", paths[0]);
            assertEquals("b", paths[1]);
            assertEquals("c", paths[2]);
            assertEquals("d", paths[3]);
        }

        {
            String dpath = "a.b.c.[i].d";
            String[] paths = DPathUtils.splitDpath(dpath);
            assertEquals(5, paths.length);
            assertEquals("a", paths[0]);
            assertEquals("b", paths[1]);
            assertEquals("c", paths[2]);
            assertEquals("[i]", paths[3]);
            assertEquals("d", paths[4]);
        }

        {
            String dpath = "a.b.c.d[i]";
            String[] paths = DPathUtils.splitDpath(dpath);
            assertEquals(5, paths.length);
            assertEquals("a", paths[0]);
            assertEquals("b", paths[1]);
            assertEquals("c", paths[2]);
            assertEquals("d", paths[3]);
            assertEquals("[i]", paths[4]);
        }

        {
            String dpath = "a.b.c[i].d";
            String[] paths = DPathUtils.splitDpath(dpath);
            assertEquals(5, paths.length);
            assertEquals("a", paths[0]);
            assertEquals("b", paths[1]);
            assertEquals("c", paths[2]);
            assertEquals("[i]", paths[3]);
            assertEquals("d", paths[4]);
        }

        {
            String dpath = "a.b.c.[i]d";
            String[] paths = DPathUtils.splitDpath(dpath);
            assertEquals(4, paths.length);
            assertEquals("a", paths[0]);
            assertEquals("b", paths[1]);
            assertEquals("c", paths[2]);
            assertEquals("[i]d", paths[3]);
        }

        {
            String dpath = "a.b.c.[i].[j].d";
            String[] paths = DPathUtils.splitDpath(dpath);
            assertEquals(6, paths.length);
            assertEquals("a", paths[0]);
            assertEquals("b", paths[1]);
            assertEquals("c", paths[2]);
            assertEquals("[i]", paths[3]);
            assertEquals("[j]", paths[4]);
            assertEquals("d", paths[5]);
        }

        {
            String dpath = "a.b.c.[i][j].d";
            String[] paths = DPathUtils.splitDpath(dpath);
            assertEquals(6, paths.length);
            assertEquals("a", paths[0]);
            assertEquals("b", paths[1]);
            assertEquals("c", paths[2]);
            assertEquals("[i]", paths[3]);
            assertEquals("[j]", paths[4]);
            assertEquals("d", paths[5]);
        }

        {
            String dpath = "a.b.c[i].[j].d";
            String[] paths = DPathUtils.splitDpath(dpath);
            assertEquals(6, paths.length);
            assertEquals("a", paths[0]);
            assertEquals("b", paths[1]);
            assertEquals("c", paths[2]);
            assertEquals("[i]", paths[3]);
            assertEquals("[j]", paths[4]);
            assertEquals("d", paths[5]);
        }

        {
            String dpath = "a.b.c[i][j].d";
            String[] paths = DPathUtils.splitDpath(dpath);
            assertEquals(6, paths.length);
            assertEquals("a", paths[0]);
            assertEquals("b", paths[1]);
            assertEquals("c", paths[2]);
            assertEquals("[i]", paths[3]);
            assertEquals("[j]", paths[4]);
            assertEquals("d", paths[5]);
        }
    }

    @SuppressWarnings("unused")
    @org.junit.Test
    public void testGetValue() {
        String companyName = DPathUtils.getValue(COMPANY, "name", String.class);
        assertEquals(COMPANY_NAME, companyName);

        Long companyYear = DPathUtils.getValue(COMPANY, "year", Long.class);
        assertEquals(COMPANY_YEAR, companyYear.intValue());

        Integer employee1Age = DPathUtils.getValue(COMPANY, "employees.[0].age", Integer.class);
        assertEquals(EMPLOYEE1_AGE, employee1Age.intValue());

        Object employee2Email = DPathUtils.getValue(COMPANY, "employees[1].email");
        assertTrue(employee2Email instanceof TextNode);
        assertEquals(EMPLOYEE2_EMAIL, ((TextNode) employee2Email).asText());

        Throwable t = null;
        try {
            Object user3 = DPathUtils.getValue(COMPANY, "employees[-1]", Map.class);
        } catch (IllegalArgumentException e) {
            t = e;
        } catch (IndexOutOfBoundsException e) {
            t = e;
        }
        assertNotNull(t);
    }

    @org.junit.Test
    public void testSetValue() {
        Object notExists = DPathUtils.getValue(COMPANY, "employees.[0].not_found");
        assertNull(notExists);

        DPathUtils.setValue(COMPANY, "employees[0].not_found", "not_found");
        Object exists = DPathUtils.getValue(COMPANY, "employees[0].not_found");
        assertTrue(exists instanceof TextNode);
        assertEquals("not_found", ((TextNode) exists).asText());
    }

    @org.junit.Test
    public void testSetValue2() {
        DPathUtils.setValue(COMPANY, "employees[0]", null);
        Object nullNow = DPathUtils.getValue(COMPANY, "employees[0]");
        assertTrue(
                nullNow == null || nullNow instanceof NullNode || nullNow instanceof MissingNode);
    }

    @SuppressWarnings("unused")
    @org.junit.Test
    public void testSetValue3() {
        Throwable t = null;
        try {
            Object email = DPathUtils.getValue(COMPANY, "employees[2].email");
        } catch (IllegalArgumentException e) {
            t = e;
        } catch (IndexOutOfBoundsException e) {
            t = e;
        }
        assertNotNull(t);

        DPathUtils.setValue(COMPANY, "employees[2].email", "email3@monster.com", true);

        String employee3Email = DPathUtils.getValue(COMPANY, "employees[2].email", String.class);
        assertEquals("email3@monster.com", employee3Email);
    }

    @org.junit.Test
    public void testDeleteValue() {
        DPathUtils.deleteValue(COMPANY, "employees[0]");
        List<?> employees = DPathUtils.getValue(COMPANY, "employees", List.class);
        assertEquals(1, employees.size());
    }

    @org.junit.Test
    public void testDeleteValue2() {
        String employee1Email = DPathUtils.getValue(COMPANY, "employees.[0].email", String.class);
        assertNotNull(employee1Email);

        DPathUtils.deleteValue(COMPANY, "employees[0].email");
        assertNull(DPathUtils.getValue(COMPANY, "employees.[0].email"));
    }
}
