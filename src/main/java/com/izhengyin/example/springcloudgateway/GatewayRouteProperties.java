package com.izhengyin.example.springcloudgateway;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhengyin  <zhengyin.name@gmail.com>
 * @date Created on 2019/3/29 10:02 AM
 */
@ConfigurationProperties(prefix = "my.gateway")
public class GatewayRouteProperties {

    private Map<String, Route> routes = new LinkedHashMap();

    public Map<String, Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Map<String, Route> routes) {
        this.routes = routes;
    }

    public static class Route {
        private String uri;
        private String predicate;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getPredicate() {
            return predicate;
        }

        public void setPredicate(String predicate) {
            this.predicate = predicate;
        }

        @Override
        public String toString() {
            return "Route{" +
                    ", uri=" + uri +
                    ", predicate=" + predicate +
                    '}';
        }
    }

    @Override
    public String toString() {

        return "GatewayRouteProperties{" +
                "routes=" + JSON.toJSONString(routes )+
                '}';
    }
}

