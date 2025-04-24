package com.example.messagequeue.step8.step8;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderDeadLetterRetry {
    private final RabbitTemplate rabbitTemplate;

    public OrderDeadLetterRetry(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.DLQ)
    public void processDeadLetter(String message){

        try{
            System.out.println("[DLQ Received]: " + message);
            message = "success";
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ORDER_TOPIC_EXCHANGE,
                    "order.completed",
                    message
                    );

            System.out.println("Message successfully reprocessed: " + message);

        }catch (Exception e){
            System.err.println("[DLQ] Failed to reprocess message: " + e.getMessage());
        }

    }
}


