package com.example.messagequeue.step5.step5;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/step5/api/logs")
public class LogController {

    /**
     curl -X GET "http://localhost:8080/step5/api/logs/error"
     curl -X GET "http://localhost:8080/step5/api/logs/warn"
     curl -X POST "http://localhost:8080/step5/api/logs/info" -H "Content-Type: application/json" -d "\"System initialized successfully.\""
     */

    private final CustomExceptionHandler exceptionHandler;

    public LogController(CustomExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @GetMapping("/error")
    public ResponseEntity<String> errorAPI() {
        try {
            throw new NullPointerException("Null 포인트 에러입니다.");
        } catch (NullPointerException e) {
            exceptionHandler.handleException(e);
        }
        return ResponseEntity.ok("Controller Nullpoint Exception 처리");
    }

    @GetMapping("/warn")
    public ResponseEntity<String> warnAPI() {
        try {
            throw new IllegalArgumentException("invalid argument입니다.");
        } catch (IllegalArgumentException e) {
            exceptionHandler.handleException(e);
        }
        return ResponseEntity.ok("Controller IllegalArgument Exception 처리");
    }

    @PostMapping("/info")
    public ResponseEntity<String> infoAPI(@RequestBody String message){
        exceptionHandler.handleMessage(message);
        return ResponseEntity.ok("Controller Info log 발송 처리 ");
    }
}

