# com.github.ddth.commons.jsonrpc

Json-RPC utility and helper classes.

This package assumes the following conventions:

- Data transferred between client and server is in JSON format, UTF8 encoding.
- Response from server is a JSON-encoded map of {key:value}s.

_**Available since v0.9.0.**_

## Maven

```xml
<dependency>
    <groupId>com.github.ddth</groupId>
    <artifactId>ddth-commons-jsonrpc</artifactId>
    <version>${ddth_commons_version}</version>
    <type>pom</type>
</dependency>
```

**Class `RequestResponse`:**

Capture RPC's request and response:

- Request
  - http method, url and parameters
  - headers
  - request data  
- RPC call
  - status
  - error
  - timestamp start and end
- Response
  - status
  - response data

**Class `JsonRpcUtils`:**

Json-RPC utility class

- Helper methods to build RPC response in JSON format, containing fields `status`, `message` and `data`.
- Helper methods to make RPC calls via HTTP(S).

**Class `HttpJsonRpcClient`:**

Client to make RPC calls via HTTP(S).

## Examples

Make some http requests and print out response data:

```java
package qnd;

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
            Map<String, Object> headers = MapUtils.createMap("X-App-Id", "app-id", "X-Access-Token", "access-token");
            Map<String, Object> urlParams = MapUtils.createMap("a", 1, "b", true);

            print(client.doGet("https://httpbin.org/get", headers, urlParams));
            print(client.doPost("https://httpbin.org/post", headers, urlParams, "post-request"));
            print(client.doPut("https://httpbin.org/put", headers, urlParams, "put-request"));
            print(client.doPatch("https://httpbin.org/patch", headers, urlParams, "patch-request"));
            print(client.doDelete("https://httpbin.org/delete", headers, urlParams));
        }
    }
}
```

## History

**v0.9.3 - 2019-04-29**
- DELETE call with body is deprecated.

**v0.9.0 - 2018-01-27**
- First release
