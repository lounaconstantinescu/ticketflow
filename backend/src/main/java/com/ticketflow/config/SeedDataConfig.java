package com.ticketflow.config;

import com.ticketflow.domain.TicketPriority;
import com.ticketflow.domain.User;
import com.ticketflow.repo.TicketRepository;
import com.ticketflow.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SeedDataConfig {

    @Bean
    CommandLineRunner seed(
            @Value("${ticketflow.seed.enabled:false}") boolean enabled,
            UserRepository userRepo,
            TicketRepository ticketRepo,
            PasswordEncoder encoder
    ) {
        return args -> {
            if (!enabled) return;

            userRepo.findByUsername("admin").orElseGet(() ->
                    userRepo.save(new User("admin", encoder.encode("admin"), "ADMIN"))
            );

            if (ticketRepo.count() == 0) {
                ticketRepo.save(new com.ticketflow.domain.Ticket("Première demande", "Ticket de démonstration.", TicketPriority.P2));
                ticketRepo.save(new com.ticketflow.domain.Ticket("Bug UI", "Le bouton ne répond pas.", TicketPriority.P1));
                ticketRepo.save(new com.ticketflow.domain.Ticket("Demande d'évolution", "Ajouter un filtre par priorité.", TicketPriority.P3));
            }
        };
    }
}
