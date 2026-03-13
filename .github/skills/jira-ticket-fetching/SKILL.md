---
name: jira-ticket-fetching
description: Fetch Jira ticket details using the Jira CLI and save them to a local tickets directory. Use when user requests to fetch/download/retrieve a Jira ticket.
user-invocable: false
---

# Jira Ticket Fetcher Skill

## Description
Fetch Jira ticket details using the Jira CLI and save them to a local tickets directory. This skill retrieves both raw JSON data and plain text format for comprehensive ticket information.

## When to Use
Use this skill when:
- User requests to fetch/download/retrieve a Jira ticket
- User wants to save Jira ticket details locally
- User provides a Jira ticket number (e.g., TICKET-123, PROJ-456)
- User wants to analyze or reference Jira ticket information

## Capabilities
- Fetches single Jira tickets using the Jira CLI
- Retrieves up to 100 comments per ticket
- Saves ticket data in two formats:
  - JSON (raw format with all fields)
  - TXT (plain text format for readability)
- Organizes tickets in structured directory: `tickets/{TICKET-NO}/`

## Prerequisites
- Jira CLI must be installed and configured
- User must have authentication set up for Jira CLI
- Access permissions to the requested Jira tickets

## Workflow

When a user requests a Jira ticket, follow these steps:

### Step 1: Validate Input
- Extract the Jira ticket number from user's request
- Ensure ticket number follows valid Jira format (e.g., PROJ-123)
- If ticket number is ambiguous, ask for clarification

### Step 2: Create Directory Structure
- Create directory: `tickets/{TICKET_NUMBER}/`
- Example: `tickets/ONBOARD-1234/`

### Step 3: Fetch Raw JSON Data
Execute command:
```bash
jira issue view {TICKET_NUMBER} --raw --comments 100 > tickets/{TICKET_NUMBER}/{TICKET_NUMBER}_raw.json
```

### Step 4: Fetch Plain Text Data
Execute command:
```bash
jira issue view {TICKET_NUMBER} --plain > tickets/{TICKET_NUMBER}/{TICKET_NUMBER}_plain.txt
```

### Step 5: Verify Success
- Check both files were created successfully
- Verify files contain data (not empty or error messages)
- Report summary to user with file locations

### Step 6: Error Handling
If any step fails:
- Report specific error (authentication, not found, permission denied, etc.)
- Provide helpful suggestions for resolution
- Do not create empty or partial files

## Output Format
After successful execution, the directory structure will be:
```
tickets/
└── {TICKET_NUMBER}/
    ├── {TICKET_NUMBER}_raw.json
    └── {TICKET_NUMBER}_plain.txt
```

## Example Usage

**User Request:** "Fetch ONBOARD-1234 from Jira"

**Actions:**
1. Create directory: `tickets/ONBOARD-1234/`
2. Run: `jira issue view ONBOARD-1234 --raw --comments 100 > tickets/ONBOARD-1234/ONBOARD-1234_raw.json`
3. Run: `jira issue view ONBOARD-1234 --plain > tickets/ONBOARD-1234/ONBOARD-1234_plain.txt`
4. Confirm success: "✅ Fetched ONBOARD-1234 and saved to tickets/ONBOARD-1234/"

## Error Messages
- **Authentication Error**: "Jira CLI authentication failed. Run `jira init` to configure."
- **Ticket Not Found**: "Ticket {TICKET_NUMBER} not found. Verify the ticket number is correct."
- **Permission Denied**: "Access denied to {TICKET_NUMBER}. Check your Jira permissions."
- **CLI Not Installed**: "Jira CLI not found. Install from: https://github.com/ankitpokhrel/jira-cli"

## Future Enhancements
- Batch fetching multiple tickets
- Custom comment limits
- Filtered field extraction
- Automatic ticket number detection from context
- Integration with git branch names
