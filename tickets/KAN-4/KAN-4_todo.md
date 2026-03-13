# KAN-4 Todo

## Goal
Reformat Java files in `clippy-reborn-backend` to Google coding standards using the IntelliJ Google coding XML profile, with no functional changes.

## Checklist

- [x] **Confirm branch and clean working set**
  - Work on a dedicated branch for this ticket (for example `KAN-4-reformat-code`).
  - Verify uncommitted changes are expected before formatting.
  - Completed: branch `KAN-4-reformat-code` created.

- [ ] **Set formatter profile in IntelliJ**
  - Open IntelliJ settings for Java code style.
  - Ensure the Google coding XML profile is selected and active.
  - Confirm formatter applies to Java only.
  - Note: terminal implementation used `google-java-format` to apply Google style; IntelliJ profile verification is still pending.

- [x] **Format Java source files (`src/main/java`)**
  - Run reformat on `src/main/java/com/clippy/**/*.java`.
  - Do not modify non-Java files.

- [x] **Format Java test files (`src/test/java`)**
  - Run reformat on `src/test/java/com/clippy/**/*.java`.
  - Skip if no Java test files are present.

- [ ] **Review diffs for safety**
  - Check that changes are formatting-only (spacing, wrapping, imports, line breaks).
  - Revert any accidental behavioral or non-format edits.
  - Ensure `build/` artifacts and generated files were not touched.
  - Pending: non-formatting changes currently appear in `src/main/java/com/clippy/service/ChatService.java` and `src/main/java/com/clippy/model/MessageType.java`; these must be excluded from KAN-4 commit scope.

- [x] **Run validation tests**
  - Run `./gradlew test`.
  - If tests fail, investigate whether failures are unrelated or caused by unintended edits.

- [ ] **Prepare commit**
  - Stage only formatting changes.
  - Commit with message: `chore(KAN-4): reformat Java sources to Google coding standards`.

- [ ] **Final verification before PR**
  - Re-check `git diff --staged` for formatting-only content.
  - Confirm ticket key `KAN-4` is referenced in commit/PR.

## Helpful Commands

```bash
git checkout -b KAN-4-reformat-code
git status
./gradlew test
git add src/main/java src/test/java
git diff --staged
git commit -m "chore(KAN-4): reformat Java sources to Google coding standards"
```



