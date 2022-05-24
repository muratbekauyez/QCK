package com.example.qck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class QckApplication {

    public static void main(String[] args) {
        SpringApplication.run(QckApplication.class, args);
    }

}
