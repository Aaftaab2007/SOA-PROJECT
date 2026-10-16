package com.bidvelocity.auction.controller;

import java.util.List;
import com.bidvelocity.auction.exception.AdminAccessDeniedException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bidvelocity.auction.dto.AuctionRequest;
import com.bidvelocity.auction.dto.AuctionResponse;
import com.bidvelocity.auction.dto.AuctionUpdateRequest;
import com.bidvelocity.auction.service.AuctionService;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping
    public ResponseEntity<AuctionResponse> createAuction(
            @Valid @RequestBody AuctionRequest request,
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role) {

        checkAdmin(role);

        request.setSellerId(userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(auctionService.createAuction(request));
    }

    @GetMapping
    public ResponseEntity<List<AuctionResponse>> getAllAuctions() {
        return ResponseEntity.ok(
                auctionService.getAllAuctions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionResponse> getAuction(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                auctionService.getAuction(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<AuctionResponse>> getActiveAuctions() {
        return ResponseEntity.ok(
                auctionService.getActiveAuctions());
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<AuctionResponse>> getSellerAuctions(
            @PathVariable Long sellerId) {

        return ResponseEntity.ok(
                auctionService.getSellerAuctions(sellerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuctionResponse> updateAuction(
            @PathVariable Long id,
            @Valid @RequestBody AuctionUpdateRequest request,
            @RequestHeader("X-User-Role") String role) {

        checkAdmin(role);

        return ResponseEntity.ok(
                auctionService.updateAuction(id, request));
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<AuctionResponse> activateAuction(
            @PathVariable Long id,
            @RequestHeader("X-User-Role") String role) {

        checkAdmin(role);

        return ResponseEntity.ok(
                auctionService.activateAuction(id));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<AuctionResponse> closeAuction(
            @PathVariable Long id,
            @RequestHeader("X-User-Role") String role) {

        checkAdmin(role);

        return ResponseEntity.ok(
                auctionService.closeAuction(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<AuctionResponse> cancelAuction(
            @PathVariable Long id,
            @RequestHeader("X-User-Role") String role) {

        checkAdmin(role);

        return ResponseEntity.ok(
                auctionService.cancelAuction(id));
    }

    private void checkAdmin(String role) {

        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new AdminAccessDeniedException(
                    "Only administrators can perform this operation");
        }
    }
}