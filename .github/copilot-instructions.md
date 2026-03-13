# Project Guidelines

## Code Style
- Follow Google Java Style for all Java code changes.
- Use descriptive lower camelCase variable names that reflect role/type intent.
  - Preferred: `chatService`, `calendarService`, `messageType`
  - Avoid unclear abbreviations like `svc`, `msg`, `tmpObj`

## Spring Conventions
- Prefer constructor injection for Spring-managed components (`@Controller`, `@Service`, `@Component`, `@Configuration`).
- Avoid field injection unless there is a clear, justified exception.

## Architecture Boundaries
- Keep API entry in controller layer (`controller/`).
- Keep routing/coordination in handler/router/service layers (`handler/`, `service/`).
- Keep integrations in dedicated service/config classes (`CalendarService`, `SlackService`, config classes).

## Build and Run
- Build: `./gradlew build`
- Test: `./gradlew test`
- Run local profile: `./gradlew bootRun --args='--spring.profiles.active=local'`

## Branches
- Any new feature branches are prefixed with feature - eg /feature/KAN-1_ImplementNewButton
- Any new bug branches are prefixed with bug - eg /bug/KAN-1_FixAnnoyingNull

## References
- See `README.md` for setup and environment prerequisites.
- See `CLAUDE.md` for architecture and API contract context.
