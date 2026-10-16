package com.bidvelocity.bidding.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bidvelocity.bidding.dto.AuctionWinnerResponse;
import com.bidvelocity.bidding.dto.BidRequest;
import com.bidvelocity.bidding.dto.BidResponse;
import com.bidvelocity.bidding.dto.CurrentBidResponse;
import com.bidvelocity.bidding.service.BiddingService;

@RestController
@RequestMapping("/api/bids")
public class BiddingController {

    private final BiddingService biddingService;

    public BiddingController(BiddingService biddingService) {
        this.biddingService = biddingService;
    }

    @PostMapping
    public ResponseEntity<BidResponse> placeBid(
            @Valid @RequestBody BidRequest request,
            @RequestHeader("X-User-Id") Long userId) {

        request.setBidderId(userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(biddingService.placeBid(request));
    }

    @GetMapping("/auction/{auctionId}/current")
    public ResponseEntity<CurrentBidResponse> getCurrentBid(
            @PathVariable Long auctionId) {

        return ResponseEntity.ok(
                biddingService.getCurrentBid(auctionId));
    }

    @GetMapping("/auction/{auctionId}/history")
    public ResponseEntity<List<BidResponse>> getBidHistory(
            @PathVariable Long auctionId) {

        return ResponseEntity.ok(
                biddingService.getBidHistory(auctionId));
    }

    @GetMapping("/auction/{auctionId}/winner")
    public ResponseEntity<AuctionWinnerResponse> getAuctionWinner(
            @PathVariable Long auctionId) {

        return ResponseEntity.ok(
                biddingService.getAuctionWinner(auctionId));
    }
}