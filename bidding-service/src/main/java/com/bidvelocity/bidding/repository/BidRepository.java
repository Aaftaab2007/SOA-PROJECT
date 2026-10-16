package com.bidvelocity.bidding.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bidvelocity.bidding.entity.Bid;
import com.bidvelocity.bidding.entity.BidStatus;

public interface BidRepository
		extends JpaRepository<Bid, Long> {

	List<Bid> findByAuctionIdOrderByBidTimeDesc(
			Long auctionId);

	List<Bid> findByAuctionIdAndStatusOrderByAmountDesc(
			Long auctionId,
			BidStatus status);

	Optional<Bid> findTopByAuctionIdAndStatusOrderByAmountDesc(
			Long auctionId,
			BidStatus status);
}