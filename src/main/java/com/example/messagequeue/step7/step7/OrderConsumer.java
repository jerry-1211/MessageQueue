package com.example.messagequeue.step7.step7;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    private final RabbitTemplate rabbitTemplate;
    private final RetryTemplate retryTemplate;

    public OrderConsumer(RabbitTemplate rabbitTemplate, RetryTemplate retryTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.retryTemplate = retryTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_COMPLETED_QUEUE)
    public void consume(String message){
        retryTemplate.execute(retryContext -> {
            try{
                System.out.println("# Recieved Message " + message + "# retry : " + retryContext.getRetryCount());
                if ("fail".equalsIgnoreCase(message)) {
                    throw new RuntimeException();
                }
                System.out.println("# 메시지 처리 성공 " + message);
            } catch (RuntimeException e) {
                if(retryContext.getRetryCount() >= 2){
                    rabbitTemplate.convertAndSend(
                            RabbitMQConfig.ORDER_TOPIC_DLX,
                            RabbitMQConfig.DEAD_LETTER_ROUTING_KEY,
                            message
                    );
                }else {
                    throw e;
                }
            }
            return null;
        });
    }
}
