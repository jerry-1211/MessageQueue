package com.example.messagequeue.step3.step3;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;



@Component
public class NewsSubscriber {

    private final SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(NewsSubscriber.class);

    public NewsSubscriber(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.JAVA_QUEUE)
    public void javaNews(String message){
        logger.warn(message);
        messagingTemplate.convertAndSend("/topic/java",message);
    }

    @RabbitListener(queues = RabbitMQConfig.SPRING_QUEUE)
    public void springNews(String message){
        logger.warn(message);
        messagingTemplate.convertAndSend("/topic/spring",message);
    }

    @RabbitListener(queues = RabbitMQConfig.VUE_QUEUE)
    public void vueNews(String message){
        logger.warn(message);
        messagingTemplate.convertAndSend("/topic/vue",message);
    }
}
