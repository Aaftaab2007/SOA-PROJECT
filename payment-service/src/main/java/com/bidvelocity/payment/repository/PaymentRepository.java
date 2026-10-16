package com.bidvelocity.payment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bidvelocity.payment.entity.Payment;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    Optional<Payment> findByAuctionId(Long auctionId);

    Optional<Payment> findByPaymentReference(
            String paymentReference);

    boolean existsByAuctionId(Long auctionId);
}