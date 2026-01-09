package com.ticketflow.service;

import com.ticketflow.common.NotFoundException;
import com.ticketflow.domain.Comment;
import com.ticketflow.domain.Ticket;
import com.ticketflow.repo.CommentRepository;
import com.ticketflow.repo.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;

    public CommentService(TicketRepository ticketRepository, CommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
    }

    public List<Comment> listForTicket(Long ticketId) {
        Ticket t = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket " + ticketId + " not found"));
        return commentRepository.findByTicketOrderByCreatedAtAsc(t);
    }

    public Comment add(Long ticketId, String author, String message) {
        Ticket t = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket " + ticketId + " not found"));
        return commentRepository.save(new Comment(t, author, message));
    }
}
