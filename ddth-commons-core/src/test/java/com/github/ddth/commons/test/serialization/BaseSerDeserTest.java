package com.github.ddth.commons.test.serialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.github.ddth.commons.serialization.ISerDeser;
import com.github.ddth.commons.test.ClassA;
import com.github.ddth.commons.test.ClassB;
import com.github.ddth.commons.test.Employee;

import junit.framework.TestCase;

public class BaseSerDeserTest extends TestCase {

    public BaseSerDeserTest(String testName) {
        super(testName);
    }

    private Map<String, Object> COMPANY;

    @Before
    public void setUp() {
        COMPANY = new HashMap<>();
        COMPANY.put("name", "Monster Corp.");
        COMPANY.put("year", 2019);
        COMPANY.put("active", true);
        COMPANY.put("A", new ClassA());
        COMPANY.put("B", new ClassB());

        Employee[] seniorEmployees = new Employee[2];
        {
            COMPANY.put("semployees", seniorEmployees);
            for (int i = 0; i < seniorEmployees.length; i++) {
                seniorEmployees[i] = new Employee();
                seniorEmployees[i].setName("Senior - " + i);
                seniorEmployees[i].setYob(2009 + i);
                seniorEmployees[i].setWorkHours(new int[] { 2, 4, 6, 8, i });
                List<Object> kpi = new ArrayList<>();
                seniorEmployees[i].setKpi(kpi);
                {
                    kpi.add("1");
                    kpi.add(2);
                    kpi.add(3.4);
                    kpi.add(true);
                    kpi.add(new ClassA());
                }
                Map<String, Object> options = new HashMap<>();
                seniorEmployees[i].setOptions(options);
                {
                    options.put("1", "one");
                    options.put("2", 2);
                    options.put("3", 3.4);
                    options.put("true", true);
                    options.put("B", new ClassB());
                }
            }
        }

        List<Employee> employees = new ArrayList<>();
        {
            COMPANY.put("employees", employees);
            for (int i = 0; i < 5; i++) {
                Employee employee = new Employee();
                employees.add(employee);
                employee.setName("Employee - " + i);
                employee.setYob(1990 + i);
                employee.setWorkHours(new int[] { 1, 3, 5, 7, i });
                List<Object> kpi = new ArrayList<>();
                employee.setKpi(kpi);
                {
                    kpi.add("1");
                    kpi.add(2);
                    kpi.add(3.4);
                    kpi.add(true);
                    kpi.add(new ClassA());
                }
                Map<String, Object> options = new HashMap<>();
                employee.setOptions(options);
                {
                    options.put("1", "one");
                    options.put("2", 2);
                    options.put("3", 3.4);
                    options.put("true", true);
                    options.put("B", new ClassB());
                }
            }
        }
    }

    @After
    public void tearDown() {
    }

    @SuppressWarnings("unchecked")
    protected void doTest(ISerDeser serDeser) {
        byte[] data = serDeser.toBytes(COMPANY);
        Assert.assertNotNull(data);
        Map<String, Object> company = serDeser.fromBytes(data, Map.class);
        Assert.assertNotNull(company);

        String[] fields = { "name", "year", "active", "A", "B" };
        for (String field : fields) {
            Assert.assertEquals("Value of field [" + field + "] should be equal",
                    COMPANY.get(field), company.get(field));
        }
        Employee[] lhsSEmployees = (Employee[]) COMPANY.get("semployees");
        Employee[] rhsSEmployees = (Employee[]) company.get("semployees");
        Assert.assertArrayEquals(lhsSEmployees, rhsSEmployees);
        List<Employee> lhsEmployees = (List<Employee>) COMPANY.get("employees");
        List<Employee> rhsEmployees = (List<Employee>) company.get("employees");
        Assert.assertTrue(lhsEmployees.equals(rhsEmployees));
    }
}
