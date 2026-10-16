package com.bidvelocity.auction.dto;

import java.math.BigDecimal;

public class BiddingWinnerResponse {

    private Long auctionId;
    private Long winnerId;
    private BigDecimal winningBid;

    public BiddingWinnerResponse() {
    }

    public BiddingWinnerResponse(
            Long auctionId,
            Long winnerId,
            BigDecimal winningBid) {
        this.auctionId = auctionId;
        this.winnerId = winnerId;
        this.winningBid = winningBid;
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

    public BigDecimal getWinningBid() {
        return winningBid;
    }

    public void setWinningBid(BigDecimal winningBid) {
        this.winningBid = winningBid;
    }
}