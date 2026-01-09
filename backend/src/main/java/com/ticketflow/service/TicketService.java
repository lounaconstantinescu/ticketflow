package com.ticketflow.service;

import com.ticketflow.common.NotFoundException;
import com.ticketflow.domain.Ticket;
import com.ticketflow.domain.TicketPriority;
import com.ticketflow.domain.TicketStatus;
import com.ticketflow.repo.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository repo;

    public TicketService(TicketRepository repo) {
        this.repo = repo;
    }

    public Ticket create(String title, String description, TicketPriority priority) {
        return repo.save(new Ticket(title, description, priority));
    }

    public Ticket get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Ticket " + id + " not found"));
    }

    public Page<Ticket> list(TicketStatus status, TicketPriority priority, Pageable pageable) {
        if (status != null && priority != null) {
            return repo.findByStatusAndPriority(status, priority, pageable);
        }
        if (status != null) {
            return repo.findByStatus(status, pageable);
        }
        if (priority != null) {
            return repo.findByPriority(priority, pageable);
        }
        return repo.findAll(pageable);
    }

    public Ticket update(Long id, String title, String description, TicketStatus status, TicketPriority priority) {
        Ticket t = get(id);
        if (title != null) t.setTitle(title);
        if (description != null) t.setDescription(description);
        if (status != null) t.setStatus(status);
        if (priority != null) t.setPriority(priority);
        return repo.save(t);
    }
}
