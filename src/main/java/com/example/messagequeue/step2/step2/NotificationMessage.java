package com.example.messagequeue.step2.step2;

public class NotificationMessage {
    private final String message;

    public NotificationMessage() {
        message = "";
    }

    public NotificationMessage(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
