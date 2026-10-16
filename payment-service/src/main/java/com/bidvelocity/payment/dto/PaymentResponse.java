package com.bidvelocity.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bidvelocity.payment.entity.Payment;

public class PaymentResponse {

    private Long id;
    private Long auctionId;
    private Long winnerId;
    private BigDecimal amount;
    private String paymentReference;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;

    public PaymentResponse() {
    }

    public PaymentResponse(Payment payment) {

        this.id = payment.getId();
        this.auctionId = payment.getAuctionId();
        this.winnerId = payment.getWinnerId();
        this.amount = payment.getAmount();
        this.paymentReference =
                payment.getPaymentReference();

        this.status =
                payment.getStatus() != null
                        ? payment.getStatus().name()
                        : null;

        this.createdAt = payment.getCreatedAt();
        this.processedAt = payment.getProcessedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
}