package com.bidvelocity.bidding.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AuctionNotFoundException.class)
	public ResponseEntity<Map<String, Object>>
	handleAuctionNotFound(
			AuctionNotFoundException ex) {

		return buildResponse(
				HttpStatus.NOT_FOUND,
				ex.getMessage());
	}

	@ExceptionHandler(AuctionClosedException.class)
	public ResponseEntity<Map<String, Object>>
	handleAuctionClosed(
			AuctionClosedException ex) {

		return buildResponse(
				HttpStatus.CONFLICT,
				ex.getMessage());
	}

	@ExceptionHandler(InvalidBidException.class)
	public ResponseEntity<Map<String, Object>>
	handleInvalidBid(
			InvalidBidException ex) {

		return buildResponse(
				HttpStatus.BAD_REQUEST,
				ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>>
	handleGeneralException(Exception ex) {

		return buildResponse(
				HttpStatus.INTERNAL_SERVER_ERROR,
				"An unexpected error occurred");
	}

	private ResponseEntity<Map<String, Object>>
	buildResponse(
			HttpStatus status,
			String message) {

		Map<String, Object> response =
				new LinkedHashMap<>();

		response.put(
				"timestamp",
				LocalDateTime.now());

		response.put(
				"status",
				status.value());

		response.put(
				"error",
				status.getReasonPhrase());

		response.put(
				"message",
				message);

		return ResponseEntity
				.status(status)
				.body(response);
	}
}