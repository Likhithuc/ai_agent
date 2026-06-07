package com.example.aiagent;

import com.example.aiagent.model.ChatRequest;
import com.example.aiagent.model.ChatResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testChatEndpoint() {
        ChatRequest request = new ChatRequest("Hello");
        ResponseEntity<ChatResponse> response = restTemplate.postForEntity("/api/agent/chat", request, ChatResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        // Since we don't have a real API key in tests, it should return the "Please configure..." message
        assertNotNull(response.getBody().getResponse());
    }
}
