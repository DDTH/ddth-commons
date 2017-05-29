package com.github.ddth.commons.utils;

import java.io.InputStream;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Utility class to work with Jackson's {@link JsonNode}.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.6.2
 */
public class JacksonUtils {

    /**
     * Serialize an object to {@link JsonNode}.
     * 
     * @param obj
     * @return
     */
    public static JsonNode toJson(Object obj) {
        return toJson(obj, null);
    }

    /**
     * Serialize an object to {@link JsonNode}, with a custom class loader.
     * 
     * @param obj
     * @param classLoader
     * @return
     */
    public static JsonNode toJson(Object obj, ClassLoader classLoader) {
        return SerializationUtils.toJson(obj, classLoader);
    }

    /**
     * Deserialize a {@link JsonNode}.
     * 
     * @param json
     * @return
     */
    public static Object fromJson(JsonNode json) {
        return fromJson(json, Object.class, null);
    }

    /**
     * Deserialize a {@link JsonNode}, with custom class loader.
     * 
     * @param json
     * @param classLoader
     * @return
     */
    public static Object fromJson(JsonNode json, ClassLoader classLoader) {
        return fromJson(json, Object.class, classLoader);
    }

    /**
     * Deserialize a {@link JsonNode}.
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(JsonNode json, Class<T> clazz) {
        return fromJson(json, clazz, null);
    }

    /**
     * Deserialize a {@link JsonNode}, with custom class loader.
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(JsonNode json, Class<T> clazz, ClassLoader classLoader) {
        return SerializationUtils.fromJson(json, clazz, classLoader);
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance.
     * 
     * @param source
     * @return
     */
    public static JsonNode readJson(String source) {
        return readJson(source, null);
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance, with a custom class loader.
     * 
     * @param source
     * @param classLoader
     * @return
     */
    public static JsonNode readJson(String source, ClassLoader classLoader) {
        return SerializationUtils.readJson(source, classLoader);
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance.
     * 
     * @param source
     * @return
     */
    public static JsonNode readJson(byte[] source) {
        return readJson(source, null);
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance, with a custom class loader.
     * 
     * @param source
     * @param classLoader
     * @return
     */
    public static JsonNode readJson(byte[] source, ClassLoader classLoader) {
        return SerializationUtils.readJson(source, classLoader);
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance.
     * 
     * @param source
     * @return
     */
    public static JsonNode readJson(InputStream source) {
        return readJson(source, null);
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance, with a custom class loader.
     * 
     * @param source
     * @param classLoader
     * @return
     */
    public static JsonNode readJson(InputStream source, ClassLoader classLoader) {
        return SerializationUtils.readJson(source, classLoader);
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance.
     * 
     * @param source
     * @return
     */
    public static JsonNode readJson(Reader source) {
        return readJson(source, null);
    }

    /**
     * Read a JSON string and parse to {@link JsonNode} instance, with a custom class loader.
     * 
     * @param source
     * @param classLoader
     * @return
     */
    public static JsonNode readJson(Reader source, ClassLoader classLoader) {
        return SerializationUtils.readJson(source, classLoader);
    }

    /**
     * Extract value from a {@link JsonNode}.
     * 
     * @param node
     * @param clazz
     * @return
     */
    public static <T> T asValue(JsonNode node, Class<T> clazz) {
        return node != null ? ValueUtils.convertValue(node, clazz) : null;
    }

    /**
     * Extract a date value from the target {@link JsonNode} using DPath expression. If the
     * extracted value is
     * a string, parse it as a {@link Date} using the specified date-time format.
     * 
     * @param node
     * @param dPath
     * @param dateTimeFormat
     *            see {@link SimpleDateFormat}
     * @return
     * @see DPathUtils
     */
    public static Date getDate(JsonNode node, String dPath, String dateTimeFormat) {
        return DPathUtils.getDate(node, dPath, dateTimeFormat);
    }

    /**
     * Extract a value from the target {@link JsonNode} using DPath expression (generic
     * version).
     * 
     * @param node
     * @param dPath
     * @param clazz
     * @return
     * @see DPathUtils
     */
    public static <T> T getValue(JsonNode node, String dPath, Class<T> clazz) {
        return DPathUtils.getValue(node, dPath, clazz);
    }

    /**
     * Extract a value from the target {@link JsonNode} using DPath expression.
     * 
     * @param node
     * @param dPath
     * @see DPathUtils
     */
    public static JsonNode getValue(JsonNode node, String dPath) {
        return DPathUtils.getValue(node, dPath);
    }

    /**
     * Set a value to the target {@link JsonNode} specified by DPath expression.
     * 
     * <p>
     * Note: intermediated nodes will be created if {@code createIntermediatePath} is true.
     * </p>
     * 
     * <p>
     * Note: if {@code value} is {@code null}, the item (specified by {@code dPath} will be removed.
     * </p>
     * 
     * @param node
     * @param dPath
     * @param value
     * @param createIntermediatePath
     * @see DPathUtils
     */
    public static void setValue(JsonNode node, String dPath, Object value,
            boolean createIntermediatePath) {
        DPathUtils.setValue(node, dPath, value, createIntermediatePath);
    }

    /**
     * Set a value to the target {@link JsonNode} specified by DPath expression.
     * 
     * <p>
     * Note: intermediated nodes will NOT be created.
     * </p>
     * 
     * <p>
     * Note: if {@code value} is {@code null}, the item (specified by {@code dPath} will be removed.
     * </p>
     * 
     * @param node
     * @param dPath
     * @param value
     * @see DPathUtils
     */
    public static void setValue(JsonNode node, String dPath, Object value) {
        DPathUtils.setValue(node, dPath, value, false);
    }

    /**
     * Delete a value from the target {@link JsonNode} specified by DPath expression.
     * 
     * @param node
     * @param dPath
     * @see DPathUtils
     */
    public static void deleteValue(JsonNode node, String dPath) {
        DPathUtils.deleteValue(node, dPath);
    }

    /*----------------------------------------------------------------------*/

    private static class MyClass {
        public int age = 0;
        public String name = "";
        public boolean gender = false;

        public int hashCode() {
            HashCodeBuilder hcb = new HashCodeBuilder();
            hcb.append(age).append(name).append(gender);
            return hcb.hashCode();
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> mapBase = new HashMap<String, Object>();
        mapBase.put("key1", "value1");
        mapBase.put("key2", 2);
        mapBase.put("key3", 3.0);
        mapBase.put("key4", true);
        mapBase.put("key5", new MyClass());

        JsonNode json = toJson(mapBase);
        System.out.println(json);
        Object obj = fromJson(json);
        System.out.println(obj.getClass());
    }
}
