package com.ticketflow.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ticketflow.security.jwt")
public record JwtProperties(
        String secret,
        String issuer,
        long ttlMinutes
) {}
