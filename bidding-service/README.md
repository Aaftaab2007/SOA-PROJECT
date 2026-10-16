# BidVelocity Bidding Service

## Purpose

Handles bidding operations and real-time bid updates.

## Port

8083

## Responsibilities

- Place bids
- Validate bid amounts
- Maintain highest bid
- Maintain bid history
- Determine auction winner
- Handle concurrent bidding
- Broadcast real-time bid updates

## Database

bidvelocity_bidding

## WebSocket

Endpoint:

/ws

Subscription:

/topic/auction/{auctionId}

## Main APIs

POST /api/bids

GET /api/bids/auction/{auctionId}/current

GET /api/bids/auction/{auctionId}/history

GET /api/bids/auction/{auctionId}/winner

## Service Communication

Bidding Service communicates with Auction Service using OpenFeign.

## Technology

- Spring Boot
- Spring Data JPA
- OpenFeign
- WebSocket
- STOMP
- MySQL
- Eureka
- Java 21