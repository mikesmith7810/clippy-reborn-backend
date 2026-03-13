# KAN-4 Ticket Recap Fact Sheet

## Window and Context
- **Ticket:** KAN-4 (from user input)
- **Time window used for git activity:** default `2w` (because input `KAN-4` is not a time pattern)
  - Source: `ticket-recap.prompt.md` parsing rule + command `git rev-list --count --since="2 weeks ago" HEAD`

## Verified Quantitative Facts
- **Commits in last 2 weeks:** 3
  - Source: terminal command `git rev-list --count --since="2 weeks ago" HEAD` -> `3`
- **Recent commits in window:**
  - `eb65a49 chore(KAN-4): reformat Java sources to Google coding standards`
  - `18e4200 Add ollama serve instructions to README`
  - `293d269 updated cors config`
  - Source: terminal command `git log --since="2 weeks ago" --oneline`
- **KAN-4 commit impact:** 14 files changed, 298 insertions, 249 deletions
  - Source: terminal command `git show --stat --oneline eb65a49`
- **Most frequently touched file in 2w window:** `src/main/java/com/clippy/config/CorsConfig.java` (2 changes)
  - Source: terminal command `git log --since='2 weeks ago' --name-only --pretty='format:' ...` output
- **Tracked modified files currently in working tree:** 2
  - `src/main/java/com/clippy/model/MessageType.java`
  - `src/main/java/com/clippy/service/ChatService.java`
  - Source: terminal command `git status --short`
- **Top-level untracked paths currently present:** 3
  - `.github/`, `src/main/java/com/clippy/handler/GeneralRequestHandler.java`, `tickets/`
  - Source: terminal command `git status --short`
- **Branch context:** current branch is `KAN-4-reformat-code`; repo has 2 local branches and 2 remote tracking branches listed
  - Source: terminal commands `git rev-parse --abbrev-ref HEAD`, `GIT_PAGER=cat git branch --all`
- **TODO/FIXME markers in Java source/test:** none found
  - Source: terminal commands `grep -Rni "TODO" src/main/java src/test/java` and `grep -Rni "FIXME" src/main/java src/test/java` (no matches)

## Verified Ticket State Facts
- **Jira ticket key/type/status/assignee:** `KAN-4`, `Story`, `Done`, `Mike Smith`
  - Source: terminal command `jira issue view KAN-4 --plain`
- **Ticket summary:** `Reformat Code`
  - Source: `tickets/KAN-4/KAN-4_plain.txt` lines 4-5
- **Ticket description:** `All the java code needs to be reformattted to Google Code standards`
  - Source: `tickets/KAN-4/KAN-4_plain.txt` lines 10-11

## Verified Implementation Artifact Facts
- **Research file exists and marks readiness as sufficient**
  - Source: `tickets/KAN-4/KAN-4_research.md` line 40
- **Solution proposal expects format-only change for Java files**
  - Source: `tickets/KAN-4/KAN-4_solution.md` lines 3-10
- **Todo has pending manual IntelliJ profile verification**
  - Source: `tickets/KAN-4/KAN-4_todo.md` lines 12-17
- **Todo calls out non-formatting diffs in `ChatService` and `MessageType` as pending safety item**
  - Source: `tickets/KAN-4/KAN-4_todo.md` lines 29-33

## Verified Architecture Facts (Code-level)
- **API entrypoint:** `POST /api/chat` in `ChatController.chat(...)`
  - Source: `src/main/java/com/clippy/controller/ChatController.java` lines 17-20
- **Controller delegates to router:** `router.route(body.get("message"))`
  - Source: `src/main/java/com/clippy/controller/ChatController.java` line 19
- **Router classifies message and dispatches to typed handlers**
  - Source: `src/main/java/com/clippy/service/ChatMessageRouter.java` lines 34-40
- **Calendar handler flow:** extract date range -> fetch calendar events -> call chat with context
  - Source: `src/main/java/com/clippy/handler/CalendarRequestHandler.java` lines 25-27
- **Slack handler flow:** extract channel -> fetch messages -> call chat with context
  - Source: `src/main/java/com/clippy/handler/SlackRequestHandler.java` lines 25-28
- **ChatService classification categories currently include `CALENDAR`, `SLACK`, `GENERAL` and fallback to `GENERAL`**
  - Source: `src/main/java/com/clippy/service/ChatService.java` lines 31-35 and 47-57
- **`MessageType` enum currently includes `GENERAL`**
  - Source: `src/main/java/com/clippy/model/MessageType.java` lines 3-7
- **A `GeneralRequestHandler` file exists and maps `MessageType.GENERAL` to `chatService.chatGeneral(...)`**
  - Source: `src/main/java/com/clippy/handler/GeneralRequestHandler.java` lines 17-24
- **Calendar integration uses Google Calendar API OAuth flow and returns formatted event list**
  - Source: `src/main/java/com/clippy/service/CalendarService.java` lines 55-67 and 77-115
- **Slack integration uses bot token + conversations history API**
  - Source: `src/main/java/com/clippy/service/SlackService.java` lines 48-54
- **Runtime defaults:** server port 8080, Ollama base-url `http://localhost:11434`, model `llama3.2`
  - Source: `src/main/resources/application.yml` lines 1-10

## Uncertain / Not Verified
- **Plan-review or diff-review fact files for this ticket:** not found in ticket folder.
  - Source: file search query `tickets/KAN-4/*facts.md` returned none
- **Whether IntelliJ XML formatter profile was used for the committed formatting:** not verified from code alone; todo says CLI formatter was used and IntelliJ verification is pending.
  - Source: `tickets/KAN-4/KAN-4_todo.md` lines 16-17
- **Open PR metadata (title/body/reviewers/state):** not verified in this run (only PR creation URL was observed previously in terminal output).

