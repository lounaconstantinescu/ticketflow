package com.ticketflow.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TicketControllerIT {

    @Autowired
    MockMvc mvc;

    @Test
    void list_shouldReturn200() throws Exception {
        mvc.perform(get("/api/tickets"))
                .andExpect(status().isOk());
    }

    @Test
    void create_shouldReturn200() throws Exception {
        String body = "{ \"title\": \"Ticket CI\", \"description\": \"Créé en test d'intégration\", \"priority\": \"P2\" }";
        mvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Ticket CI"));
    }
}
