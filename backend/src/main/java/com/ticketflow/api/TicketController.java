package com.ticketflow.api;

import com.ticketflow.api.dto.CreateTicketRequest;
import com.ticketflow.api.dto.TicketDto;
import com.ticketflow.api.dto.UpdateTicketRequest;
import com.ticketflow.domain.Ticket;
import com.ticketflow.domain.TicketPriority;
import com.ticketflow.domain.TicketStatus;
import com.ticketflow.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping
    public Page<TicketDto> list(
            @RequestParam(name = "status", required = false) TicketStatus status,
            @RequestParam(name = "priority", required = false) TicketPriority priority,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return service.list(status, priority, PageRequest.of(page, size))
                .map(TicketController::toDto);
    }

    @PostMapping
    public ResponseEntity<TicketDto> create(@Valid @RequestBody CreateTicketRequest req) {
        Ticket t = service.create(req.title(), req.description(), req.priority());
        return ResponseEntity.ok(toDto(t));
    }

    @GetMapping("/{id}")
    public TicketDto get(@PathVariable Long id) {
        return toDto(service.get(id));
    }

    @PutMapping("/{id}")
    public TicketDto update(@PathVariable Long id, @Valid @RequestBody UpdateTicketRequest req) {
        Ticket t = service.update(id, req.title(), req.description(), req.status(), req.priority());
        return toDto(t);
    }

    static TicketDto toDto(Ticket t) {
        return new TicketDto(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getStatus(),
                t.getPriority(),
                t.getCreatedAt(),
                t.getUpdatedAt()
        );
    }
}
