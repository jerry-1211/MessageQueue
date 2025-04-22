package com.example.messagequeue.step3.step3;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/step3/news/api")
public class NewsRestController {

    /*
    curl -X POST "http://localhost:8080/step3/news/api/publish?newsType=java" -H "Content-Type: application/json"
    curl -X POST "http://localhost:8080/step3/news/api/publish?newsType=spring" -H "Content-Type: application/json"
    curl -X POST "http://localhost:8080/step3/news/api/publish?newsType=vue" -H "Content-Type: application/json"
    * */


    private final NewsPublisher newsPublisher;

    public NewsRestController(NewsPublisher newsPublisher) {
        this.newsPublisher = newsPublisher;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishNews(@RequestParam String newsType){
        String result = newsPublisher.publishAPI(newsType);
        return ResponseEntity.ok("# Message published to RabbitMQ: " + result);
    }
}
