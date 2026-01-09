package com.ticketflow.api.dto;

import java.time.Instant;

public record CommentDto(
        Long id,
        String author,
        String message,
        Instant createdAt
) {}
