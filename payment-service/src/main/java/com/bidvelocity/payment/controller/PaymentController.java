package com.bidvelocity.payment.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bidvelocity.payment.dto.PaymentRequest;
import com.bidvelocity.payment.dto.PaymentResponse;
import com.bidvelocity.payment.service.PaymentProcessingService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentProcessingService paymentService;

    public PaymentController(
            PaymentProcessingService paymentService) {

        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody PaymentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.createPayment(request));
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<PaymentResponse> processPayment(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {

        return ResponseEntity.ok(
                paymentService.processPayment(id, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {

        return ResponseEntity.ok(
                paymentService.getPayment(id, userId));
    }

    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<PaymentResponse>
            getPaymentByAuction(
                    @PathVariable Long auctionId,
                    @RequestHeader("X-User-Id") Long userId) {

        return ResponseEntity.ok(
                paymentService
                        .getPaymentByAuction(auctionId, userId));
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<PaymentResponse> refundPayment(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) {

        return ResponseEntity.ok(
                paymentService.refundPayment(id, userId));
    }
}