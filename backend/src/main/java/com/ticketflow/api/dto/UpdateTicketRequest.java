package com.ticketflow.api.dto;

import com.ticketflow.domain.TicketPriority;
import com.ticketflow.domain.TicketStatus;
import jakarta.validation.constraints.Size;

public record UpdateTicketRequest(
        @Size(max = 120) String title,
        @Size(max = 4000) String description,
        TicketStatus status,
        TicketPriority priority
) {}
