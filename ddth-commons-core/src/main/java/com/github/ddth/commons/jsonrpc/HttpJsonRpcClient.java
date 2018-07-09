package com.github.ddth.commons.jsonrpc;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.ddth.commons.jsonrpc.RequestResponse.RpcStatus;
import com.github.ddth.commons.utils.SerializationUtils;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * HTTP Json-RPC client.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.9.0
 */
public class HttpJsonRpcClient implements AutoCloseable {
    public final static String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
    public final static MediaType MEDIA_TYPE_JSON = MediaType.parse(CONTENT_TYPE_JSON);

    private OkHttpClient client;
    private long readTimeoutMs = 60000;
    private long writeTimeoutMs = 60000;

    /**
     * Create a new instance of {@link OkHttpClient}. Sub-class my override this method to customize
     * the {@link OkHttpClient} instance.
     * 
     * @return
     * @since 0.9.1.6
     */
    protected OkHttpClient buildHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(readTimeoutMs, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeoutMs, TimeUnit.MILLISECONDS);
        OkHttpClient client = builder.build();
        return client;
    }

    /**
     * Setter for {@link #client}.
     * 
     * @param client
     * @return
     * @since 0.9.1.6
     */
    protected HttpJsonRpcClient setHttpClient(OkHttpClient client) {
        this.client = client;
        return this;
    }

    /**
     * Getter for {@link #client}.
     * 
     * @return
     * @since 0.9.1.6
     */
    protected OkHttpClient getHttpClient() {
        return client;
    }

    /**
     * Getter for {@link #readTimeoutMs}.
     * 
     * @return
     * @since 0.9.1.6
     */
    public long getReadTimeoutMs() {
        return readTimeoutMs;
    }

    /**
     * Setter for {@link #readTimeoutMs}.
     * 
     * @param timeout
     * @return
     * @since 0.9.1.6
     */
    public HttpJsonRpcClient setReadTimeoutMs(long timeout) {
        this.readTimeoutMs = timeout;
        return this;
    }

    /**
     * Getter for {@link #writeTimeoutMs}.
     * 
     * @return
     * @since 0.9.1.6
     */
    public long getWriteTimeoutMs() {
        return writeTimeoutMs;
    }

    /**
     * Setter for {@link #writeTimeoutMs}.
     * 
     * @param timeout
     * @return
     * @since 0.9.1.6
     */
    public HttpJsonRpcClient setWriteTimeoutMs(long timeout) {
        this.writeTimeoutMs = timeout;
        return this;
    }

    public HttpJsonRpcClient init() {
        setHttpClient(buildHttpClient());
        return this;
    }

    public void destroy() {
        // EMPTY
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        destroy();
    }

    private static HttpUrl buildUrl(String url, Map<String, Object> urlParams) {
        HttpUrl.Builder builder = HttpUrl.parse(url.replaceAll("[\\?&\\s]+$", "")).newBuilder();
        if (urlParams != null) {
            urlParams.forEach(
                    (k, v) -> builder.addQueryParameter(k, v != null ? v.toString().trim() : ""));
        }
        return builder.build();
    }

    private static Request.Builder buildRequest(String url, Map<String, Object> headers,
            Map<String, Object> urlParams) {
        HttpUrl finalUrl = buildUrl(url, urlParams);
        Request.Builder requestBuilder = new Request.Builder().url(finalUrl);
        if (headers != null) {
            headers.forEach(
                    (k, v) -> requestBuilder.addHeader(k, v != null ? v.toString().trim() : ""));
        }
        return requestBuilder;
    }

    private static RequestResponse initRequestResponse(String method, String url,
            Map<String, Object> headers, Map<String, Object> urlParams, Object requestData) {
        RequestResponse requestResponse = new RequestResponse().setRequestMethod(method)
                .setRequestUrl(url).setRequestHeaders(headers).setRequestParams(urlParams)
                .setRequestData(requestData);
        return requestResponse;
    }

    private static RequestResponse doCall(OkHttpClient client, Request request,
            RequestResponse requestResponse) {
        try {
            Response response = client.newCall(request).execute();
            if (response != null) {
                int httpCode = response.code();
                requestResponse.setResponseStatus(String.valueOf(httpCode)).setRpcStatus(
                        200 >= httpCode && httpCode < 300 ? RpcStatus.OK : RpcStatus.ERROR);
                ResponseBody body = response.body();
                requestResponse.setResponseData(body != null ? body.bytes() : null);
            } else {
                requestResponse.setRpcStatus(RpcStatus.ERROR);
            }
        } catch (Throwable e) {
            requestResponse.setRpcStatus(RpcStatus.ERROR).setRpcError(e);
        }
        requestResponse.setTimestampEnd(System.currentTimeMillis());
        return requestResponse;
    }

    private static RequestBody buildRequestBody(Object data) {
        return data != null
                ? RequestBody.create(MEDIA_TYPE_JSON, SerializationUtils.toJsonString(data)) : null;
    }

    /**
     * Perform a GET request.
     * 
     * @param url
     * @param headers
     * @param urlParams
     * @return
     */
    public RequestResponse doGet(String url, Map<String, Object> headers,
            Map<String, Object> urlParams) {
        RequestResponse requestResponse = initRequestResponse("GET", url, headers, urlParams, null);
        Request.Builder requestBuilder = buildRequest(url, headers, urlParams).get();
        return doCall(client, requestBuilder.build(), requestResponse);
    }

    /**
     * Perform a POST request.
     * 
     * @param url
     * @param headers
     * @param urlParams
     * @param requestData
     * @return
     */
    public RequestResponse doPost(String url, Map<String, Object> headers,
            Map<String, Object> urlParams, Object requestData) {
        RequestResponse requestResponse = initRequestResponse("POST", url, headers, urlParams,
                requestData);
        Request.Builder requestBuilder = buildRequest(url, headers, urlParams)
                .post(buildRequestBody(requestData));
        return doCall(client, requestBuilder.build(), requestResponse);
    }

    /**
     * Perform a PUT request.
     * 
     * @param url
     * @param headers
     * @param urlParams
     * @param requestData
     * @return
     */
    public RequestResponse doPut(String url, Map<String, Object> headers,
            Map<String, Object> urlParams, Object requestData) {
        RequestResponse requestResponse = initRequestResponse("PUT", url, headers, urlParams,
                requestData);
        Request.Builder requestBuilder = buildRequest(url, headers, urlParams)
                .put(buildRequestBody(requestData));
        return doCall(client, requestBuilder.build(), requestResponse);
    }

    /**
     * Perform a PATCH request.
     * 
     * @param url
     * @param headers
     * @param urlParams
     * @param requestData
     * @return
     */
    public RequestResponse doPatch(String url, Map<String, Object> headers,
            Map<String, Object> urlParams, Object requestData) {
        RequestResponse requestResponse = initRequestResponse("PATCH", url, headers, urlParams,
                requestData);
        Request.Builder requestBuilder = buildRequest(url, headers, urlParams)
                .patch(buildRequestBody(requestData));
        return doCall(client, requestBuilder.build(), requestResponse);
    }

    /**
     * Perform a DELETE request.
     * 
     * @param url
     * @param headers
     * @param urlParams
     * @return
     */
    public RequestResponse doDelete(String url, Map<String, Object> headers,
            Map<String, Object> urlParams) {
        return doDelete(url, headers, urlParams, null);
    }

    /**
     * Perform a DELETE request.
     * 
     * @param url
     * @param headers
     * @param urlParams
     * @param requestData
     * @return
     */
    public RequestResponse doDelete(String url, Map<String, Object> headers,
            Map<String, Object> urlParams, Object requestData) {
        RequestResponse requestResponse = initRequestResponse("DELETE", url, headers, urlParams,
                requestData);
        Request.Builder requestBuilder = buildRequest(url, headers, urlParams)
                .delete(buildRequestBody(requestData));
        return doCall(client, requestBuilder.build(), requestResponse);
    }

    /*----------------------------------------------------------------------*/
    public static void main(String[] args) {
        try (HttpJsonRpcClient client = new HttpJsonRpcClient()) {
            client.init();

            System.out.println(client.doGet("https://httpbin.org/get", null, null));
            System.out
                    .println(client.doPost("https://httpbin.org/post", null, null, "post-request"));
            System.out.println(client.doPut("https://httpbin.org/put", null, null, "put-request"));
            System.out.println(
                    client.doPatch("https://httpbin.org/patch", null, null, "patch-request"));
            System.out.println(client.doDelete("https://httpbin.org/delete", null, null));
            System.out.println(
                    client.doDelete("https://httpbin.org/delete", null, null, "delete-request"));
        }
    }
}
