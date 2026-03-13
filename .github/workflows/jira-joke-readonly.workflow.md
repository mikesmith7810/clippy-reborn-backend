---
on:
  pull-request:
    merged: true

permissions:
  contents: read
  issues: read
  pull-requests: read

safe-outputs:
  jira-comment:
    body-prefix: "Jira joke: "

tools:
  github:
  jira:
---

# Jira Joke Publisher (Read-Only Repo Access)

On PR merge, generate a short, work-safe joke about Jira and publish it to the Jira ticket referenced by that PR.

## Ticket Resolution
- Resolve Jira ticket key from the merged PR context, in this order:
  1. PR title
  2. PR branch name
  3. PR body
- Ticket key format: `ABC-123`
- If no ticket key is found, do nothing and report no-op.

## Requirements
- Keep repository access read-only.
- Do not create, edit, rename, or delete repository files.
- Do not run git write operations.
- Joke must be friendly and professional.

## Steps
1. Confirm Jira CLI is available and authenticated.
2. Resolve the ticket key from merged PR context.
3. Generate one concise Jira joke.
4. Publish comment:
  - `jira issue comment add <resolved-ticket> "Jira joke: <joke>"`
5. Report success with resolved ticket key and posted joke.
