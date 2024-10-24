package edu.escuelaing.arsw.ASE.app.WebSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller class for handling ticket generation requests.
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

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