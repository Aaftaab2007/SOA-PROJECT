package com.bidvelocity.auction.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bidvelocity.auction.entity.Auction;
import com.bidvelocity.auction.entity.AuctionStatus;

public interface AuctionRepository
		extends JpaRepository<Auction, Long> {

	List<Auction> findByStatus(
			AuctionStatus status);

	List<Auction> findByStatusAndEndTimeBefore(
			AuctionStatus status,
			LocalDateTime time);

	List<Auction> findByStatusAndStartTimeBefore(
			AuctionStatus status,
			LocalDateTime time);

	List<Auction> findBySellerId(Long sellerId);
}