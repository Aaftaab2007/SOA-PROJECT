package com.bidvelocity.auction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AuctionUpdateRequest {

	@NotBlank
	private String title;

	private String description;

	@NotNull
	@DecimalMin(value = "0.01")
	private BigDecimal startingPrice;

	@NotNull
	@DecimalMin(value = "0.01")
	private BigDecimal minimumBidIncrement;

	@NotNull
	@Future
	private LocalDateTime startTime;

	@NotNull
	@Future
	private LocalDateTime endTime;

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

		this.minimumBidIncrement =
				minimumBidIncrement;
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
}