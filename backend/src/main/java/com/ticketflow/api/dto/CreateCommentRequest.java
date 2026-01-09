package com.ticketflow.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest(
        @NotBlank @Size(max = 80) String author,
        @NotBlank @Size(max = 2000) String message
) {}
