package com.example.messagequeue.step10.step10;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "transactionQueue";
    public static final String EXCHANGE_NAME = "transactionExchange";
    public static final String ROUTING_KEY = "transactionRoutingKey";

    @Bean
    public Queue transactionQueue() {
            return QueueBuilder.durable(QUEUE_NAME)
                    .withArgument("x-dead-letter-exchange", "")
                    .withArgument("x-dead-letter-routing-key", "deadLetterQueue")
                    .build();
    }

    @Bean
    public Queue deadLetterQueue(){
        return new Queue("deadLetterQueue");
    }

    @Bean
    public DirectExchange transactionExchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding transactionBinding(Queue transactionQueue, DirectExchange transactionExchange){
        return BindingBuilder.bind(transactionQueue).to(transactionExchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if(ack){
                System.out.println("#### [Message confirmed: " +
                        (correlationData != null ? correlationData.getId() : "null"));
            }else{
                System.out.println("### [Message not confirmed]: " +
                        (correlationData != null ? correlationData.getId() : "null") + ", Reason: " + cause);

                /**
                 * 실패 메시지에 대한 추가 처리 로직 작성
                 * */
            }
        });

        rabbitTemplate.setReturnsCallback(returned ->{
            System.out.println("Return Message: " + returned.getMessage().getBody());
            System.out.println("Exchange : " + returned.getExchange());
            System.out.println("RoutingKey : " + returned.getRoutingKey());

            /**
             * 데드레터 설정 추가
             * */
        });

        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

}
