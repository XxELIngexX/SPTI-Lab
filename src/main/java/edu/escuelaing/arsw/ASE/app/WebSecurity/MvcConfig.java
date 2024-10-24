package edu.escuelaing.arsw.ASE.app.WebSecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for MVC settings.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Configures view controllers to handle specific URLs.
     *
     * @param registry ViewControllerRegistry instance to register view controllers
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("error");
    }
}