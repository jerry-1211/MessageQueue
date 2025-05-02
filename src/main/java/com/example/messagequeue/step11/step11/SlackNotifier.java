package com.example.messagequeue.step11.step11;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class SlackNotifier {
    private final String apiToken;
    private final String channelId;
    private final WebClient webClient;

    public SlackNotifier(
            @Value("${SLACK_API_TOKEN}") String apiToken,
            @Value("${SLACK_CHANNEL}") String channelId) {
        this.apiToken = apiToken;
        this.channelId = channelId;

        System.out.println("API Token loaded: " + apiToken);
        System.out.println("channelId = " + channelId);


        this.webClient = WebClient.builder()
                .baseUrl("https://slack.com/api")
                .defaultHeader("Authorization", "Bearer " + apiToken)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public void sendSlackNotification(String message){
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("channel", channelId);
        requestBody.put("text", message);

        webClient.post()
                .uri("/chat.postMessage")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> System.out.println("Response: " + response))
                .block();

        System.out.println("Slack notification sent: " + message);

    }
}
