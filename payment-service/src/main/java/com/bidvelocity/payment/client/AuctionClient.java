package com.bidvelocity.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bidvelocity.payment.dto.AuctionResponse;

@FeignClient(name = "auction-service")
public interface AuctionClient {

	@GetMapping("/api/auctions/{id}")
	AuctionResponse getAuction(
			@PathVariable("id") Long id);
}