---
agent: "Bug Workflow"
description: Implement an approved bug fix, validate with tests, and keep ticket scope tight.
---

Precondition:
- Explicit user approval has been given for `{TICKET-KEY}` fix proposal.

Implementation process:
1. Review `tickets/{TICKET-KEY}/{TICKET-KEY}_fix_proposal.md`.
2. Implement minimal scoped code changes.
3. Add or update tests for the bug scenario.
4. Run focused tests first, then broader tests when practical.
5. Summarize changed files and test outcomes.

Output:
- Implementation summary
- Test commands + results
- Remaining risks (if any)

If approval is missing, stop and ask for approval.
