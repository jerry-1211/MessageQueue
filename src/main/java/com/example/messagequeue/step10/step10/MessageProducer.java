package com.example.messagequeue.step10.step10;

import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;
    private final StockRepository stockRepository;

    public MessageProducer(RabbitTemplate rabbitTemplate, StockRepository stockRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void sendMessage(StockEntity stockEntity, boolean testCase) {
        stockEntity.setProcessed(false);
        stockEntity.setCreatedAt(LocalDateTime.now());
        StockEntity entity = stockRepository.save(stockEntity);

        System.out.println("[Producer entity] : "+ entity);

        if(stockEntity.getUserId() == null || stockEntity.getUserId().isEmpty()){
            throw new RuntimeException("User Id is required");
        }

        try{
            CorrelationData correlationData = new CorrelationData(entity.getId().toString());
            rabbitTemplate.convertAndSend(
                    //RabbitMQConfig.EXCHANGE_NAME, (이렇게 하면 Return Callback 볼 수 있음)
                    testCase ? "nonExistentExchange" : RabbitMQConfig.EXCHANGE_NAME,
                    testCase ? "invalidRoutingKey" : RabbitMQConfig.ROUTING_KEY,
                    entity,
                    correlationData
            );

            if(correlationData.getFuture().get(5, TimeUnit.SECONDS).isAck()){
                System.out.println("[Producer correlationData] 성공" + entity);
                entity.setProcessed(true);
                stockRepository.save(entity);
            }else {
                throw new RuntimeException("# confirm 실패 - 롤백");
            }

        }catch (Exception e){
            System.out.println("[Producer exception fail] : " + e);
            throw new RuntimeException(e);
        }

    }
}
