package de.t1dev.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;

@SpringBootApplication
public class SpringdemoApplication {
    public static void main(final String[] args) {
        SpringApplication.run(SpringdemoApplication.class, args);
    }
}
