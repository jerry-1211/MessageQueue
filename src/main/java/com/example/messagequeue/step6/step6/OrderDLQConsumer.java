package com.example.messagequeue.step6.step6;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderDLQConsumer {
    private final RabbitTemplate rabbitTemplate;

    public OrderDLQConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.DLQ)
    public void process(String message){
        System.out.println("DLQ Message Receiced: " + message);

        try{
            String fixMessage = "success";

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ORDER_EXCHANGE,
                    "order.completed.shipping",
                    fixMessage
            );
            System.out.println("DLQ Message Sent: " + fixMessage);
        } catch (Exception e) {
            System.out.println("### [DLQ Consumer Error] " + e.getMessage());
        }
    }
}
