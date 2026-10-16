package com.bidvelocity.bidding.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.bidvelocity.bidding.dto.BidResponse;

@Service
public class BidBroadcastService {

    private final SimpMessagingTemplate messagingTemplate;

    public BidBroadcastService(
            SimpMessagingTemplate messagingTemplate) {

        this.messagingTemplate = messagingTemplate;
    }

    public void broadcastBid(
            Long auctionId,
            BidResponse response) {

        messagingTemplate.convertAndSend(
                "/topic/auction/" + auctionId,
                response);
    }
}