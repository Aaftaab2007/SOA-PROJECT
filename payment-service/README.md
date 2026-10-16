# BidVelocity Payment Service

## Purpose

Handles payment creation, processing and refund operations.

## Port

8084

## Responsibilities

- Create payment
- Process payment
- Track payment status
- Retrieve payment information
- Refund successful payments

## Payment States

PENDING
PROCESSING
SUCCESS
FAILED
REFUNDED

## Database

bidvelocity_payment

## Main APIs

POST /api/payments

POST /api/payments/{id}/process

GET /api/payments/{id}

GET /api/payments/auction/{auctionId}

POST /api/payments/{id}/refund

## Technology

- Spring Boot
- Spring Data JPA
- MySQL
- Eureka
- Java 21