---
agent: "Bug Workflow"
description: Propose a bug fix plan from reproduction evidence and pause for explicit approval before implementation.
---

Given `{TICKET-KEY}` and reproduction evidence, generate a fix proposal that includes:
- Suspected root cause
- Affected files/components
- Proposed code changes
- Test strategy (including failing test first when practical)
- Risks and rollback notes

Write proposal to:
- `tickets/{TICKET-KEY}/{TICKET-KEY}_fix_proposal.md`

Critical rule:
- After presenting the proposal, PAUSE and require explicit user approval before any code edits.
