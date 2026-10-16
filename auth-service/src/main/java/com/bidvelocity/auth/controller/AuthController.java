package com.bidvelocity.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.bidvelocity.auth.dto.AuthResponse;
import com.bidvelocity.auth.dto.LoginRequest;
import com.bidvelocity.auth.dto.RegisterRequest;
import com.bidvelocity.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(
			@Valid @RequestBody RegisterRequest request) {

		authService.register(request);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body("User registered successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(
			@Valid @RequestBody LoginRequest request) {

		return ResponseEntity.ok(
				authService.login(request));
	}
}