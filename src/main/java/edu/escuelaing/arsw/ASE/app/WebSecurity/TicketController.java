package edu.escuelaing.arsw.ase.app.websecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller class for handling ticket generation requests.
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    /**
     * Constructs a new TicketController with the required TicketService dependency.
     *
     * @param ticketService The service responsible for ticket operations
     */
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Endpoint to generate a new ticket for WebSocket authorization.
     *
     * @return The generated ticket as a string
     */
    @GetMapping("/generate")
    public String generateTicket() {
        // Generate a new ticket
        String ticket = ticketService.generateTicket();
        return ticket;
    }
}