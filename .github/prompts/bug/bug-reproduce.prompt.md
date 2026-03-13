---
agent: "Bug Workflow"
description: Reproduce a Jira bug by calling the running local service, capturing requests/responses, and collecting logs.
---

Use the `service-debugging` skill to reproduce the issue for `{TICKET-KEY}`.

Objectives:
1. Ensure service is running (start locally if needed).
2. Derive reproduction request(s) from Jira bug details.
3. Execute requests against local service endpoint(s).
4. Capture response evidence and relevant logs.
5. Save reproduction artifacts under `tickets/{TICKET-KEY}/`.

Required outputs:
- Reproduction verdict: `reproduced` or `not reproduced`.
- Minimal deterministic repro steps.
- Evidence file paths.

If not reproducible:
- Do not proceed to implementation.
- Report what was tried and what extra inputs are needed.
