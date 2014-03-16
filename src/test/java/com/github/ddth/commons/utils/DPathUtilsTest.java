package com.github.ddth.commons.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;

public class DPathUtilsTest extends TestCase {

    public DPathUtilsTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(DPathUtilsTest.class);
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

        COMPANY = company;
    }

    @After
    public void tearDown() {
    }

    @org.junit.Test
    public void test1() {
        String companyName = DPathUtils.getValue(COMPANY, "name", String.class);
        assertEquals(COMPANY_NAME, companyName);

        Integer companyYear = DPathUtils.getValue(COMPANY, "year", Integer.class);
        assertEquals(COMPANY_YEAR, companyYear.intValue());
    }

    @SuppressWarnings("unchecked")
    @org.junit.Test
    public void test2() {
        Object user1 = DPathUtils.getValue(COMPANY, "employees.[0]");
        assertNotNull(user1);

        Map<String, Object> user2 = DPathUtils.getValue(COMPANY, "employees.[1]", Map.class);
        assertNotNull(user2);

        Object user3 = DPathUtils.getValue(COMPANY, "employees.[-1]", Map.class);
        assertNull(user3);

        String firstName1 = DPathUtils.getValue(COMPANY, "employees.[0].first_name", String.class);
        assertEquals(EMPLOYEE1_FIRST_NAME, firstName1);

        Long age2 = DPathUtils.getValue(COMPANY, "employees.[1].age", Long.class);
        assertEquals(EMPLOYEE2_AGE, age2.intValue());

        Object email3 = DPathUtils.getValue(COMPANY, "employees.[2].email");
        assertNull(email3);
    }
}
