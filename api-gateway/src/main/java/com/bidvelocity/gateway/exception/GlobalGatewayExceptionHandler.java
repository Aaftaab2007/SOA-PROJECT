package com.bidvelocity.gateway.exception;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.webflux.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Component
@Order(-1)
public class GlobalGatewayExceptionHandler
        implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalGatewayExceptionHandler(
            ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(
            ServerWebExchange exchange,
            Throwable ex) {

        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        HttpStatus status =
                HttpStatus.INTERNAL_SERVER_ERROR;

        Map<String, Object> response =
                new LinkedHashMap<>();

        response.put(
                "timestamp",
                LocalDateTime.now());

        response.put(
                "status",
                status.value());

        response.put(
                "error",
                status.getReasonPhrase());

        response.put(
                "message",
                "Gateway request failed");

        response.put(
                "path",
                exchange.getRequest()
                        .getPath()
                        .value());

        String requestId =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst(
                                "X-Request-ID");

        response.put(
                "requestId",
                requestId);

        try {

            byte[] bytes =
                    objectMapper.writeValueAsString(
                            response)
                            .getBytes(
                                    StandardCharsets.UTF_8);

            exchange.getResponse()
                    .setStatusCode(status);

            exchange.getResponse()
                    .getHeaders()
                    .setContentType(
                            MediaType.APPLICATION_JSON);

            return exchange.getResponse()
                    .writeWith(
                            Mono.just(
                                    exchange.getResponse()
                                            .bufferFactory()
                                            .wrap(bytes)));

        } catch (Exception e) {

            return Mono.error(e);
        }
    }
}