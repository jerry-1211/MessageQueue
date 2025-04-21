package com.example.messagequeue.step2.step2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationSubscriber {

    public static final String CLIENT_URL = "/topic/notifications";

    // SimpMessagingTemplate는 Spring에서
    // WebSocker 메시지를 서버에서 클라이언트로 보내는 도구
    // WebSocker 전용 라이브러리
    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationSubscriber(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void subscriber(String message){
        // RabbitMQ Queue에서 메시지 수신
        System.out.println("Received Notification: " + message);

        // convertAndSend를 통해 특정 경로로 메시지 전달
        simpMessagingTemplate.convertAndSend(CLIENT_URL,message);
    }
}
