package com.example.messagequeue.step2.step2;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisher {
    private final RabbitTemplate rabbitTemplate;

    public NotificationPublisher(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(String message){
        // Fanout Exchange는 모든 큐에 브로드캐스팅하기 때문에, 별도의 Routing key가 필요없음
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, "", message);
        System.out.println("[#] Published Notification: " + message);
    }
}

