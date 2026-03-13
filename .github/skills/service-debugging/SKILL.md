---
name: service-debugging
description: Start or verify the local backend service, reproduce bugs with HTTP requests, capture response evidence, and inspect logs for root-cause clues.
user-invocable: false
---

# Service Debugging Skill

## Description
Operational bug-reproduction helper for the local backend service. It verifies service availability, executes reproducible HTTP requests, captures evidence, and summarizes log findings.

## When to Use
Use this skill when:
- A bug ticket needs runtime reproduction against local service.
- You need request/response evidence before proposing a fix.
- You need quick log-driven triage for backend failures.

## Capabilities
- Checks whether service is reachable locally.
- Starts service locally when needed.
- Executes targeted HTTP requests (`curl`) to reproduce behavior.
- Captures request/response artifacts under `tickets/{TICKET-KEY}/`.
- Captures relevant log snippets from active run output or local log files.
- Produces a deterministic reproduction summary.

## Prerequisites
- Local project dependencies configured.
- Jira ticket context available (`{TICKET-KEY}`).
- Service should run locally on configured endpoint (default `http://localhost:8080`).

## Workflow

### Step 1: Resolve target and artifact paths
- Require `{TICKET-KEY}`.
- Ensure artifact directory exists: `tickets/{TICKET-KEY}/`.

Recommended artifact files:
- `tickets/{TICKET-KEY}/{TICKET-KEY}_repro.md`
- `tickets/{TICKET-KEY}/{TICKET-KEY}_repro_requests.md`
- `tickets/{TICKET-KEY}/{TICKET-KEY}_repro_logs.md`

### Step 2: Verify service availability
1. Probe service:
   - `curl -sS -i http://localhost:8080/ | head -n 20`
2. If unavailable, start service:
   - `./gradlew bootRun --args='--spring.profiles.active=local'`
3. Re-probe until reachable or timeout and report blocker.

### Step 3: Build reproduction requests
- Derive request payloads from ticket description/comments and API contract.
- Prefer minimal requests that isolate bug behavior.
- Record exact commands and payloads in `{TICKET-KEY}_repro_requests.md`.

### Step 4: Execute and capture evidence
- Run each request and capture:
  - Timestamp
  - Request command/payload
  - Status code
  - Response body (redacted where necessary)
- Append summarized results to `{TICKET-KEY}_repro.md`.

### Step 5: Collect log clues
- Inspect active terminal output and/or relevant log files.
- Capture only relevant snippets and add context (request correlation where possible).
- Save findings to `{TICKET-KEY}_repro_logs.md`.

### Step 6: Reproduction verdict
Return one of:
- `reproduced`: include deterministic steps and evidence paths.
- `not reproduced`: include attempted scenarios and missing inputs.

## Safety and Redaction
- Use local endpoints only unless user explicitly says otherwise.
- Redact secrets/tokens from requests and logs before saving artifacts.
- Avoid storing full credential payloads in ticket artifacts.

## Error Handling
- **Service won’t start**: report startup command, last error lines, and probable cause.
- **Endpoint unknown**: ask user for route or infer from controller mapping files.
- **Auth/config dependency missing**: report exactly what is missing and how to provide it.
- **Non-deterministic repro**: log frequency, conditions, and stabilization suggestions.
