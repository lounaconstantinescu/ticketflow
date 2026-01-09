package com.ticketflow.api.dto;

import com.ticketflow.domain.TicketPriority;
import com.ticketflow.domain.TicketStatus;

import java.time.Instant;

public record TicketDto(
        Long id,
        String title,
        String description,
        TicketStatus status,
        TicketPriority priority,
        Instant createdAt,
        Instant updatedAt
) {}
