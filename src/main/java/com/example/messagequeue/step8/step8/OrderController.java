package com.example.messagequeue.step8.step8;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/step8/api/order")
public class OrderController {

    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    /*
    * PostMan으로 테스트
    * http://localhost:8080/step8/api/order?message=success
    * http://localhost:8080/step8/api/order?message=fail
    * */

    @GetMapping
    public ResponseEntity<String> sendOrderMessage(@RequestParam String message){
        orderProducer.sendShipping(message);
        return ResponseEntity.ok("Order Completed Message sent: " + message);
    }
}

