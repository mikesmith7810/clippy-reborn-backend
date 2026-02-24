# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

`clippy-reborn-backend` is the Spring Boot backend for the Clippy desktop assistant. It receives chat messages from the Electron frontend, fetches context from Google Calendar, and uses a locally running Ollama LLM to generate natural language responses.

## API contract with the frontend

The Electron frontend (`../clippy` repo) calls one endpoint:

- **`POST /api/chat`**
- Request: `{ "message": "<user text>" }`
- Response: `{ "reply": "<assistant response>" }`
- Frontend expects the server on `http://localhost:8080`

CORS must be enabled for `http://localhost:5173` (Vite dev server) and the packaged Electron app (file:// origin).

## Tech stack

- Java 21
- Spring Boot 3.x
- Spring AI with Ollama integration — `spring-ai-ollama-spring-boot-starter`
- Google Calendar API — `google-api-services-calendar`
- Maven

## Architecture

```
ChatController  (POST /api/chat)
      │
      ├──► CalendarService       fetches today's events from Google Calendar
      │         └──► Google Calendar API (OAuth2, refresh token stored locally)
      │
      └──► ChatService           builds prompt and calls Ollama
                └──► Spring AI OllamaChatModel (http://localhost:11434)
```

The controller injects today's calendar events as context into the Ollama prompt so the LLM can answer questions like "what do I have on today?".

## Key dependencies (pom.xml)

```xml
<!-- Spring AI Ollama -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-ollama-spring-boot-starter</artifactId>
</dependency>

<!-- Google Calendar -->
<dependency>
    <groupId>com.google.apis</groupId>
    <artifactId>google-api-services-calendar</artifactId>
    <version>v3-rev20220715-2.0.0</version>
</dependency>
<dependency>
    <groupId>com.google.auth</groupId>
    <artifactId>google-auth-library-oauth2-http</artifactId>
</dependency>
```

Spring AI BOM should be imported in `<dependencyManagement>`.

## Configuration (application.yml)

```yaml
server:
  port: 8080

spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        model: llama3.2   # or whichever model is pulled locally

google:
  calendar:
    credentials-file: classpath:credentials.json   # OAuth2 Desktop app credentials from Google Cloud Console
    tokens-dir: ./tokens                           # refresh token stored here after first auth
    user: mike                                     # arbitrary identifier for the stored token
```

## Google Calendar setup (one-time)

1. Go to [Google Cloud Console](https://console.cloud.google.com)
2. Create a project, enable the **Google Calendar API**
3. Create **OAuth 2.0 credentials** — type: *Desktop app* — download as `credentials.json`
4. Place `credentials.json` in `src/main/resources/`
5. On first run, a browser window opens for you to approve access — after approval a refresh token is saved to `./tokens/` and reused on all future runs

## Running locally

```bash
# Prerequisites
ollama pull llama3.2        # pull the model once
# Place credentials.json in src/main/resources/ (see Google Calendar setup above)

mvn spring-boot:run         # starts on http://localhost:8080
```

Start the frontend separately (`npm run dev` in the `../clippy` repo) and press **Ctrl+/** to open Clippy.

## Package structure to build

```
com.clippy
├── ClippyBackendApplication.java
├── controller
│   └── ChatController.java        POST /api/chat
├── service
│   ├── ChatService.java           builds prompt, calls Ollama
│   └── CalendarService.java       fetches today's Google Calendar events
└── config
    ├── CorsConfig.java            allow frontend origins
    └── GoogleCalendarConfig.java  OAuth2 setup
```
