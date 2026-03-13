---
agent: "Bug Workflow"
description: Create branch, commit bug fix changes, push, and open a PR for a bug ticket.
---

For `{TICKET-KEY}`:
1. Ensure on a feature branch (recommended: `bug/{TICKET-KEY}-<short-slug>`).
2. Stage only ticket-scope changes.
3. Commit with clear bug-fix message including ticket key.
4. Push branch to `origin`.
5. Create PR to `main` with:
   - Bug context
   - Reproduction summary
   - Fix summary
   - Validation/test results

Required output:
- Branch name
- Commit SHA
- PR URL
