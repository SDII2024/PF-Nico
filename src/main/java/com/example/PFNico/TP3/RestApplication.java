package com.example.PFNico.TP3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RestApplication {
    public static void main(String... args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(com.example.PFNico.TP3.RestApplication.class, args);
    }
}
