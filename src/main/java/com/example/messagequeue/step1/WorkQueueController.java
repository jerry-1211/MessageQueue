package com.example.messagequeue.step1;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class WorkQueueController {
    private final WorkQueueProducer workQueueProducer;

    public WorkQueueController(WorkQueueProducer workQueueProducer) {
        this.workQueueProducer = workQueueProducer;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        workQueueProducer.sendWorkQueue(message);
        return "[#] Message sent successfully! " + message;
    }
}
