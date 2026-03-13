---
agent: "Bug Workflow"
description: Link a bug-fix PR to Jira and transition the ticket to In Review.
---

Inputs:
- `{TICKET-KEY}`
- `{PR-URL}`

Steps:
1. Add Jira comment: `PR opened for this ticket: {PR-URL}`.
2. Transition Jira ticket to `In Review` (or next valid review state).
3. Verify ticket status and comment count.
4. Report confirmation with links.

Use Jira CLI commands and keep output concise and verifiable.
