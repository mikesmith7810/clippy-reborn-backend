---
agent: "Bug Workflow"
description: Fetch a Jira bug ticket into local ticket artifacts for workflow execution.
---

Use the `jira-ticket-fetching` skill to fetch the target bug ticket into:
- `tickets/{TICKET-KEY}/{TICKET-KEY}_raw.json`
- `tickets/{TICKET-KEY}/{TICKET-KEY}_plain.txt`

Inputs:
- Required: `{TICKET-KEY}`

After fetching:
1. Verify both files exist and are non-empty.
2. Summarize bug title, status, and acceptance details.
3. Suggest the next step: `/bug-reproduce {TICKET-KEY}`.
