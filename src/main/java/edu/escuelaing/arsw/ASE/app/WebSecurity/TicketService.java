package edu.escuelaing.arsw.ASE.app.WebSecurity;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.springframework.stereotype.Service;

/**
 * Service class for managing tickets.
 */
@Service
public class TicketService {

    private Map<String, TicketInfo> ticketStore = new HashMap<>();

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
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private static class TicketInfo {
        private long expirationTime;

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