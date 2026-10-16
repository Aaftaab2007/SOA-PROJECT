package com.bidvelocity.bidding.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bidvelocity.bidding.entity.Bid;

public class BidResponse {

    private Long bidId;
    private Long auctionId;
    private Long bidderId;
    private BigDecimal amount;
    private LocalDateTime bidTime;
    private String status;

    public BidResponse() {
    }

    public BidResponse(Bid bid) {

        this.bidId = bid.getId();
        this.auctionId = bid.getAuctionId();
        this.bidderId = bid.getBidderId();
        this.amount = bid.getAmount();
        this.bidTime = bid.getBidTime();

        this.status =
                bid.getStatus() != null
                        ? bid.getStatus().name()
                        : null;
    }

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Long getBidderId() {
        return bidderId;
    }

    public void setBidderId(Long bidderId) {
        this.bidderId = bidderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}