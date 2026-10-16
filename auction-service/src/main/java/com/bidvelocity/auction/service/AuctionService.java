package com.bidvelocity.auction.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bidvelocity.auction.client.BiddingClient;
import com.bidvelocity.auction.client.PaymentClient;
import com.bidvelocity.auction.dto.AuctionRequest;
import com.bidvelocity.auction.dto.AuctionResponse;
import com.bidvelocity.auction.dto.AuctionUpdateRequest;
import com.bidvelocity.auction.dto.BiddingWinnerResponse;
import com.bidvelocity.auction.dto.PaymentRequest;
import com.bidvelocity.auction.dto.PaymentResponse;
import com.bidvelocity.auction.entity.Auction;
import com.bidvelocity.auction.entity.AuctionStatus;
import com.bidvelocity.auction.exception.AuctionNotFoundException;
import com.bidvelocity.auction.exception.InvalidAuctionException;
import com.bidvelocity.auction.repository.AuctionRepository;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final BiddingClient biddingClient;
    private final PaymentClient paymentClient;

    public AuctionService(
            AuctionRepository auctionRepository,
            BiddingClient biddingClient,
            PaymentClient paymentClient) {

        this.auctionRepository = auctionRepository;
        this.biddingClient = biddingClient;
        this.paymentClient = paymentClient;
    }

    @Transactional
    public AuctionResponse createAuction(AuctionRequest request) {

        validateTimes(
                request.getStartTime(),
                request.getEndTime());

        validatePrices(
                request.getStartingPrice(),
                request.getMinimumBidIncrement());

        Auction auction = new Auction();

        auction.setTitle(request.getTitle());
        auction.setDescription(request.getDescription());
        auction.setSellerId(request.getSellerId());
        auction.setStartingPrice(request.getStartingPrice());
        auction.setMinimumBidIncrement(
                request.getMinimumBidIncrement());
        auction.setStartTime(request.getStartTime());
        auction.setEndTime(request.getEndTime());

        if (request.getStartTime().isAfter(LocalDateTime.now())) {
            auction.setStatus(AuctionStatus.SCHEDULED);
        } else {
            auction.setStatus(AuctionStatus.ACTIVE);
        }

        return new AuctionResponse(
                auctionRepository.save(auction));
    }

    @Transactional(readOnly = true)
    public AuctionResponse getAuction(Long id) {

        Auction auction = findAuction(id);

        return new AuctionResponse(auction);
    }

    @Transactional(readOnly = true)
    public List<AuctionResponse> getAllAuctions() {

        return auctionRepository.findAll()
                .stream()
                .map(AuctionResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AuctionResponse> getActiveAuctions() {

        return auctionRepository
                .findByStatus(AuctionStatus.ACTIVE)
                .stream()
                .map(AuctionResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AuctionResponse> getSellerAuctions(Long sellerId) {

        return auctionRepository
                .findBySellerId(sellerId)
                .stream()
                .map(AuctionResponse::new)
                .toList();
    }

    @Transactional
    public AuctionResponse updateAuction(
            Long id,
            AuctionUpdateRequest request) {

        Auction auction = findAuction(id);

        if (auction.getStatus() != AuctionStatus.SCHEDULED) {
            throw new InvalidAuctionException(
                    "Only scheduled auctions can be updated");
        }

        validateTimes(
                request.getStartTime(),
                request.getEndTime());

        validatePrices(
                request.getStartingPrice(),
                request.getMinimumBidIncrement());

        auction.setTitle(request.getTitle());
        auction.setDescription(request.getDescription());
        auction.setStartingPrice(request.getStartingPrice());
        auction.setMinimumBidIncrement(
                request.getMinimumBidIncrement());
        auction.setStartTime(request.getStartTime());
        auction.setEndTime(request.getEndTime());

        return new AuctionResponse(
                auctionRepository.save(auction));
    }

    @Transactional
    public AuctionResponse activateAuction(Long id) {

        Auction auction = findAuction(id);

        if (auction.getStatus() != AuctionStatus.SCHEDULED) {
            throw new InvalidAuctionException(
                    "Only scheduled auctions can be activated");
        }

        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(auction.getStartTime())) {
            throw new InvalidAuctionException(
                    "Auction start time has not been reached");
        }

        if (!now.isBefore(auction.getEndTime())) {
            throw new InvalidAuctionException(
                    "Auction end time has already passed");
        }

        auction.setStatus(AuctionStatus.ACTIVE);

        return new AuctionResponse(
                auctionRepository.save(auction));
    }

    @Transactional
    public AuctionResponse closeAuction(Long id) {

        Auction auction = findAuction(id);

        if (auction.getStatus() != AuctionStatus.ACTIVE) {
            throw new InvalidAuctionException(
                    "Only active auctions can be closed");
        }

        processAuctionWinner(auction);

        auction.setStatus(AuctionStatus.CLOSED);

        return new AuctionResponse(
                auctionRepository.save(auction));
    }

    @Transactional
    public AuctionResponse cancelAuction(Long id) {

        Auction auction = findAuction(id);

        if (auction.getStatus() == AuctionStatus.CLOSED) {
            throw new InvalidAuctionException(
                    "Closed auction cannot be cancelled");
        }

        if (auction.getStatus() == AuctionStatus.CANCELLED) {
            throw new InvalidAuctionException(
                    "Auction is already cancelled");
        }

        auction.setStatus(AuctionStatus.CANCELLED);

        return new AuctionResponse(
                auctionRepository.save(auction));
    }

    @Transactional
    public void activateScheduledAuctions() {

        LocalDateTime now = LocalDateTime.now();

        List<Auction> auctions =
                auctionRepository.findByStatusAndStartTimeBefore(
                        AuctionStatus.SCHEDULED,
                        now);

        for (Auction auction : auctions) {

            if (now.isBefore(auction.getEndTime())) {
                auction.setStatus(AuctionStatus.ACTIVE);
            } else {
                auction.setStatus(AuctionStatus.CLOSED);
            }
        }

        if (!auctions.isEmpty()) {
            auctionRepository.saveAll(auctions);
        }
    }

    @Transactional(readOnly = true)
    public List<Auction> findExpiredActiveAuctions() {

        return auctionRepository.findByStatusAndEndTimeBefore(
                AuctionStatus.ACTIVE,
                LocalDateTime.now());
    }

    private void processAuctionWinner(Auction auction) {

        try {

            BiddingWinnerResponse winner =
                    biddingClient.getAuctionWinner(auction.getId());

            if (winner == null ||
                    winner.getWinnerId() == null ||
                    winner.getWinningBid() == null) {

                return;
            }

            auction.setWinnerId(winner.getWinnerId());
            auction.setWinningBid(winner.getWinningBid());

            PaymentRequest paymentRequest =
                    new PaymentRequest(
                            auction.getId(),
                            winner.getWinnerId(),
                            winner.getWinningBid());

            PaymentResponse payment = paymentClient.createPayment(paymentRequest);

            if (payment != null && payment.getId() != null) {
                paymentClient.processPayment(
                        payment.getId(),
                        winner.getWinnerId());
            }

        } catch (Exception e) {

            throw new InvalidAuctionException(
                    "Unable to complete auction settlement");
        }
    }

    private Auction findAuction(Long id) {

        return auctionRepository.findById(id)
                .orElseThrow(() ->
                        new AuctionNotFoundException(
                                "Auction not found with id: " + id));
    }

    private void validateTimes(
            LocalDateTime startTime,
            LocalDateTime endTime) {

        if (startTime == null || endTime == null) {
            throw new InvalidAuctionException(
                    "Start time and end time are required");
        }

        if (!endTime.isAfter(startTime)) {
            throw new InvalidAuctionException(
                    "End time must be after start time");
        }
    }

    private void validatePrices(
            BigDecimal startingPrice,
            BigDecimal minimumBidIncrement) {

        if (startingPrice == null ||
                startingPrice.compareTo(BigDecimal.ZERO) <= 0) {

            throw new InvalidAuctionException(
                    "Starting price must be greater than zero");
        }

        if (minimumBidIncrement == null ||
                minimumBidIncrement.compareTo(BigDecimal.ZERO) <= 0) {

            throw new InvalidAuctionException(
                    "Minimum bid increment must be greater than zero");
        }
    }
}