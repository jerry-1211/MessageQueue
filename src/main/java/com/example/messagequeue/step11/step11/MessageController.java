package com.example.messagequeue.step11.step11;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/step11/api/message")
public class MessageController {
    private final RabbitTemplate rabbitTemplate;

    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody String message){
        try{
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ORDER_TOPIC_EXCHANGE,
                    "order.completed",
                    message
            );
            return ResponseEntity.ok("Message sent to RabbitMQ: " + message);
        }catch (Exception e ){
            return ResponseEntity.status(500).body("Failed to send message: " + e.getMessage());
        }
    }

}
