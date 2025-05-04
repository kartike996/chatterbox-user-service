package com.chatterbox.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ChatterboxUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatterboxUserServiceApplication.class, args);
    }
}