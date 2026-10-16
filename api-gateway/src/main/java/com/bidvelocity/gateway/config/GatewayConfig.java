package com.bidvelocity.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(
            RouteLocatorBuilder builder) {

        return builder.routes()

                // Authentication
                .route("auth-service", route ->
                        route.path("/api/auth/**")
                                .uri("lb://AUTH-SERVICE"))

                // Auction
                .route("auction-service", route ->
                        route.path("/api/auctions/**")
                                .uri("lb://AUCTION-SERVICE"))

                // Bidding
                .route("bidding-service", route ->
                        route.path("/api/bids/**")
                                .uri("lb://BIDDING-SERVICE"))

                // Payment
                .route("payment-service", route ->
                        route.path("/api/payments/**")
                                .uri("lb://PAYMENT-SERVICE"))

                // WebSocket
                .route("bidding-websocket", route ->
                        route.path("/ws/**")
                                .uri("lb:ws://BIDDING-SERVICE"))

                .build();
    }
}