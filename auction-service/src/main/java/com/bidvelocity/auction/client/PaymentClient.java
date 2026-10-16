package com.bidvelocity.auction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.bidvelocity.auction.dto.PaymentRequest;
import com.bidvelocity.auction.dto.PaymentResponse;

@FeignClient(name = "payment-service")
public interface PaymentClient {

    @PostMapping("/api/payments")
    PaymentResponse createPayment(
            @RequestBody PaymentRequest request);

    @PostMapping("/api/payments/{id}/process")
    PaymentResponse processPayment(
            @PathVariable("id") Long paymentId,
            @RequestHeader("X-User-Id") Long userId);
}