package com.example.messagequeue.step11.step11;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate, StockRepository stockRepository) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String exchange, String routingKey, String message){
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("Message sent to exchange [" + exchange + "] with routing ket [" + routingKey + "]: " + message);
    }


}
