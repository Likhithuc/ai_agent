package com.example.aiagent.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AiAgentService {

    @Value("${gemini.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent";

    public AiAgentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String processChat(String message) {

        log.info("Processing message: {}", message);

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-goog-api-key", apiKey);

            Map<String, Object> requestBody = Map.of(
                    "contents",
                    List.of(
                            Map.of(
                                    "parts",
                                    List.of(
                                            Map.of("text", message)
                                    )
                            )
                    )
            );

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> responseEntity =
                    restTemplate.exchange(
                            GEMINI_API_URL,
                            HttpMethod.POST,
                            entity,
                            Map.class
                    );

            Map<String, Object> response = responseEntity.getBody();

            if (response != null && response.containsKey("candidates")) {

                List<Map<String, Object>> candidates =
                        (List<Map<String, Object>>) response.get("candidates");

                if (!candidates.isEmpty()) {

                    Map<String, Object> firstCandidate = candidates.get(0);

                    Map<String, Object> content =
                            (Map<String, Object>) firstCandidate.get("content");

                    List<Map<String, Object>> parts =
                            (List<Map<String, Object>>) content.get("parts");

                    if (!parts.isEmpty()) {
                        return String.valueOf(parts.get(0).get("text"));
                    }
                }
            }

            return "No response received from Gemini.";

        } catch (Exception e) {
            log.error("Gemini API Error", e);
            return "Error calling Gemini API: " + e.getMessage();
        }
    }
}