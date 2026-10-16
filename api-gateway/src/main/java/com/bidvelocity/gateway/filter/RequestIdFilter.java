package com.bidvelocity.gateway.filter;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class RequestIdFilter
        implements GlobalFilter, Ordered {
	private static final Logger log =
	        LoggerFactory.getLogger(RequestIdFilter.class);

    private static final String REQUEST_ID =
            "X-Request-ID";

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        String requestId =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst(REQUEST_ID);

        if (requestId == null ||
                requestId.isBlank()) {

            requestId =
                    UUID.randomUUID().toString();
        }

        final String finalRequestId = requestId;

        ServerWebExchange modifiedExchange =
                exchange.mutate()
                        .request(request ->
                                request.headers(headers -> {

                                    headers.remove(
                                            REQUEST_ID);

                                    headers.add(
                                            REQUEST_ID,
                                            finalRequestId);
                                }))
                        .build();

        modifiedExchange.getResponse()
                .getHeaders()
                .set(
                        REQUEST_ID,
                        finalRequestId);
        log.info(
                "Request {} {}",
                finalRequestId,
                exchange.getRequest().getURI().getPath());

        return chain.filter(modifiedExchange);
    }

    @Override
    public int getOrder() {
        return -200;
    }
}