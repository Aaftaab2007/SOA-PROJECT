package com.bidvelocity.bidding.dto;

import java.math.BigDecimal;

public class AuctionWinnerResponse {

	private Long auctionId;
	private Long winnerId;
	private BigDecimal winningBid;

	public AuctionWinnerResponse() {
	}

	public AuctionWinnerResponse(
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

	public Long getWinnerId() {
		return winnerId;
	}

	public BigDecimal getWinningBid() {
		return winningBid;
	}
}