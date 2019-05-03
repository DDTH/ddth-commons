package com.github.ddth.commons.test;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Employee {
    public String name;
    protected int yob;
    private int[] workHours;
    List<Object> kpi;
    Map<String, Object> options;

    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    public int getYob() {
        return yob;
    }

    public Employee setYob(int yob) {
        this.yob = yob;
        return this;
    }

    public int[] getWorkHours() {
        return workHours;
    }

    public Employee setWorkHours(int[] workHours) {
        this.workHours = workHours;
        return this;
    }

    public List<Object> getKpi() {
        return kpi;
    }

    public Employee setKpi(List<Object> kpi) {
        this.kpi = kpi;
        return this;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public Employee setOptions(Map<String, Object> options) {
        this.options = options;
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Employee)) {
            return false;
        }
        Employee that = (Employee) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(this.workHours, that.workHours).append(this.yob, that.yob)
                .append(this.kpi, that.kpi).append(this.name, that.name)
                .append(this.options, that.options);
        return eb.isEquals();
    }
}
