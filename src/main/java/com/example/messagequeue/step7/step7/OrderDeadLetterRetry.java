package com.example.messagequeue.step7.step7;

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
        System.out.println("[DLQ Received]: " + message);
        try{
            if("fail".equalsIgnoreCase(message)){
                message = "success";
            }else{
                System.out.println("[DLQ] Message already fixed. Ignore: " + message);
            }

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ORDER_TOPIC_EXCHANGE,
                    "order.completed",
                    message
                    );
        }catch (Exception e){
            System.err.println("[DLQ] Failed to reprocess message: " + e.getMessage());
        }

    }
}


