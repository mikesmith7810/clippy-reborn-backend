# clippy-reborn-backend

> **Note:** This project was built as a test of vibe coding using Claude Code — the whole thing was written with AI assistance, with minimal manual code changes.

Spring Boot backend for the Clippy desktop assistant. Receives chat messages from the Electron frontend, enriches them with today's Google Calendar events, and uses a locally running Ollama LLM to generate responses.

## Prerequisites

- Java 21
- [Ollama](https://ollama.com) installed and running
- A Google account with access to Google Cloud Console

## Setup

### 1. Ollama

Install Ollama from [ollama.com](https://ollama.com), then pull the model:

```bash
ollama pull llama3.2
```

### 2. Google Calendar credentials

The app uses OAuth2 to read your Google Calendar. You need to create a free Google Cloud project and generate credentials — this is a one-time setup.

> **Note:** `credentials.json` contains a client secret and must never be committed to version control. It is already listed in `.gitignore`.

#### Create a Google Cloud project

1. Go to [console.cloud.google.com](https://console.cloud.google.com)
2. Click the project dropdown (top left) → **New Project**
3. Name it (e.g. `clippy-reborn`) → click **Create**
4. Make sure the new project is selected before continuing

#### Enable the Google Calendar API

1. Go to **APIs & Services → Library**
2. Search for `Google Calendar API` → click **Enable**

#### Create OAuth 2.0 credentials

1. Go to **APIs & Services → Credentials**
2. Click **+ Create Credentials → OAuth client ID**
3. If prompted to configure the consent screen first:
   - Choose **External**
   - Fill in an app name (e.g. `Clippy`) and your email address
   - Click through the remaining steps — no need to add scopes or test users
   - Save, then return to creating credentials
4. For application type select **Desktop app**
5. Name it anything (e.g. `clippy-desktop`) → click **Create**
6. Click **Download JSON**

#### Place the credentials file

```bash
mv ~/Downloads/client_secret_*.json \
  src/main/resources/credentials.json
```

### 3. Slack credentials

The app can read recent messages from Slack channels to answer questions like "what's been going on in #general?".

> **Note:** `application-local.yml` contains your bot token and must never be committed. It is already listed in `.gitignore`.

#### Create a Slack app and get a bot token

1. Go to [api.slack.com/apps](https://api.slack.com/apps) → **Create New App** → **From scratch**
2. Give it a name (e.g. `Clippy`) and select your workspace
3. In the left sidebar go to **OAuth & Permissions**
4. Under **Bot Token Scopes** add:
   - `channels:read`
   - `channels:history`
5. Scroll up and click **Install to Workspace** → **Allow**
6. Copy the **Bot OAuth Token** (starts with `xoxb-`)

#### Store the token locally

Create `src/main/resources/application-local.yml` (gitignored) with your token:

```yaml
slack:
  bot-token: xoxb-your-token-here
```

Then invite the bot to any channels you want to query in Slack:

```
/invite @your-app-name
```

You can then ask Clippy about any channel by name — e.g. "what's going on in #general?"

### 4. Run the app

```bash
./gradlew bootRun --args='--spring.profiles.active=local'
```

On first run a browser window will open asking you to approve Google Calendar access. After approval a refresh token is saved to `./tokens/` and reused on all future runs — you won't be prompted again.

The server starts on `http://localhost:8080`.

## API

```
POST /api/chat
Content-Type: application/json

{ "message": "What do I have on today?" }
```

```json
{ "reply": "You have a team standup at 9am and a 1:1 with your manager at 2pm." }
```

## Configuration

All config lives in `src/main/resources/application.yml`. Key settings:

| Key | Default | Description |
|-----|---------|-------------|
| `spring.ai.ollama.chat.model` | `llama3.2` | Ollama model to use |
| `spring.ai.ollama.base-url` | `http://localhost:11434` | Ollama server URL |
| `google.calendar.tokens-dir` | `./tokens` | Where the OAuth refresh token is stored |
| `google.calendar.user` | `mike` | Identifier for the stored token |
| `slack.bot-token` | — | Bot token from Slack — set in `application-local.yml` |
| `slack.message-limit` | `20` | Number of recent messages to fetch from the channel |
