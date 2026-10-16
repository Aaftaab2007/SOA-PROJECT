package com.bidvelocity.auction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bidvelocity.auction.dto.BiddingWinnerResponse;

@FeignClient(name = "bidding-service")
public interface BiddingClient {

    @GetMapping("/api/bids/auction/{auctionId}/winner")
    BiddingWinnerResponse getAuctionWinner(@PathVariable("auctionId") Long auctionId);
}