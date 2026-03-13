---
name: next
description: Analyze Jira sprint and backlog to discover your next tasks. Shows assigned tickets, available work, code review opportunities, and backlog priorities. Invoke with /next command.
user-invocable: false
---

## When to Use

Use this skill when:
- User types `/next` command
- User asks "what should I work on next?"
- User wants to see their assigned sprint tickets
- User wants to find available tickets in the sprint
- User wants to know which tickets need code review
- User wants to see upcoming backlog priorities~~

## Capabilities
- Shows your assigned tickets in the current sprint
- Lists available unassigned tickets in the sprint
- Identifies tickets in code review status (for review contributions)
- Displays high-priority backlog items when sprint is empty/complete
- Provides ticket summaries, statuses, and priorities
- Outputs formatted markdown report

# Pre-flight
- Ensure you hae access to the `jira` cli tool. This is installed via brew. Check using `which jira` or `jira version`.
- User must be authenticated with Jira CLI
- Access to the Jira project (default: {PROJECT_NAME} project)

# Available Commands

Given the command line a moment to fetch the latest data from Jira, it will show a spinner whilst it does this:

Get existing sprint summary:
`jira issue list --jql "project = {PROJECT_NAME} AND sprint in openSprints()" --plain`

Get project {PROJECT_NAME} backlog summary:
`jira issue list --jql "project = {PROJECT_NAME} AND sprint is EMPTY AND status IN ('To Do', 'Backlog', 'Ready')" --order-by rank --reverse --plain`

# Display Summary
Show inline summary with:
- Count of assigned tickets
- Count of available tickets
- Count of review opportunities
- Top 3 recommendations for what to work on next
- Link to full report file

## Usage Examples

**User:** `/next`

**Output:**
```
🎯 Analyzing your Jira project...

📋 My Assigned Tickets: 2
✅ AO-123 - Implement user validation [In Progress] {High}
📝 AO-456 - Fix login bug [To Do] {Medium}

🆓 Available Sprint Tickets: 3
📝 AO-789 - Add API rate limiting [To Do] {High}
📝 AO-790 - Update documentation [To Do] {Low}
📝 AO-791 - Refactor auth service [To Do] {Medium}

👀 Code Review Opportunities: 1
🔍 AO-321 - Update password reset flow [Code Review] - @johndoe

💡 Recommendations:
1. Continue AO-123 (In Progress)
2. Pick up AO-789 (High Priority, unassigned)
3. Review AO-321 (Unblock @johndoe)

📄 Full report saved to: tickets/next-tasks-2026-02-13.md
```

# Preference

Deliver all content directly within the chat window using standard Markdown formatting. Do not generate downloadable files (.md, .txt, etc.) or use code blocks for prose. If the response is long, break it into sections within this conversation.