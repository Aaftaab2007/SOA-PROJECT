package com.bidvelocity.payment.dto;

import java.math.BigDecimal;

public class AuctionResponse {

	private Long id;
	private Long winnerId;
	private BigDecimal winningBid;
	private String status;

	public Long getId() {
		return id;
	}

	public Long getWinnerId() {
		return winnerId;
	}

	public BigDecimal getWinningBid() {
		return winningBid;
	}

	public String getStatus() {
		return status;
	}
}