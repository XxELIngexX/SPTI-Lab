package edu.escuelaing.arsw.ase.app.websecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for setting up web security using Spring Security.
 * This class enables web security and configures HTTP security, login, and user
 * details service.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    /**
     * Configures the {@link HttpSecurity} to define security policies.
     * 
     * @param http The {@link HttpSecurity} instance to configure
     * @return The configured {@link SecurityFilterChain}
     * @throws Exception If an error occurs while configuring the security filter
     *                   chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("index").permitAll() // Allow access to the index page
                                                        // without authentication
                .anyRequest().authenticated() // Require authentication for all other
                                                // requests
            )
            .formLogin((form) -> form
                .loginPage("/login") // Specify the custom login page
                .permitAll() // Allow access to the login page without authentication
            )
            .logout((logout) -> logout.permitAll()); // Allow logout for all users
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines an in-memory user details service with two users: "user" and "admin".
     * 
     * @return An instance of {@link InMemoryUserDetailsManager} with predefined
     *         users
     */
    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = passwordEncoder();
        UserDetails user1 = User.builder()
            .username("user")
            .password(encoder.encode(System.getenv("USER_PASSWORD"))) // Obtener la contraseña de la variable de entorno
            .roles("USER")
            .build();
        UserDetails user2 = User.builder()
            .username("admin")
            .password(encoder.encode(System.getenv("ADMIN_PASSWORD"))) // Obtener la contraseña de la variable de entorno
            .roles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
}
