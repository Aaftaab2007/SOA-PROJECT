package com.bidvelocity.bidding.dto;

import java.math.BigDecimal;

import com.bidvelocity.bidding.entity.AuctionBidState;

public class CurrentBidResponse {

	private Long auctionId;
	private BigDecimal currentHighestBid;
	private Long highestBidderId;

	public CurrentBidResponse() {
	}

	public CurrentBidResponse(
			AuctionBidState state) {

		this.auctionId =
				state.getAuctionId();

		this.currentHighestBid =
				state.getCurrentHighestBid();

		this.highestBidderId =
				state.getHighestBidderId();
	}

	public Long getAuctionId() {
		return auctionId;
	}

	public BigDecimal getCurrentHighestBid() {
		return currentHighestBid;
	}

	public Long getHighestBidderId() {
		return highestBidderId;
	}
}