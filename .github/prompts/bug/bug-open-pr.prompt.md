---
agent: "Bug Workflow"
description: Create branch, commit bug fix changes, push, and open a PR for a bug ticket.
---

For `{TICKET-KEY}`:
1. Ensure branch name strictly matches: `bug/{TICKET-KEY}-<short-slug>`.
    - If current branch does not start with `bug/{TICKET-KEY}`, STOP and fix branch naming before commit/PR.
    - Example fix flow:
       - `git branch -m bug/{TICKET-KEY}-<short-slug>`
       - `git push -u origin bug/{TICKET-KEY}-<short-slug>`
       - Continue PR creation only after naming is compliant.
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
