# spring-gateway-example

> This project is for illustration the issue (https://github.com/spring-cloud/spring-cloud-gateway/issues/993)

### initial properties
```
spring.application.name=gateway-example
server.port=9999

logging.level.root=info

management.endpoints.web.exposure.include = *

my.gateway.routes.httpbin.uri=http://httpbin.org
my.gateway.routes.httpbin.predicate=foo

```


> test route 


```
➜ curl  http://127.0.0.1:9999/foo/get
{
  "args": {}, 
  "headers": {
    "Accept": "*/*", 
    "Forwarded": "proto=http;host=\"127.0.0.1:9999\";for=\"127.0.0.1:50784\"", 
    "Host": "httpbin.org", 
    "User-Agent": "curl/7.54.0", 
    "X-Forwarded-Host": "127.0.0.1:9999", 
    "X-Forwarded-Prefix": "/foo"
  }, 
  "origin": "127.0.0.1, 123.114.75.163, 127.0.0.1", 
  "url": "https://127.0.0.1:9999/get"
}
```

> modify properties to change predicate 

```
➜  spring-cloud-gateway curl -X PUT "http://127.0.0.1:9999/properties/my.gateway.routes.httpbin.predicate/bar"   
put properties , my.gateway.routes.httpbin.predicate -> bar%                                            
```

> refresh gateway
```
➜  spring-cloud-gateway curl -v -X POST   http://127.0.0.1:9999/actuator/gateway/refresh
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9999 (#0)
> POST /actuator/gateway/refresh HTTP/1.1
> Host: 127.0.0.1:9999
> User-Agent: curl/7.54.0
> Accept: */*
> 
< HTTP/1.1 200 OK
< content-length: 0
< 
* Connection #0 to host 127.0.0.1 left intact
```

> not refreshed
```
➜ curl -I  http://127.0.0.1:9999/bar/get
HTTP/1.1 404 Not Found
transfer-encoding: chunked
Content-Type: application/json;charset=UTF-8
Content-Length: 110

➜ curl -I  http://127.0.0.1:9999/foo/get
HTTP/1.1 200 OK
Access-Control-Allow-Credentials: true
Access-Control-Allow-Origin: *
Content-Type: application/json
Date: Wed, 03 Apr 2019 03:50:52 GMT
Server: nginx
Content-Length: 0
```