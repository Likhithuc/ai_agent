# AI Agent Backend

This is a Spring Boot application that integrates with the Gemini API to provide an AI Chat Agent.

## Setup

1. **Prerequisites**:
   - Java 21
   - An IDE (IntelliJ IDEA, Eclipse, VS Code) or Gradle installed locally.

2. **Configuration**:
   - Open `src/main/resources/application.yml`.
   - Replace `YOUR_GEMINI_API_KEY` with your actual Gemini API key from [Google AI Studio](https://aistudio.google.com/).
   - Alternatively, set an environment variable `GEMINI_API_KEY`.

3. **Running the application**:
   - If you have Gradle installed: `gradle bootRun`
   - Using your IDE: Run the `AiAgentApplication` class.

## API Endpoints

### Chat Request

**POST** `http://localhost:9999/api/agent/chat`

**Request Body**:
```json
{
  "message": "Explain Spring Boot"
}
```

**Response Body**:
```json
{
  "response": "Spring Boot is an open-source Java-based framework used to create microservices..."
}
```

## Project Structure

- `com.example.aiagent.controller`: REST Controllers
- `com.example.aiagent.service`: Business logic and Gemini API integration
- `com.example.aiagent.model`: Data models for API requests and responses
- `com.example.aiagent.config`: Spring configuration beans
