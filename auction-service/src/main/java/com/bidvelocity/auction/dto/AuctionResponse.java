package com.bidvelocity.auction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bidvelocity.auction.entity.Auction;

public class AuctionResponse {

    private Long id;
    private String title;
    private String description;
    private Long sellerId;
    private BigDecimal startingPrice;
    private BigDecimal minimumBidIncrement;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Long winnerId;
    private BigDecimal winningBid;

    public AuctionResponse() {
    }

    public AuctionResponse(Auction auction) {

        this.id = auction.getId();
        this.title = auction.getTitle();
        this.description = auction.getDescription();
        this.sellerId = auction.getSellerId();
        this.startingPrice = auction.getStartingPrice();
        this.minimumBidIncrement =
                auction.getMinimumBidIncrement();
        this.startTime = auction.getStartTime();
        this.endTime = auction.getEndTime();
        this.status =
                auction.getStatus() != null
                        ? auction.getStatus().name()
                        : null;
        this.winnerId = auction.getWinnerId();
        this.winningBid = auction.getWinningBid();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    public BigDecimal getMinimumBidIncrement() {
        return minimumBidIncrement;
    }

    public void setMinimumBidIncrement(
            BigDecimal minimumBidIncrement) {

        this.minimumBidIncrement = minimumBidIncrement;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public BigDecimal getWinningBid() {
        return winningBid;
    }

    public void setWinningBid(BigDecimal winningBid) {
        this.winningBid = winningBid;
    }
}