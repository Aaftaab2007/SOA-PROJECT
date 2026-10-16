# BidVelocity Auction Service

## Purpose

Manages the complete auction lifecycle.

## Port

8082

## Responsibilities

- Create auctions
- Update scheduled auctions
- Activate auctions
- Close auctions
- Cancel auctions
- Track auction status
- Determine auction winner
- Trigger payment settlement

## Auction States

SCHEDULED
ACTIVE
CLOSED
CANCELLED

## Database

bidvelocity_auction

## Service Communication

Auction Service communicates with:

- Bidding Service
- Payment Service

using OpenFeign and Eureka.

## Technology

- Spring Boot
- Spring Data JPA
- MySQL
- Eureka
- OpenFeign
- Scheduling
- Java 21