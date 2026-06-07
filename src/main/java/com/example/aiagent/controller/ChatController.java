package com.example.aiagent.controller;

import com.example.aiagent.model.ChatRequest;
import com.example.aiagent.model.ChatResponse;
import com.example.aiagent.service.AiAgentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agent")
@RequiredArgsConstructor
public class ChatController {

    private final AiAgentService aiAgentService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@Valid @RequestBody ChatRequest request) {
        String responseMessage = aiAgentService.processChat(request.getMessage());
        return ResponseEntity.ok(new ChatResponse(responseMessage));
    }
}
