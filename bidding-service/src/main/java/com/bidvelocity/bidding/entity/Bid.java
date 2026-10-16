package com.bidvelocity.bidding.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(
	name = "bids",
	indexes = {
		@Index(name = "idx_bid_auction", columnList = "auctionId"),
		@Index(name = "idx_bid_bidder", columnList = "bidderId"),
		@Index(name = "idx_bid_time", columnList = "bidTime")
	}
)
public class Bid {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long auctionId;

	@Column(nullable = false)
	private Long bidderId;

	@Column(nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;

	@Column(nullable = false)
	private LocalDateTime bidTime;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BidStatus status;

	public Bid() {
	}

	public Long getId() {
		return id;
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

	public BidStatus getStatus() {
		return status;
	}

	public void setStatus(BidStatus status) {
		this.status = status;
	}
}