# KAN-8 Reproduction

## Request
curl -i -sS -X POST http://localhost:8080/api/chat -H "Content-Type: application/json" -d '{"message":null}'

## Response
HTTP/1.1 500
Content-Type: application/json

{"timestamp":"2026-03-13T13:29:31.891+00:00","status":500,"error":"Internal Server Error","path":"/api/chat"}

## Verdict
- Reproduced: yes
- Observed behavior: null `message` causes server-side 500.
- Expected behavior (from bug context): return client-safe validation response (for example 400) instead of 500.
