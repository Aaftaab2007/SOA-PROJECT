package com.bidvelocity.bidding.service;
import com.bidvelocity.bidding.dto.CurrentBidResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bidvelocity.bidding.dto.AuctionResponse;
import com.bidvelocity.bidding.dto.AuctionWinnerResponse;
import com.bidvelocity.bidding.dto.BidRequest;
import com.bidvelocity.bidding.dto.BidResponse;
import com.bidvelocity.bidding.entity.AuctionBidState;
import com.bidvelocity.bidding.entity.Bid;
import com.bidvelocity.bidding.entity.BidStatus;
import com.bidvelocity.bidding.exception.AuctionNotFoundException;
import com.bidvelocity.bidding.repository.AuctionBidStateRepository;
import com.bidvelocity.bidding.repository.BidRepository;

@Service
public class BiddingService {

    private final BidRepository bidRepository;
    private final AuctionBidStateRepository stateRepository;
    private final BidValidationService validationService;
    private final BidBroadcastService broadcastService;

    public BiddingService(
            BidRepository bidRepository,
            AuctionBidStateRepository stateRepository,
            BidValidationService validationService,
            BidBroadcastService broadcastService) {

        this.bidRepository = bidRepository;
        this.stateRepository = stateRepository;
        this.validationService = validationService;
        this.broadcastService = broadcastService;
    }

    @Transactional
    public BidResponse placeBid(BidRequest request) {

        AuctionResponse auction =
                validationService.validateAuction(
                        request.getAuctionId());

        AuctionBidState state =
                getOrCreateState(
                        request.getAuctionId());

        validationService.validateAmount(
                request.getAmount(),
                auction,
                state);

        Bid bid = new Bid();

        bid.setAuctionId(
                request.getAuctionId());

        bid.setBidderId(
                request.getBidderId());

        bid.setAmount(
                request.getAmount());

        bid.setBidTime(
                LocalDateTime.now());

        bid.setStatus(
                BidStatus.ACCEPTED);

        Bid savedBid =
                bidRepository.save(bid);

        state.setCurrentHighestBid(
                request.getAmount());

        state.setHighestBidderId(
                request.getBidderId());

        stateRepository.save(state);

        BidResponse response =
                new BidResponse(savedBid);

        broadcastService.broadcastBid(
                request.getAuctionId(),
                response);

        return response;
    }

    @Transactional(readOnly = true)
    public CurrentBidResponse getCurrentBid(Long auctionId) {

        AuctionBidState state =
                stateRepository
                        .findById(auctionId)
                        .orElseThrow(() ->
                                new AuctionNotFoundException(
                                        "No bid state found for auction: "
                                                + auctionId));

        return new CurrentBidResponse(state);
    }

    @Transactional(readOnly = true)
    public List<BidResponse> getBidHistory(
            Long auctionId) {

        return bidRepository
                .findByAuctionIdOrderByBidTimeDesc(
                        auctionId)
                .stream()
                .map(BidResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public AuctionWinnerResponse getAuctionWinner(
            Long auctionId) {

        Bid winner =
                bidRepository
                        .findTopByAuctionIdAndStatusOrderByAmountDesc(
                                auctionId,
                                BidStatus.ACCEPTED)
                        .orElse(null);

        if (winner == null) {

            return new AuctionWinnerResponse(
                    auctionId,
                    null,
                    null);
        }

        return new AuctionWinnerResponse(
                auctionId,
                winner.getBidderId(),
                winner.getAmount());
    }

    private AuctionBidState getOrCreateState(
            Long auctionId) {

        return stateRepository
                .findByAuctionIdForUpdate(auctionId)
                .orElseGet(() -> {

                    AuctionBidState state =
                            new AuctionBidState(auctionId);

                    try {

                        return stateRepository
                                .saveAndFlush(state);

                    } catch (
                            DataIntegrityViolationException e) {

                        return stateRepository
                                .findByAuctionIdForUpdate(
                                        auctionId)
                                .orElseThrow(() -> e);
                    }
                });
    }
}