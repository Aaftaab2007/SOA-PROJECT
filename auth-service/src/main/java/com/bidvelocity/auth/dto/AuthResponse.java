package com.bidvelocity.auth.dto;

public class AuthResponse {

    private Long id;
    private String token;
    private String username;
    private String role;

    public AuthResponse() {
    }

    public AuthResponse(Long id, String token, String username, String role) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}