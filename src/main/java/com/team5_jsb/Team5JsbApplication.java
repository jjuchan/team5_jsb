package com.team5_jsb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableJpaAuditing
public class Team5JsbApplication {

    public static void main(String[] args) {
        SpringApplication.run(Team5JsbApplication.class, args);
    }

}
