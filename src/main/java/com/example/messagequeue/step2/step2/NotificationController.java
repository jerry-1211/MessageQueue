package com.example.messagequeue.step2.step2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/step2/notifications")
public class NotificationController {
    private final NotificationPublisher publisher;

    public NotificationController(NotificationPublisher publisher) {
        this.publisher = publisher;
    }

    //  curl -X POST 'http://localhost:8080/step2/notifications' -H 'Content-Type: application/json' -d '"Hello Subscriber2"'
    @PostMapping
    public String sendNotification(@RequestBody String message){
        publisher.publish(message);
        return "[#] Notification sent " + message + "\n";
    }
}
