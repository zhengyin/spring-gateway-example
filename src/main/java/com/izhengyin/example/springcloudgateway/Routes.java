package com.izhengyin.example.springcloudgateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @author zhengyin  <zhengyin.name@gmail.com>
 * @date Created on 2019/4/3 9:55 AM
 */
@Configuration
public class Routes {
    private final Logger logger = LoggerFactory.getLogger(Routes.class);
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder routeLocatorBuilder , GatewayRouteProperties routeProperties) {
        Map<String, GatewayRouteProperties.Route> routeMap = routeProperties.getRoutes();
        RouteLocatorBuilder.Builder builder = routeLocatorBuilder.routes();
        for(String name : routeMap.keySet()){
            final GatewayRouteProperties.Route route = routeMap.get(name);
            builder.route(name, r -> {
                return r.predicate(new Predicate<ServerWebExchange>() {
                    @Override
                    public boolean test(ServerWebExchange serverWebExchange) {
                        return Pattern.compile(route.getPredicate()).matcher(serverWebExchange.getRequest().getURI().getPath()).find();
                    }
                }).filters(f -> {
                    return f.stripPrefix(1);
                }).uri(route.getUri());
            });
        }

        routeMap.keySet().forEach(routeName -> {
            logger.info("Build Route "+routeName +" -> "+routeMap.get(routeName));
        });

        return builder.build();
    }

    @Bean
    @Primary
    public GatewayRouteProperties gatewayRouteProperties(){
        return new GatewayRouteProperties();
    }

}
