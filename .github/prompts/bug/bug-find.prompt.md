---
agent: "Bug Workflow"
description: Find active Jira bug tickets and recommend the next bug to work on.
---

Use Jira CLI to list active bug tickets and summarize candidates.

Default query:
- `project = KAN AND issuetype = Bug AND statusCategory != Done ORDER BY priority DESC, created DESC`

Steps:
1. Verify Jira CLI is available and authenticated.
2. Run the bug query and return a concise shortlist with key, summary, status, priority, assignee.
3. Recommend top 1-3 tickets with rationale (priority, recency, ownership).
4. Ask user which ticket to proceed with.

If no bug tickets exist, report this and suggest checking non-bug tickets with `/whats-next`.
