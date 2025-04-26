package com.example.messagequeue.step9.step9;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/step9/api/message")
public class TransactionController {
    private MessageProducer messageProducer;

    public TransactionController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping()
    public ResponseEntity<String> sendMessage(@RequestBody StockEntity stockEntity,
                                              @RequestParam(required = false, defaultValue = "success") String testCase) {
        System.out.println("Send message : " + stockEntity);
        System.out.println("testCase = " + testCase);

        try{
            messageProducer.sendMessage(stockEntity, testCase);
            return ResponseEntity.ok("Message sent successfully");
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("MQ 트랜젝션 실패: " + e.getMessage());
        }
    }


}
