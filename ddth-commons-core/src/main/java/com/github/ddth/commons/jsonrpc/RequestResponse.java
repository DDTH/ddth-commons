package com.github.ddth.commons.jsonrpc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public static enum RpcStatus {
        NO_RESPONSE(0) // no response from RPC call get
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
    public long getTimestampStart() {
        return timestampStart;
    }

    public RequestResponse setTimestampStart(long timestamp) {
        this.timestampStart = timestamp;
        return this;
    }

    public long getTimestampEnd() {
        return timestampEnd;
    }

    public RequestResponse setTimestampEnd(long timestamp) {
        this.timestampEnd = timestamp;
        return this;
    }

    public Throwable getRpcError() {
        return rpcError;
    }

    public RequestResponse setRpcError(Throwable error) {
        this.rpcError = error;
        return this;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public RequestResponse setRequestMethod(String method) {
        this.requestMethod = method;
        return this;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public RequestResponse setRequestUrl(String url) {
        this.requestUrl = url;
        return this;
    }

    public Map<String, Object> getRequestHeaders() {
        return requestHeaders;
    }

    public RequestResponse setRequestHeaders(Map<String, Object> requestHeaders) {
        this.requestHeaders = requestHeaders;
        return this;
    }

    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    public RequestResponse setRequestParams(Map<String, Object> requestParams) {
        this.requestParams = requestParams;
        return this;
    }

    public Object getRequestData() {
        return requestData;
    }

    synchronized public RequestResponse setRequestData(Object requestData) {
        this.requestData = requestData;
        this.requestJson = requestData != null ? SerializationUtils.toJson(requestData) : null;
        return this;
    }

    public JsonNode getRequestJson() {
        return requestJson;
    }
    /*------------------------------------------------------------*/

    /**
     * 
     * @return
     * @see RpcStatus
     */
    public RpcStatus getRpcStatus() {
        return rpcStatus;
    }

    /**
     * 
     * @param rpcStatus
     * @return
     * @see RpcStatusFs
     */
    public RequestResponse setRpcStatus(RpcStatus rpcStatus) {
        this.rpcStatus = rpcStatus;
        return this;
    }

    /**
     * Response status from RPC call (if any).
     * 
     * @return
     */
    public String getResponseStatus() {
        return responseStatus;
    }

    public RequestResponse setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
        return this;
    }

    /**
     * Raw response data from RPC call.
     * 
     * @return
     */
    public byte[] getResponseData() {
        return responseData;
    }

    synchronized public RequestResponse setResponseData(byte[] responseData) {
        this.responseData = responseData;
        this.responseJson = responseData != null ? SerializationUtils.readJson(responseData) : null;
        return this;
    }

    /**
     * Response data from RPC call as Json object.
     * 
     * @return
     */
    public JsonNode getResponseJson() {
        return responseJson;
    }

    public JsonNode getResponseValue(String path) {
        return JacksonUtils.getValue(responseJson, path);
    }

    public Optional<JsonNode> getResponseValueOptional(String path) {
        return JacksonUtils.getValueOptional(responseJson, path);
    }

    public <T> T getResponseValue(String path, Class<T> clazz) {
        return JacksonUtils.getValue(responseJson, path, clazz);
    }

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
