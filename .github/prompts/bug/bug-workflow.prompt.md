---
agent: "Bug Workflow"
description: Run an end-to-end bug fix workflow from Jira bug discovery through reproduction, approved fix, PR creation, and Jira linkage.
---

Run the full bug workflow using step prompts and pause for confirmation at each stage.

Execution order:
1. Find bug candidates: `/bug-find`
2. Fetch selected ticket: `/bug-fetch {ticket-key}`
3. Reproduce bug on running service: `/bug-reproduce {ticket-key}`
4. Propose fix and pause for approval: `/bug-propose-fix {ticket-key}`
5. Only after explicit user approval, implement: `/bug-implement-fix {ticket-key}`
6. Open PR: `/bug-open-pr {ticket-key}`
7. Link PR to Jira and move ticket to review: `/bug-link-pr {ticket-key} {pr-url}`

Rules:
- Never implement code before explicit approval after proposal step.
- If reproduction fails, do not implement; report findings and request more context.
- Keep all artifacts for the ticket under `tickets/{ticket-key}/`.
