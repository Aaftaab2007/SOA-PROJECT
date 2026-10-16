package com.bidvelocity.auction.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AuctionClosingService {

    private final AuctionService auctionService;

    public AuctionClosingService(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @Scheduled(fixedRate = 5000)
    public void processAuctionLifecycle() {

        auctionService.activateScheduledAuctions();

        auctionService.findExpiredActiveAuctions()
                .forEach(auction ->
                        auctionService.closeAuction(
                                auction.getId()));
    }
}