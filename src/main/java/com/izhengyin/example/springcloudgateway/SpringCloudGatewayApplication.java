package com.izhengyin.example.springcloudgateway;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringCloudGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayApplication.class, args);
    }

    @RestController
    public static class SimulateRefreshConfig implements ApplicationContextAware {

        @Autowired
        private GatewayRouteProperties gatewayRouteProperties;

        private ApplicationContext applicationContext;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        @PutMapping("/properties/{key}/{value}")
        public Mono<String> putProperties(@PathVariable String key , @PathVariable String value){
            System.setProperty(key,value);
            Set<String> keys = new HashSet<>();
            keys.add(key);
            this.applicationContext.publishEvent(new EnvironmentChangeEvent(keys));
            return Mono.just("put properties , "+key+" -> "+value);
        }

        @GetMapping("/gatewayRouteProperties")
        public Mono<String> gatewayRouteProperties(){
            return Mono.just(gatewayRouteProperties.toString());
        }

    }





}
