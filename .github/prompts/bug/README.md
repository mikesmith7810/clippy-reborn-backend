# Bug Workflow Prompt Guide

Use this sequence to run the bug-fix workflow end-to-end.

## Quick Start
1. Find candidate bug tickets:
   - `/bug-find`
2. Fetch selected ticket details:
   - `/bug-fetch {TICKET-KEY}`
3. Reproduce on local running service:
   - `/bug-reproduce {TICKET-KEY}`
4. Propose fix and pause for approval:
   - `/bug-propose-fix {TICKET-KEY}`
5. After explicit approval, implement fix:
   - `/bug-implement-fix {TICKET-KEY}`
6. Open PR:
   - `/bug-open-pr {TICKET-KEY}`
7. Link PR to Jira and move to review:
   - `/bug-link-pr {TICKET-KEY} {PR-URL}`

## Important Rules
- Do not implement code before approval after `/bug-propose-fix`.
- If bug is not reproducible, stop implementation and report missing context.
- Keep all ticket artifacts under `tickets/{TICKET-KEY}/`.

## Expected Artifacts
- Ticket fetch:
  - `tickets/{TICKET-KEY}/{TICKET-KEY}_raw.json`
  - `tickets/{TICKET-KEY}/{TICKET-KEY}_plain.txt`
- Reproduction:
  - `tickets/{TICKET-KEY}/{TICKET-KEY}_repro.md`
  - `tickets/{TICKET-KEY}/{TICKET-KEY}_repro_requests.md`
  - `tickets/{TICKET-KEY}/{TICKET-KEY}_repro_logs.md`
- Fix proposal:
  - `tickets/{TICKET-KEY}/{TICKET-KEY}_fix_proposal.md`

## Jira State Flow
Default state progression:
- `To Do -> In Progress -> In Review -> Done`

## Notes
- This workflow is coordinated by `Bug Workflow` agent.
- Orchestrator prompt:
  - `/bug-workflow`
