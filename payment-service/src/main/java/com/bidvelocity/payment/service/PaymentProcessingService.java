package com.bidvelocity.payment.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bidvelocity.payment.dto.PaymentRequest;
import com.bidvelocity.payment.dto.PaymentResponse;
import com.bidvelocity.payment.entity.Payment;
import com.bidvelocity.payment.entity.PaymentStatus;
import com.bidvelocity.payment.exception.PaymentAccessDeniedException;
import com.bidvelocity.payment.exception.PaymentNotFoundException;
import com.bidvelocity.payment.exception.PaymentProcessingException;
import com.bidvelocity.payment.repository.PaymentRepository;

@Service
public class PaymentProcessingService {

    private final PaymentRepository paymentRepository;

    public PaymentProcessingService(
            PaymentRepository paymentRepository) {

        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public PaymentResponse createPayment(
            PaymentRequest request) {

        return paymentRepository
                .findByAuctionId(request.getAuctionId())
                .map(PaymentResponse::new)
                .orElseGet(() -> {

                    Payment payment = new Payment();

                    payment.setAuctionId(
                            request.getAuctionId());

                    payment.setWinnerId(
                            request.getWinnerId());

                    payment.setAmount(
                            request.getAmount());

                    payment.setPaymentReference(
                            generatePaymentReference());

                    payment.setStatus(
                            PaymentStatus.PENDING);

                    payment.setCreatedAt(
                            LocalDateTime.now());

                    return new PaymentResponse(
                            paymentRepository.save(payment));
                });
    }

    @Transactional
    public PaymentResponse processPayment(
            Long id,
            Long userId) {

        Payment payment = findPayment(id);

        authorizeWinner(payment, userId);

        if (payment.getStatus() ==
                PaymentStatus.SUCCESS) {

            return new PaymentResponse(payment);
        }

        if (payment.getStatus() ==
                PaymentStatus.REFUNDED) {

            throw new PaymentProcessingException(
                    "Refunded payment cannot be processed");
        }

        payment.setStatus(
                PaymentStatus.PROCESSING);

        paymentRepository.save(payment);

        payment.setStatus(
                PaymentStatus.SUCCESS);

        payment.setProcessedAt(
                LocalDateTime.now());

        return new PaymentResponse(
                paymentRepository.save(payment));
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPayment(
            Long id,
            Long userId) {

        Payment payment = findPayment(id);

        authorizeWinner(payment, userId);

        return new PaymentResponse(payment);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByAuction(
            Long auctionId,
            Long userId) {

        Payment payment =
                paymentRepository
                        .findByAuctionId(auctionId)
                        .orElseThrow(() ->
                                new PaymentNotFoundException(
                                        "Payment not found for auction: "
                                                + auctionId));

        authorizeWinner(payment, userId);

        return new PaymentResponse(payment);
    }

    @Transactional
    public PaymentResponse refundPayment(
            Long id,
            Long userId) {

        Payment payment = findPayment(id);

        authorizeWinner(payment, userId);

        if (payment.getStatus() !=
                PaymentStatus.SUCCESS) {

            throw new PaymentProcessingException(
                    "Only successful payments can be refunded");
        }

        payment.setStatus(
                PaymentStatus.REFUNDED);

        return new PaymentResponse(
                paymentRepository.save(payment));
    }

    private void authorizeWinner(
            Payment payment,
            Long userId) {

        if (payment.getWinnerId() == null ||
                !payment.getWinnerId().equals(userId)) {

            throw new PaymentAccessDeniedException(
                    "You are not authorized to access this payment");
        }
    }

    private Payment findPayment(Long id) {

        return paymentRepository
                .findById(id)
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "Payment not found with id: "
                                        + id));
    }

    private String generatePaymentReference() {

        return "PAY-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 12)
                        .toUpperCase();
    }
}