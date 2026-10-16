package com.bidvelocity.bidding.exception;

public class AuctionClosedException
		extends RuntimeException {

	public AuctionClosedException(String message) {
		super(message);
	}
}