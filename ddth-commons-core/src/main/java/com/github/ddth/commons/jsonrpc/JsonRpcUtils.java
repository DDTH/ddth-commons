package com.github.ddth.commons.jsonrpc;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.ddth.commons.utils.MapUtils;
import com.github.ddth.commons.utils.SerializationUtils;

/**
 * Json-RPC helper class.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.9.0
 */
public class JsonRpcUtils {
    public final static String FIELD_STATUS = "status";
    public final static String FIELD_MESSAGE = "message";
    public final static String FIELD_DATA = "data";

    public final static HttpJsonRpcClient httpJsonRpcClient = new HttpJsonRpcClient().init();

    /**
     * Build Json-RPC's response in JSON format.
     * 
     * <p>
     * Json-RPC response as the following format:
     * </p>
     * 
     * <pre>
     * {
     *     "status" : (int) response status/error code,
     *     "message": (string) response message,
     *     "data"   : (object) response data
     * }
     * </pre>
     * 
     * @param status
     * @param message
     * @param data
     * @return
     */
    public static JsonNode buildResponse(int status, String message, Object data) {
        return SerializationUtils.toJson(MapUtils.removeNulls(MapUtils.createMap(FIELD_STATUS,
                status, FIELD_MESSAGE, message, FIELD_DATA, data)));
    }

    /**
     * Build Json-RPC's response in JSON format.
     * 
     * @param status
     * @return
     * @see #buildResponse(int, String, Object)
     */
    public static JsonNode buildResponse(int status) {
        return buildResponse(status, null, null);
    }

    /**
     * Build Json-RPC's response in JSON format.
     * 
     * @param status
     * @param message
     * @return
     * @see #buildResponse(int, String, Object)
     */
    public static JsonNode buildResponse(int status, String message) {
        return buildResponse(status, message, null);
    }

    /**
     * Build Json-RPC's response in JSON format.
     * 
     * @param status
     * @param data
     * @return
     * @see #buildResponse(int, String, Object)
     */
    public static JsonNode buildResponse(int status, Object data) {
        return buildResponse(status, null, data);
    }

    /**
     * Perform a HTTP GET request.
     * 
     * @param url
     * @param headers
     * @param urlParams
     * @return
     */
    public static RequestResponse callHttpGet(String url, Map<String, Object> headers,
            Map<String, Object> urlParams) {
        return httpJsonRpcClient.doGet(url, headers, urlParams);
    }

    /**
     * Perform a HTTP POST request.
     * 
     * @param url
     * @param headers
     * @param urlParams
     * @param requestData
     * @return
     */
    public static RequestResponse callHttpPost(String url, Map<String, Object> headers,
            Map<String, Object> urlParams, Object requestData) {
        return httpJsonRpcClient.doPost(url, headers, urlParams, requestData);
    }

    /**
     * Perform a HTTP PUT request.
     * 
     * @param url
     * @param headers
     * @param urlParams
     * @param requestData
     * @return
     */
    public static RequestResponse callHttpPut(String url, Map<String, Object> headers,
            Map<String, Object> urlParams, Object requestData) {
        return httpJsonRpcClient.doPut(url, headers, urlParams, requestData);
    }

    /**
     * Perform a HTTP PATCH request.
     * 
     * @param url
     * @param headers
     * @param urlParams
     * @param requestData
     * @return
     */
    public static RequestResponse callHttpPatch(String url, Map<String, Object> headers,
            Map<String, Object> urlParams, Object requestData) {
        return httpJsonRpcClient.doPatch(url, headers, urlParams, requestData);
    }

    /**
     * Perform a HTTP DELETE request.
     * 
     * @param url
     * @param headers
     * @param urlParams
     * @return
     */
    public static RequestResponse callHttpDelete(String url, Map<String, Object> headers,
            Map<String, Object> urlParams) {
        return httpJsonRpcClient.doDelete(url, headers, urlParams);
    }

    /**
     * Perform a HTTP DELETE request.
     * 
     * @param url
     * @param headers
     * @param urlParams
     * @param requestData
     * @return
     */
    public static RequestResponse callHttpDelete(String url, Map<String, Object> headers,
            Map<String, Object> urlParams, Object requestData) {
        return httpJsonRpcClient.doDelete(url, headers, urlParams, requestData);
    }
}
