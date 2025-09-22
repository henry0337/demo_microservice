package com.demo.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.*;

@Configuration
public class GatewayConfiguration {

    @Bean
    RouterFunction<ServerResponse> gatewayRedirector() {
        return route()
                .route(path("/api/v1/user/**"), req -> ServerResponse.temporaryRedirect(
                        URI.create("lb://user-service" + req.uri().getPath())).build())
                .route(path("/api/v1/auth/**"), req -> ServerResponse.temporaryRedirect(
                        URI.create("lb://auth-service" + req.uri().getPath())).build())
                .route(path("/api/v1/payment/**"), req -> ServerResponse.temporaryRedirect(
                        URI.create("lb://payment-service" + req.uri().getPath())).build())
                .build();
    }
}
