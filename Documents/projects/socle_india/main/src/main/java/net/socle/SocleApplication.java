package net.socle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author Sai Regati
 * @version 1.0
 * @since 22-03-2022
 */


@ComponentScan(basePackages = {"net.socle.*"})
@EntityScan(basePackages = {"net.socle.*"})
@EnableJpaRepositories(basePackages = {"net.socle.*"})
@PropertySource("message.properties")
@SpringBootApplication
public class SocleApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SocleApplication.class, args);
        LOGGER.info("       BACKEND SERVER STARTED          ");
    }

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}
