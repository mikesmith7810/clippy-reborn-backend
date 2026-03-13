# KAN-8 Repro Requests

## Request 1
```bash
curl -i -sS -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message":null}'
```

## Observed
- HTTP status: `500`
- Body includes Spring error payload with `"error":"Internal Server Error"`.
