# BidVelocity API Gateway

## Purpose

Central entry point for all BidVelocity client requests.

## Port

8080

## Responsibilities

- Request routing
- JWT validation
- User identity propagation
- Load balancing
- CORS
- Request ID generation
- WebSocket routing
- Centralized gateway error handling

## Routes

/api/auth/**      -> AUTH-SERVICE
/api/auctions/**  -> AUCTION-SERVICE
/api/bids/**      -> BIDDING-SERVICE
/api/payments/**  -> PAYMENT-SERVICE
/ws/**            -> BIDDING-SERVICE

## Technology

- Spring Cloud Gateway
- Eureka
- JWT
- WebFlux
- Java 21