package edu.escuelaing.arsw.ase.app.webSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Spring Boot application.
 * The {@code @SpringBootApplication} annotation denotes this class as the main entry point for the Spring Boot application.
 */
@SpringBootApplication
public class BBAppStarter {
    
    /**
     * Main method to start the Spring Boot application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BBAppStarter.class, args);
    }
}