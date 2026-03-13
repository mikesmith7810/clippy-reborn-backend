# KAN-6 Research

## Ticket Snapshot
- **Key:** KAN-6
- **Summary:** Create a copilot instructions file
- **Type:** Story
- **Status:** In Progress
- **Assignee:** Unassigned
- **Description:** "We need a general copilot instructions file."
- **Acceptance (from ticket):**
  - Use google coding format
  - Use constructor injection for spring
  - Variable names should fully reflect the type (example: `chatService`)
  - Variable names should be camel case
- **Source files:** `tickets/KAN-6/KAN-6_plain.txt`, `tickets/KAN-6/KAN-6_raw.json`

## Sufficiency Assessment

### Critical Information (must have)
- **Clear problem statement:** Present.
- **Acceptance criteria:** Present but partially ambiguous.
- **Target repository/repositories:** Implicitly present (current repo: `clippy-reborn-backend`).
- **User story/use case:** Not explicitly stated, but acceptable for repository-maintenance work.
- **Technical context:** Sufficient to proceed for a scoped instructions-file task.

### Important Information (should have)
- **Dependencies:** None specified.
- **Technical constraints:** Partially specified via coding-style requirements.
- **API/data/UI impacts:** Not applicable.

## Repository Context Findings
- There is currently no `.github/copilot-instructions.md` file in this repo.
- Existing guidance sources are `README.md` and `CLAUDE.md`.
- Project stack and patterns are Spring Boot Java backend with service/handler/controller boundaries.

## Readiness Verdict
**Sufficient to proceed with solution proposal and implementation, with minor clarifications recommended.**

## Clarifications Needed Before Finalizing
1. Does "Use google coding format" mean Google Java Style only, or also markdown/instructions writing style constraints?
2. Should `.github/copilot-instructions.md` be the single workspace instructions file, with no `AGENTS.md` added?
3. Should the instructions explicitly enforce naming examples (e.g., avoid abbreviations like `svc`, prefer `chatService`)?

## Proposed Implementation Direction
1. Create `.github/copilot-instructions.md` with concise, always-on workspace guidance.
2. Include explicit conventions:
   - Constructor injection for Spring components.
   - Descriptive camelCase variable naming with type-reflective examples.
   - Google Java style consistency statement.
3. Keep content minimal and actionable; reference `README.md` and `CLAUDE.md` for deeper context.

## Acceptance Check Mapping
- **Google coding format:** addressed by explicit Google Java style instruction.
- **Constructor injection for Spring:** explicit rule in instructions.
- **Type-reflective variable names:** explicit naming rule with example.
- **camelCase variable names:** explicit naming convention rule.
