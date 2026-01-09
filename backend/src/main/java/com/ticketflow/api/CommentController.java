package com.ticketflow.api;

import com.ticketflow.api.dto.CommentDto;
import com.ticketflow.api.dto.CreateCommentRequest;
import com.ticketflow.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets/{ticketId}/comments")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public List<CommentDto> list(@PathVariable Long ticketId) {
        return service.listForTicket(ticketId).stream()
                .map(c -> new CommentDto(c.getId(), c.getAuthor(), c.getMessage(), c.getCreatedAt()))
                .toList();
    }

    @PostMapping
    public ResponseEntity<CommentDto> add(@PathVariable Long ticketId, @Valid @RequestBody CreateCommentRequest req) {
        var c = service.add(ticketId, req.author(), req.message());
        return ResponseEntity.ok(new CommentDto(c.getId(), c.getAuthor(), c.getMessage(), c.getCreatedAt()));
    }
}
