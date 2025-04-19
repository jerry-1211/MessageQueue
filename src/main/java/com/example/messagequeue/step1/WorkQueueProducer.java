package com.example.messagequeue.step1;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class WorkQueueProducer {
    private final RabbitTemplate rabbitTemplate;

    public WorkQueueProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendWorkQueue(String message){
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, message);
        System.out.println("[#] Sent : " + message);
    }
}
