package com.github.ddth.commons.qnd.jsonrpc;

import java.util.Map;

import com.github.ddth.commons.jsonrpc.HttpJsonRpcClient;
import com.github.ddth.commons.jsonrpc.RequestResponse;
import com.github.ddth.commons.utils.MapUtils;

public class QndJsonRpc {
    static void print(RequestResponse rr) {
        System.out.println(rr);
        System.out.println("REQUEST:");
        System.out.println("\tUrl      : [" + rr.getRequestMethod() + "] " + rr.getRequestUrl());
        System.out.println("\tHeaders  : " + rr.getRequestHeaders());
        System.out.println("\tUrlParams: " + rr.getRequestParams());
        System.out.println("\tData     : " + rr.getRequestData());

        System.out.println("RPC:");
        System.out.println("\tStatus   : " + rr.getRpcStatus());
        System.out.println("\tError    : " + rr.getRpcError());

        System.out.println("RESPONSE:");
        System.out.println("\tStatus   : " + rr.getResponseStatus());
        System.out.println("\tData     : " + rr.getResponseJson());
        System.out.println("\t  args.b                 = " + rr.getResponseValue("args.b"));
        System.out.println("\t  headers.X-Access-Token = " + rr.getResponseValue("headers.X-Access-Token"));

        System.out.println("TRACKING:");
        System.out.println("\tDuration : " + (rr.getTimestampEnd() - rr.getTimestampStart()));
        System.out.println("======================================================================");
        System.out.println();
    }

    public static void main(String[] args) {
        try (HttpJsonRpcClient client = new HttpJsonRpcClient()) {
            client.init();
            Map<String, Object> headers = MapUtils.createMap("X-App-Id", "app-id", "X-Access-Token",
                    "access-token");
            Map<String, Object> urlParams = MapUtils.createMap("a", 1, "b", true);

            print(client.doGet("https://httpbin.org/get", headers, urlParams));
            print(client.doPost("https://httpbin.org/post", headers, urlParams, "post-request"));
            print(client.doPut("https://httpbin.org/put", headers, urlParams, "put-request"));
            print(client.doPatch("https://httpbin.org/patch", headers, urlParams, "patch-request"));
            print(client.doDelete("https://httpbin.org/delete", headers, urlParams));
        }
    }
}
