package com.aptio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.aptio.repository")
@EntityScan(basePackages = "com.aptio.model")
public class AptioApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AptioApiApplication.class, args);
    }

}
