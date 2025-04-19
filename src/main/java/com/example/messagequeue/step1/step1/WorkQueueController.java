package com.example.messagequeue.step1.step1;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-step1")
public class WorkQueueController {
    private final WorkQueueProducer workQueueProducer;

    public WorkQueueController(WorkQueueProducer workQueueProducer) {
        this.workQueueProducer = workQueueProducer;
    }

    @PostMapping("/workqueue-step1")
    public String workQueue(@RequestParam String message, @RequestParam int duration){
        workQueueProducer.sendWorkQueue(message,duration);
        return "Work queue sent = " + message  + ", (" + duration + ")";
    }
}


/* 터미널 또는 포스트맨에 입력
curl -X POST "http://localhost:8080/api-step1/workqueue-step1?message=Task1&duration=2000"
curl -X POST "http://localhost:8080/api-step1/workqueue-step1?message=Task2&duration=4000"
curl -X POST "http://localhost:8080/api-step1/workqueue-step1?message=Task3&duration=5000"
*/
