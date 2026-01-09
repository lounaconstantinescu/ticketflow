package com.ticketflow.service;

import com.ticketflow.domain.Ticket;
import com.ticketflow.domain.TicketPriority;
import com.ticketflow.domain.TicketStatus;
import com.ticketflow.repo.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @Test
    void create_shouldPersistTicketWithOpenStatus() {
        TicketRepository repo = mock(TicketRepository.class);
        TicketService service = new TicketService(repo);

        when(repo.save(any(Ticket.class))).thenAnswer(inv -> inv.getArgument(0));

        Ticket created = service.create("T1", "D1", TicketPriority.P2);

        assertThat(created.getTitle()).isEqualTo("T1");
        assertThat(created.getStatus()).isEqualTo(TicketStatus.OPEN);
        verify(repo, times(1)).save(any(Ticket.class));
    }

    @Test
    void list_shouldDelegateToRepo_whenNoFilters() {
        TicketRepository repo = mock(TicketRepository.class);
        TicketService service = new TicketService(repo);

        service.list(null, null, PageRequest.of(0, 10));

        verify(repo).findAll(any(PageRequest.class));
        verifyNoMoreInteractions(repo);
    }
}
