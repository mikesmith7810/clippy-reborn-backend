````skill
---
name: update-jira-status
description: Update the Jira ticket in context to the next workflow stage using jira-cli (for example, To Do -> In Progress).
user-invocable: false
---

# Update Jira Status Skill

## Description
Moves the Jira ticket in context to the next workflow stage using `jira-cli`.

Assume `jira-cli-setup` is already complete.

## When to Use
Use this skill when:
- User asks to move/transition/update a Jira ticket status
- User asks to move a ticket in context to the next stage
- User gives a specific transition (for example: "move JWT-2 to In Progress")

## Capabilities
- Resolves the ticket in context from user input or workspace context
- Transitions issue state with `jira issue move`
- Optionally adds a transition comment
- Verifies the new status after transition
- Provides clear error messages when transition is invalid

## Prerequisites
- `jira-cli` is installed and authenticated
- User has permission to transition the issue
- `jira-cli-setup` has already been completed

## Workflow

### Step 1: Resolve the ticket in context
Determine the issue key in this order:
1. Explicit issue key from the user request (preferred)
2. Ticket key in current branch name (for example `JWT-2-short-description`)
3. Ticket key from active ticket folder (for example `tickets/JWT-2/`)

If no issue key can be determined, ask the user for the exact ticket key.

### Step 2: Determine target status
- If user provided a target status, use it exactly.
- If user asked for "next stage" and current status is `To Do`, default target to `In Progress`.
- If current status is not `To Do` and target is not specified, ask for explicit target state.

### Step 3: Run transition command
Use:

```bash
jira issue move {ISSUE_KEY} "{TARGET_STATE}"
```

Optional with comment:

```bash
jira issue move {ISSUE_KEY} "{TARGET_STATE}" --comment "Starting implementation"
```

### Step 4: Verify result
Confirm updated status:

```bash
jira issue view {ISSUE_KEY} --plain
```

If transition failed, return actionable reason and next step.

## Examples

### Example A: Explicit transition
User: "Move JWT-2 to In Progress"

Command:
```bash
jira issue move JWT-2 "In Progress"
```

### Example B: Context-based next stage
User: "Move the ticket in context to next stage"

Actions:
1. Resolve issue key from context (for example `JWT-2`)
2. Detect current status (`To Do`)
3. Transition to `In Progress`

Command:
```bash
jira issue move JWT-2 "In Progress"
```

## Error Handling
- **Issue not found**: Ask user to confirm issue key and project access.
- **Invalid transition**: Ask user for exact allowed target state for that workflow.
- **Permission denied**: Ask user to request transition permission in Jira.
- **CLI/auth error**: Ask user to re-run setup/auth (via `jira-cli-setup`).

## Success Output
After success, report:
- Issue key
- Previous status (if available)
- New status
- Confirmation command result summary

Example:
`✅ JWT-2 moved from To Do to In Progress`

````