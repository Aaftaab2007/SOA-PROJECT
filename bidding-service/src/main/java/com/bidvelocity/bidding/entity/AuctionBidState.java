package com.bidvelocity.bidding.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "auction_bid_state")
public class AuctionBidState {

    @Id
    private Long auctionId;

    @Column(precision = 19, scale = 2)
    private BigDecimal currentHighestBid;

    private Long highestBidderId;

    @Version
    private Long version;

    public AuctionBidState() {
    }

    public AuctionBidState(Long auctionId) {
        this.auctionId = auctionId;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public BigDecimal getCurrentHighestBid() {
        return currentHighestBid;
    }

    public void setCurrentHighestBid(
            BigDecimal currentHighestBid) {
        this.currentHighestBid = currentHighestBid;
    }

    public Long getHighestBidderId() {
        return highestBidderId;
    }

    public void setHighestBidderId(
            Long highestBidderId) {
        this.highestBidderId = highestBidderId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}