package com.bidvelocity.bidding.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.bidvelocity.bidding.dto.AuctionResponse;
import com.bidvelocity.bidding.entity.AuctionBidState;
import com.bidvelocity.bidding.exception.AuctionClosedException;
import com.bidvelocity.bidding.exception.AuctionNotFoundException;
import com.bidvelocity.bidding.exception.InvalidBidException;
import com.bidvelocity.bidding.client.AuctionClient;

@Service
public class BidValidationService {

    private final AuctionClient auctionClient;

    public BidValidationService(
            AuctionClient auctionClient) {

        this.auctionClient = auctionClient;
    }

    public AuctionResponse validateAuction(
            Long auctionId) {

        AuctionResponse auction;

        try {
            auction = auctionClient.getAuction(auctionId);
        } catch (Exception e) {
            throw new AuctionNotFoundException(
                    "Auction not found with id: "
                            + auctionId);
        }

        if (auction == null) {
            throw new AuctionNotFoundException(
                    "Auction not found with id: "
                            + auctionId);
        }

        if (!"ACTIVE".equalsIgnoreCase(
                auction.getStatus())) {

            throw new AuctionClosedException(
                    "Auction is not active");
        }

        LocalDateTime now = LocalDateTime.now();

        if (auction.getStartTime() != null &&
                now.isBefore(auction.getStartTime())) {

            throw new AuctionClosedException(
                    "Auction has not started yet");
        }

        if (auction.getEndTime() != null &&
                !now.isBefore(auction.getEndTime())) {

            throw new AuctionClosedException(
                    "Auction has ended");
        }

        return auction;
    }

    public void validateAmount(
            BigDecimal amount,
            AuctionResponse auction,
            AuctionBidState state) {

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new InvalidBidException(
                    "Bid amount must be greater than zero");
        }

        BigDecimal minimumAmount;

        if (state == null ||
                state.getCurrentHighestBid() == null) {

            minimumAmount =
                    auction.getStartingPrice();

        } else {

            minimumAmount =
                    state.getCurrentHighestBid()
                            .add(auction.getMinimumBidIncrement());
        }

        if (amount.compareTo(minimumAmount) < 0) {

            throw new InvalidBidException(
                    "Bid must be at least "
                            + minimumAmount);
        }
    }
}