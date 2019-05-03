package com.github.ddth.commons.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.github.ddth.commons.utils.MapUtils;

public class ClassB {
    public ClassA fieldA = new ClassA();
    protected int[] fieldArrInt = { 1, 2, 3, 4 };
    private Map<String, Object> fieldMap = MapUtils.createMap("a", "1", "b", 2, "c", true);
    List<Object> fieldList = new ArrayList<>();
    {
        fieldList.add("one");
        fieldList.add(2);
        fieldList.add(3.4);
        fieldList.add(true);
    }

    public ClassA getFieldA() {
        return fieldA;
    }

    public ClassB setFieldA(ClassA fieldA) {
        this.fieldA = fieldA;
        return this;
    }

    public int[] getFieldArrInt() {
        return fieldArrInt;
    }

    public ClassB setFieldArrInt(int[] fieldArrInt) {
        this.fieldArrInt = fieldArrInt;
        return this;
    }

    public Map<String, Object> getFieldMap() {
        return fieldMap;
    }

    public ClassB setFieldMap(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
        return this;
    }

    public List<Object> getFieldList() {
        return fieldList;
    }

    public ClassB setFieldList(List<Object> fieldList) {
        this.fieldList = fieldList;
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClassB)) {
            return false;
        }
        ClassB that = (ClassB) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(this.fieldArrInt, that.fieldArrInt).append(this.fieldA, that.fieldA)
                .append(this.fieldMap, that.fieldMap).append(this.fieldList, that.fieldList);
        return eb.isEquals();
    }
}
