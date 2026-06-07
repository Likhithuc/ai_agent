# Frontend Integration Guide

> Backend: Spring Boot 3.5.0 (Java 21) — stateless Gemini AI proxy

## Base URL

```
http://localhost:9999
```

## Authentication

**None required.** All requests are unauthenticated. No API keys, tokens, or cookies needed.

---

## Endpoints

### `POST /api/agent/chat`

Send a message to the AI agent and receive a response.

#### Request

```json
{
  "message": "Explain Spring Boot"
}
```

| Field     | Type   | Required | Notes            |
|-----------|--------|----------|------------------|
| `message` | string | yes      | Must be non-empty |

#### Success Response — `200 OK`

```json
{
  "response": "Spring Boot is an open-source Java-based framework..."
}
```

#### Error Responses

- **`400 Bad Request`** — `message` is blank or missing (Spring default error body).
- **`500 Internal Server Error`** — unexpected server failure.

> **Note**: If the Gemini API fails, the server still returns `200 OK` but the `response` field will contain a string like `"Error calling Gemini API: ..."` or `"No response received from Gemini."`. Always check the `response` text for these prefixes.

---

## Quickstart Example

### JavaScript / TypeScript

```js
const response = await fetch('http://localhost:9999/api/agent/chat', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ message: 'Hello, what can you do?' })
});

const data = await response.json();

if (data.response.startsWith('Error calling')) {
  // Handle Gemini API error
} else {
  console.log(data.response);
}
```

### curl

```bash
curl -X POST http://localhost:9999/api/agent/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Explain Spring Boot"}'
```

---

## Data Models

### ChatRequest

```json
{
  "message": "string (required, non-blank)"
}
```

### ChatResponse

```json
{
  "response": "string"
}
```

---

## Important Notes for Frontend Devs

| Topic              | Detail |
|--------------------|--------|
| **CORS**           | Not explicitly configured. Spring Boot defaults allow all origins, but if you hit CORS issues in production, ask the backend team to add `@CrossOrigin` or a `WebMvcConfigurer`. |
| **Streaming**      | Not supported. The response is a single synchronous JSON body. Expect possible multi-second delays. |
| **Pagination**     | Not applicable — only one endpoint. |
| **File upload**    | Not supported. |
| **WebSocket/SSE**  | Not supported. |
| **Rate limiting**  | Not implemented. |
| **State**          | The backend is stateless — no sessions, no chat history. Each request is independent. |

---

## Environment (for local dev)

| Property          | Default value                              | Override                 |
|-------------------|--------------------------------------------|--------------------------|
| `server.port`     | `9999`                                     | `application.yml`        |
| `gemini.api-key`  | (hardcoded in `application.yml`)           | env var `GEMINI_API_KEY` |

---

## Project Structure (frontend-relevant files only)

```
src/main/java/com/example/aiagent/
  controller/
    ChatController.java        # POST /api/agent/chat
  model/
    ChatRequest.java           # Request DTO
    ChatResponse.java          # Response DTO
  service/
    AiAgentService.java        # Gemini API integration
```
