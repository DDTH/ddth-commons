package com.github.ddth.commons.test;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class ClassA {
    public String fieldStr = "a string";
    protected int fieldInt = 103;
    private double fieldDouble = 19.81;
    boolean fieldBool = true;

    public String getFieldStr() {
        return fieldStr;
    }

    public ClassA setFieldStr(String fieldStr) {
        this.fieldStr = fieldStr;
        return this;
    }

    public int getFieldInt() {
        return fieldInt;
    }

    public ClassA setFieldInt(int fieldInt) {
        this.fieldInt = fieldInt;
        return this;
    }

    public double getFieldDouble() {
        return fieldDouble;
    }

    public ClassA setFieldDouble(double fieldDouble) {
        this.fieldDouble = fieldDouble;
        return this;
    }

    public boolean isFieldBool() {
        return fieldBool;
    }

    public ClassA setFieldBool(boolean fieldBool) {
        this.fieldBool = fieldBool;
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClassA)) {
            return false;
        }
        ClassA that = (ClassA) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(this.fieldBool, that.fieldBool).append(this.fieldDouble, that.fieldDouble)
                .append(this.fieldInt, that.fieldInt).append(this.fieldStr, that.fieldStr);
        return eb.isEquals();
    }
}
