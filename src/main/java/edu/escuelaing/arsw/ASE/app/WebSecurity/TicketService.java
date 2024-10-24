package edu.escuelaing.arsw.ase.app.webSecurity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.springframework.stereotype.Service;

/**
 * Service class for managing tickets.
 */
@Service
public class TicketService {

    private final Map<String, TicketInfo> ticketStore = new HashMap<>();
    private final Random random = new Random();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int TICKET_LENGTH = 10;

    /**
     * Verifies if a ticket is valid.
     *
     * @param ticket The ticket to validate
     * @return True if the ticket is valid, false otherwise
     */
    public boolean isValid(String ticket) {
        if (ticketStore.containsKey(ticket)) {
            TicketInfo ticketInfo = ticketStore.get(ticket);
            if (ticketInfo != null && !ticketInfo.isExpired()) {
                return true;
            } else {
                ticketStore.remove(ticket);
            }
        }
        return false;
    }

    /**
     * Generates a new ticket with a random string.
     *
     * @return The generated ticket
     */
    public String generateTicket() {
        String ticket = generateUniqueTicket();
        TicketInfo ticketInfo = new TicketInfo();
        ticketStore.put(ticket, ticketInfo);
        return ticket;
    }

    /**
     * Removes a ticket from the store.
     *
     * @param ticket The ticket to remove
     */
    public void removeTicket(String ticket) {
        ticketStore.remove(ticket);
    }

    private String generateUniqueTicket() {
        StringBuilder sb = new StringBuilder(TICKET_LENGTH);
        for (int i = 0; i < TICKET_LENGTH; i++) {
            char randomChar = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private static class TicketInfo {
        private final long expirationTime;

        /**
         * Constructs a new TicketInfo with an expiration time.
         */
        public TicketInfo() {
            this.expirationTime = System.currentTimeMillis() + 10 * 60 * 1000;
        }

        /**
         * Checks if the ticket is expired.
         *
         * @return True if the ticket is expired, false otherwise
         */
        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }
}