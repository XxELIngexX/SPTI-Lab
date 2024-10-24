package edu.escuelaing.arsw.ase.app.websecurity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling status requests.
 */
@RestController
public class DrawingServiceController {
    
    /**
     * Returns a JSON response with the server status.
     *
     * @return JSON response with the server status
     */
    @RequestMapping(value = "/status", method = RequestMethod.GET, produces = "application/json")
    public String status() {
        return "{\"status\":\"Greetings from Spring Boot. "
                + java.time.LocalDate.now() + ", "
                + java.time.LocalTime.now()
                + ". The server is Running!\"}";
    }
}