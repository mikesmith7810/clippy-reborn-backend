# KAN-8 Fix Proposal

## Bug Summary
- Ticket: `KAN-8`
- Issue: `POST /api/chat` with payload `{"message": null}` returns `500 Internal Server Error`.
- Reproduced: yes (see `KAN-8_repro.md`).

## Root Cause (Likely)
`ChatService.classify(String message)` assumes non-null input and immediately executes:
- `message = message.toLowerCase();`

When `message` is null, this throws `NullPointerException`, propagating to an unhandled server error.

## Proposed Fix (Preferred)
Validate incoming request in `ChatController` and return `400 Bad Request` for null/blank `message`.

### Changes
1. Update `ChatController.chat(...)`:
   - Guard `body == null` and `body.get("message") == null` and blank content.
   - Throw `ResponseStatusException(HttpStatus.BAD_REQUEST, "message is required")`.
2. Keep router/service behavior unchanged for valid inputs.

### Why this approach
- API-level validation gives a clear client contract and error semantics.
- Prevents invalid input from reaching routing/LLM logic.
- Minimal risk and small, focused change.

## Optional Defensive Hardening
Also add a null/blank guard in `ChatService.classify(...)` as a secondary defensive layer (return `MessageType.GENERAL` or throw a controlled exception). This is optional for this ticket if API boundary validation is accepted as sufficient.

## Test Plan
Add controller-level tests for `/api/chat`:
1. `{"message": null}` -> `400`
2. `{}` -> `400`
3. `{"message":"   "}` -> `400`
4. `{"message":"hello"}` -> non-500 (happy path smoke)

## Risk Assessment
- Low risk: localized to request validation path.
- Backward compatibility: clients sending invalid payloads now get proper `400` instead of `500`.

## Files Expected to Change
- `src/main/java/com/clippy/controller/ChatController.java`
- `src/test/java/com/clippy/...` (new/updated controller tests)

## Approval Gate
No code edits have been made yet.
Please explicitly approve this proposal before implementation.
