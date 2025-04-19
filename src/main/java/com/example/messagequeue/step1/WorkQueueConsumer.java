package com.example.messagequeue.step1;

import org.springframework.stereotype.Component;

@Component
public class WorkQueueConsumer {
    public void workQueueTask(String message){
        System.out.println("[#] Receive: " + message);
    }
}
