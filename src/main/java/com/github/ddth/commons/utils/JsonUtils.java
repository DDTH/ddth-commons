package com.github.ddth.commons.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Serialize Java object to JSON-string and vice versa.
 * 
 * <p>
 * Note: this class is deprecated as of 0.2.0 and will be removed in future
 * releases. Use {@link SerializationUtils} instead.
 * </p>
 * 
 * <p>
 * Many times you just want to serialize your Java object to JSON-string and
 * vice versa, without touching any JsonNode object. This utility class provides
 * methods to do just that.
 * </p>
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
@Deprecated
public class JsonUtils {

    private final static ObjectMapper mapper = new ObjectMapper();

    /**
     * Serializes an object to Json string.
     * 
     * @param obj
     * @return
     */
    public static String toJsonString(Object obj) {
        try {
            return obj != null ? mapper.writeValueAsString(obj) : null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes a Json string.
     * 
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T fromJsonString(String jsonString, Class<T> clazz) {
        try {
            return jsonString != null ? mapper.readValue(jsonString, clazz) : null;
        } catch (JsonParseException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserializes a Json string.
     * 
     * @param jsonString
     * @return
     */
    public static Object fromJsonString(String jsonString) {
        return fromJsonString(jsonString, Object.class);
    }
}
