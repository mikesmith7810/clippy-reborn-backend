# KAN-6 Plan Review Facts

## Inputs Reviewed
- `tickets/KAN-6/KAN-6_plain.txt`
- `tickets/KAN-6/KAN-6_raw.json`
- `tickets/KAN-6/KAN-6_research.md`
- `tickets/KAN-6/KAN-6_solution.md`
- `README.md`
- `CLAUDE.md`

## Verified Scope
- Create exactly one workspace instruction file: `.github/copilot-instructions.md`.
- Do **not** add `AGENTS.md` for this ticket.
- Keep changes documentation/instructions-only.

## Verified Acceptance Mapping
1. **Google coding format**
   - Instruction will explicitly require Google Java Style for Java code changes.
2. **Constructor injection for Spring**
   - Instruction will explicitly require constructor injection for Spring beans.
3. **Type-reflective variable names**
   - Instruction will require descriptive variable names matching role/type intent (example: `chatService`).
4. **camelCase variable names**
   - Instruction will explicitly require lower camelCase for variables.

## Proposed File Content Boundaries
- Include only always-on guidance relevant across tasks.
- Include architecture/build references concise enough to avoid context bloat.
- Reference existing docs for setup details (`README.md`, `CLAUDE.md`) instead of duplication.

## Risks and Mitigations
- **Risk:** Overly broad instructions reduce usefulness.
  - **Mitigation:** Keep file short, practical, and always-applicable.
- **Risk:** Ambiguous naming convention interpretation.
  - **Mitigation:** Include explicit examples (`chatService` good, `svc` discouraged).
- **Risk:** Duplicate or conflicting instruction systems.
  - **Mitigation:** Keep this ticket to a single instruction file only.

## Plan Review Verdict
✅ Proposal is implementation-ready with low risk.

## Recommended Next Step
Create task breakdown file `tickets/KAN-6/KAN-6_todo.md`, then implement `.github/copilot-instructions.md` according to accepted scope.
