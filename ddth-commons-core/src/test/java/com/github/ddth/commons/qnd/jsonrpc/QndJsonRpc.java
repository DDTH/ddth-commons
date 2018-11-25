package com.github.ddth.commons.qnd.jsonrpc;

import com.github.ddth.commons.jsonrpc.HttpJsonRpcClient;
import com.github.ddth.commons.jsonrpc.RequestResponse;

public class QndJsonRpc {

    static void print(RequestResponse rr) {
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
        System.out.println("\tData     : " + new String(rr.getResponseData()));

        System.out.println("TRACKING:");
        System.out.println("\tDuration : " + (rr.getTimestampEnd() - rr.getTimestampStart()));
    }

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
