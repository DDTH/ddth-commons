package com.github.ddth.commons.jsonrpc;

import java.util.Map;
import java.util.Map.Entry;

import com.github.ddth.commons.jsonrpc.RequestResponse.RpcStatus;
import com.github.ddth.commons.utils.SerializationUtils;

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

    private OkHttpClient client = new OkHttpClient();

    public HttpJsonRpcClient init() {
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

    private static String buildUrl(String url, Map<String, Object> urlParams) {
        StringBuilder sb = new StringBuilder(url);
        boolean needAnd = false;
        if (url.indexOf("?") >= 0) {
            needAnd = true;
        } else {
            sb.append("?");
        }
        if (urlParams != null) {
            for (Entry<String, Object> entry : urlParams.entrySet()) {
                if (needAnd) {
                    sb.append("&");
                }
                sb.append(entry.getKey()).append("=").append(entry.getValue().toString());
                needAnd = true;
            }
        }
        return sb.toString();
    }

    private static Request.Builder buildRequest(String url, Map<String, Object> headers,
            Map<String, Object> urlParams) {
        String finalUrl = buildUrl(url, urlParams);
        Request.Builder requestBuilder = new Request.Builder().url(finalUrl);
        if (headers != null) {
            for (Entry<String, Object> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue().toString());
            }
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
