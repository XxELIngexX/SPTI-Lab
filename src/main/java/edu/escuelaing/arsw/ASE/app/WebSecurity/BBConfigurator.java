package edu.escuelaing.arsw.ase.app.websecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Configuration class for WebSocket server endpoint and scheduling.
 */
@Configuration
@EnableScheduling
public class BBConfigurator {
    
    /**
     * Bean definition for the {@link ServerEndpointExporter}.
     * This bean enables WebSocket server endpoint registration.
     *
     * @return {@link ServerEndpointExporter} instance
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}