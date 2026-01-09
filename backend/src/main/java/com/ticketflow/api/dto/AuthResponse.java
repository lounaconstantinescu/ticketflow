package com.ticketflow.api.dto;

public record AuthResponse(
        String token,
        String tokenType
) {}
