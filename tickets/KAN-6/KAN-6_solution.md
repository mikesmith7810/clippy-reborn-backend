# KAN-6 Solution Proposal

## Goal
Create a single workspace instructions file at `.github/copilot-instructions.md` that gives always-on, project-wide guidance aligned to ticket acceptance criteria.

## Confirmed Clarifications
- "Use google coding format" means **Google Java Style**.
- Use **only** `.github/copilot-instructions.md` (no `AGENTS.md` for this ticket).
- Explicitly enforce descriptive camelCase variable names and discourage abbreviations (for example, use `chatService`, not `svc`).

## Affected Repository and Components
- **Repository:** `clippy-reborn-backend`
- **Files to create/update:**
  - `.github/copilot-instructions.md` (new)
- **Inputs used as source-of-truth:**
  - `README.md`
  - `CLAUDE.md`
  - `tickets/KAN-6/KAN-6_research.md`

## High-Level Approach
1. Create a concise `.github/copilot-instructions.md` containing only always-on guidance.
2. Include explicit coding conventions required by ticket:
   - Follow Google Java Style for Java code changes.
   - Prefer constructor injection for Spring components.
   - Use descriptive camelCase variable names that reflect type/role.
3. Add minimal repo context (architecture boundaries + standard build/test commands) to keep agent output practical and consistent.
4. Keep the file focused; reference existing docs instead of duplicating long setup content.

## Proposed Content Structure
1. **Code Style**
   - Google Java Style statement.
   - Naming guidance (descriptive camelCase, avoid abbreviations).
2. **Spring Conventions**
   - Constructor injection preference.
3. **Architecture Boundaries**
   - Controller → Router/Handler → Services boundaries.
4. **Build/Test Commands**
   - `./gradlew build`
   - `./gradlew test`
   - `./gradlew bootRun --args='--spring.profiles.active=local'`
5. **References**
   - `README.md`, `CLAUDE.md`

## Implementation Task Breakdown
1. Validate no existing `.github/copilot-instructions.md` (or merge if present).
2. Draft `.github/copilot-instructions.md` with the structure above.
3. Verify acceptance mapping against all four ticket criteria.
4. Sanity-check for brevity, clarity, and no contradictory guidance.
5. Prepare ticket recap/review artifacts if requested.

## Risks and Mitigations
- **Risk:** Overly long instructions reduce signal.
  - **Mitigation:** Keep only always-on guidance and link to docs.
- **Risk:** Ambiguous naming rules.
  - **Mitigation:** Include explicit positive/negative examples (`chatService` vs `svc`).
- **Risk:** Duplicate instruction systems.
  - **Mitigation:** Keep only `.github/copilot-instructions.md` for this ticket scope.

## Acceptance Criteria Mapping
- **Use google coding format**
  - Add explicit "Follow Google Java Style" guidance.
- **Use constructor injection for spring**
  - Add explicit constructor injection rule under Spring conventions.
- **Variable names should fully reflect type**
  - Add descriptive naming rule with example `chatService`.
- **Varnames should be camel case**
  - Add explicit camelCase requirement with examples.

## Out of Scope
- Creating `AGENTS.md` hierarchy.
- Introducing formatter tooling plugins or mass refactors.
- Changing runtime application code unrelated to instruction guidance.
