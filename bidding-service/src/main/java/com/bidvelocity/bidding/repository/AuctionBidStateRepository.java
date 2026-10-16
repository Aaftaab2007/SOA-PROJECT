package com.bidvelocity.bidding.repository;

import java.util.Optional;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bidvelocity.bidding.entity.AuctionBidState;

public interface AuctionBidStateRepository
		extends JpaRepository<AuctionBidState, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("""
			select s
			from AuctionBidState s
			where s.auctionId = :auctionId
			""")
	Optional<AuctionBidState> findByAuctionIdForUpdate(
			@Param("auctionId") Long auctionId);
}