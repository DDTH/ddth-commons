# com.github.ddth.commons.jsonrpc

***Since v0.9.0***

Json-RPC utility and helper classes.

This package assumes the following conventions:

- Data transferred between client and server is in JSON format, UTF8 encoding.
- Server responses to a RPC call a map, with the following fields:
  - `status` : (`int`) RPC call's status code
  - `message`: (`string`) RPC call's error/additional message (if any)
  - `data`   : (`object`) RPC call's output result

## Class `RequestResponse`

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

## Class `JsonRpcUtils`

Json-RPC utility class

- Helper methods to build RPC response in JSON format, containing fields `status`, `message` and `data`.
- Helper methods to make RPC calls via HTTP(S).

## Class `HttpJsonRpcClient`

Client to make RPC calls via HTTP(S).
