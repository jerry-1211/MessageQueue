package com.example.messagequeue.step4.step4;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class LogConsumer {

    @RabbitListener(queues = RabbitMQConfig.ERROR_QUEUE)
    public void consumerError(String message){
        System.out.println("[Error]를 받음 : " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.WARN_QUEUE)
    public void consumerWarn(String message){
        System.out.println("[Warn]를 받음 : " + message);
    }

    @RabbitListener(queues = RabbitMQConfig.INFO_QUEUE)
    public void consumerInfo(String message){
        System.out.println("[Info]를 받음 : " + message);
    }
}
