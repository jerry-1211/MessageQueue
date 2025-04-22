package com.example.messagequeue.step3.step3;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {
    private final NewsPublisher newsPublisher;

    public NewsController(NewsPublisher newsPublisher) {
        this.newsPublisher = newsPublisher;
    }

    @MessageMapping("/subscribe")
    public void handleSubscribe(@Header("newsType") String newsType){

        System.out.println("[#] newsType: " + newsType);
        String newsMessage = newsPublisher.publish(newsType);
        System.out.println("# newMessage: " + newsMessage);
    }
}
