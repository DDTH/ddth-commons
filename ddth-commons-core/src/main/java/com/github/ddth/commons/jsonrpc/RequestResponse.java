package com.github.ddth.commons.jsonrpc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.ddth.commons.utils.JacksonUtils;
import com.github.ddth.commons.utils.MapUtils;
import com.github.ddth.commons.utils.SerializationUtils;

/**
 * Capture RPC's request and response.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.9.0
 */
public class RequestResponse {
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestResponse.class);

    /**
     * RPC call status.
     * 
     * <ul>
     * <li>{@link #NO_RESPONSE}: no response from RPC call yet (the request has not been made, or
     * response has not arrived)</li>
     * <li>{@link #OK}: the RPC call was successful</li>
     * <li>{@link #ERROR}: there was an error while making RPC call</li>
     * </ul>
     */
    public static enum RpcStatus {
        NO_RESPONSE(0) // no response from RPC call yet
        , OK(1) // call successful
        , ERROR(-1) // call error
        ;

        private final int value;

        RpcStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private String requestUrl, requestMethod;
    private Map<String, Object> requestHeaders;
    private Map<String, Object> requestParams;
    private Object requestData;
    private JsonNode requestJson;

    private Throwable rpcError;
    private RpcStatus rpcStatus = RpcStatus.NO_RESPONSE;
    private String responseStatus;
    private byte[] responseData;
    private JsonNode responseJson;

    private long timestampStart = System.currentTimeMillis();
    private long timestampEnd = 0;

    /*------------------------------------------------------------*/
    /**
     * Timestamp when RPC starts.
     * 
     * @return
     */
    public long getTimestampStart() {
        return timestampStart;
    }

    /**
     * Timestamp when RPC starts.
     * 
     * @param timestamp
     * @return
     */
    public RequestResponse setTimestampStart(long timestamp) {
        this.timestampStart = timestamp;
        return this;
    }

    /**
     * Timestamp when RPC ends.
     * 
     * @return
     */
    public long getTimestampEnd() {
        return timestampEnd;
    }

    /**
     * Timestamp when RPC ends.
     * 
     * @param timestamp
     * @return
     */
    public RequestResponse setTimestampEnd(long timestamp) {
        this.timestampEnd = timestamp;
        return this;
    }

    /**
     * HTTP request method (GET, POST, etc).
     * 
     * @return
     */
    public String getRequestMethod() {
        return requestMethod;
    }

    /**
     * HTTP request method (GET, POST, etc).
     * 
     * @param method
     * @return
     */
    public RequestResponse setRequestMethod(String method) {
        this.requestMethod = method;
        return this;
    }

    /**
     * HTTP request URL.
     * 
     * @return
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * HTTP request URL.
     * 
     * @param url
     * @return
     */
    public RequestResponse setRequestUrl(String url) {
        this.requestUrl = url;
        return this;
    }

    /**
     * HTTP request headers.
     * 
     * @return
     */
    public Map<String, Object> getRequestHeaders() {
        return requestHeaders;
    }

    /**
     * HTTP request headers.
     * 
     * @param requestHeaders
     * @return
     */
    public RequestResponse setRequestHeaders(Map<String, Object> requestHeaders) {
        this.requestHeaders = requestHeaders;
        return this;
    }

    /**
     * HTTP URL's query string as a map.
     * 
     * @return
     */
    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    /**
     * HTTP URL's query string as a map.
     * 
     * @param requestParams
     * @return
     */
    public RequestResponse setRequestParams(Map<String, Object> requestParams) {
        this.requestParams = requestParams;
        return this;
    }

    /**
     * HTTP request body.
     * 
     * @return
     */
    public Object getRequestData() {
        return requestData;
    }

    /**
     * HTTP request body.
     * 
     * @param requestData
     * @return
     */
    public RequestResponse setRequestData(Object requestData) {
        this.requestData = requestData;
        requestJson = requestData != null
                ? (requestData instanceof JsonNode ? (JsonNode) requestData
                        : SerializationUtils.toJson(requestData))
                : null;
        return this;
    }

    /**
     * HTTP request body as Json.
     * 
     * @return
     */
    public JsonNode getRequestJson() {
        return requestJson;
    }
    /*------------------------------------------------------------*/

    /**
     * RPC call status.
     * 
     * @return
     * @see RpcStatus
     */
    public RpcStatus getRpcStatus() {
        return rpcStatus;
    }

    /**
     * RPC call status.
     * 
     * @param rpcStatus
     * @return
     * @see RpcStatus
     */
    public RequestResponse setRpcStatus(RpcStatus rpcStatus) {
        this.rpcStatus = rpcStatus;
        return this;
    }

    /**
     * RPC error, if any.
     * 
     * @return
     */
    public Throwable getRpcError() {
        return rpcError;
    }

    /**
     * RPC error, if any.
     * 
     * @param error
     * @return
     */
    public RequestResponse setRpcError(Throwable error) {
        this.rpcError = error;
        return this;
    }

    /**
     * HTTP response status (200, 403, etc).
     * 
     * @return
     */
    public String getResponseStatus() {
        return responseStatus;
    }

    /**
     * HTTP response status (200, 403, etc).
     * 
     * @param responseStatus
     * @return
     */
    public RequestResponse setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    /**
     * Raw HTTP response data.
     * 
     * @return
     */
    public byte[] getResponseData() {
        return responseData;
    }

    /**
     * Raw HTTP response data.
     * 
     * @param responseData
     * @return
     */
    public RequestResponse setResponseData(byte[] responseData) {
        this.responseData = responseData;
        try {
            responseJson = responseData != null ? SerializationUtils.readJson(responseData) : null;
        } catch (Exception e) {
            responseJson = null;
            LOGGER.error(e.getMessage(), e);
        }
        return this;
    }

    /**
     * HTTP response data as Json object.
     * 
     * @return
     */
    public JsonNode getResponseJson() {
        return responseJson;
    }

    /**
     * Get a response value from the JSON tree using dPath expression.
     * 
     * @param dPath
     * @return
     */
    public JsonNode getResponseValue(String dPath) {
        return JacksonUtils.getValue(responseJson, dPath);
    }

    /**
     * Get a response value from the JSON tree using dPath expression.
     * 
     * @param path
     * @return
     */
    public Optional<JsonNode> getResponseValueOptional(String path) {
        return JacksonUtils.getValueOptional(responseJson, path);
    }

    /**
     * Get a response value from the JSON tree using dPath expression.
     * 
     * @param path
     * @param clazz
     * @return
     */
    public <T> T getResponseValue(String path, Class<T> clazz) {
        return JacksonUtils.getValue(responseJson, path, clazz);
    }

    /**
     * Get a response value from the JSON tree using dPath expression.
     * 
     * @param path
     * @param clazz
     * @return
     */
    public <T> Optional<T> getResponseValueOptional(String path, Class<T> clazz) {
        return JacksonUtils.getValueOptional(responseJson, path, clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        Map<String, Object> toString = new HashMap<>();

        Map<String, Object> request = MapUtils.removeNulls(MapUtils.createMap("url", requestUrl,
                "method", requestMethod, "headers", requestHeaders, "urlParams", requestParams,
                "data", requestData, "dataJson", requestJson));
        toString.put("request", request);

        Map<String, Object> rpc = MapUtils
                .removeNulls(MapUtils.createMap("status", rpcStatus, "error", rpcError));
        toString.put("rpc", rpc);

        Map<String, Object> response = MapUtils.removeNulls(MapUtils.createMap("status",
                responseStatus, "data", responseData, "dataJson", responseJson));
        toString.put("response", response);

        Map<String, Object> tracking = MapUtils.removeNulls(MapUtils.createMap("start",
                timestampStart, "end", timestampEnd, "duration", timestampEnd - timestampStart));
        toString.put("tracking", tracking);

        return SerializationUtils.toJsonString(toString);
    }
}
