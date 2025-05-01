package com.example.messagequeue.step10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication

public class MessageQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageQueueApplication.class, args);
    }

}
