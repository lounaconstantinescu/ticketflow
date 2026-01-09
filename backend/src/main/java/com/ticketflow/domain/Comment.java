package com.ticketflow.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "tf_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Ticket ticket;

    @Column(nullable = false, length = 80)
    private String author;

    @Column(nullable = false, length = 2000)
    private String message;

    @Column(nullable = false)
    private Instant createdAt;

    protected Comment() {}

    public Comment(Ticket ticket, String author, String message) {
        this.ticket = ticket;
        this.author = author;
        this.message = message;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public Ticket getTicket() { return ticket; }
    public String getAuthor() { return author; }
    public String getMessage() { return message; }
    public Instant getCreatedAt() { return createdAt; }
}
