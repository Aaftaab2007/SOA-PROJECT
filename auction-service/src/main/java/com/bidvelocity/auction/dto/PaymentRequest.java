package com.bidvelocity.auction.dto;

import java.math.BigDecimal;

public class PaymentRequest {

    private Long auctionId;
    private Long winnerId;
    private BigDecimal amount;

    public PaymentRequest() {
    }

    public PaymentRequest(
            Long auctionId,
            Long winnerId,
            BigDecimal amount) {
        this.auctionId = auctionId;
        this.winnerId = winnerId;
        this.amount = amount;
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
}