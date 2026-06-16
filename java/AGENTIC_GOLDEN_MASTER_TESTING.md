# Agentic Golden Master Testing

This project uses a **golden master (approval) test** wired into Claude Code `PostToolUse` hooks so that any AI agent refactoring Java files gets immediate, automatic feedback on whether it broke the observable behavior of the game.

## What is a golden master test?

A golden master test captures the full output of a program and saves it as a "golden" snapshot. Future runs compare against that snapshot. If anything in the output changes — even a single character — the test fails.

This is especially useful for legacy code where there are no unit tests and the behavior is complex. You don't need to understand the logic; you just need the output to stay the same.

In this project, `ApprovalTest.testApproval` captures everything written to stdout by `GameRunner.main()` and compares it to `ApprovalTest.testApproval.approved.txt`.

## How the hooks work

Two hooks fire on every `Edit` or `Write` tool call, in sequence:

```
Agent edits a .java file
        │
        ▼
Hook 1: run-approval-test.sh  (type: command)
        │
        ├─ file is not .java? → exit silently
        │
        └─ file is .java
                ├─ no approved file yet? → bootstrap golden master automatically
                └─ approved file exists → run mvn test -Dtest=ApprovalTest
                        ├─ PASSED → print "APPROVAL TEST: PASSED"
                        └─ FAILED → print diff of approved vs received
        │
        ▼
Hook 2: analysis agent  (type: agent)
        │
        reads approved + received files
        │
        ├─ identical → { "ok": true }
        └─ differ    → { "ok": false, "reason": "<diagnosis>" }
                        e.g. "Chet escaped the penalty box one turn early —
                              likely due to the roll condition in Game.java:87"
```

The shell script gives raw speed. The agent hook adds intelligence: instead of handing the main agent a raw diff, it explains **what game behavior changed and which code is likely responsible**.

Both results are returned to the main agent as `additionalContext` before its next step.

## Files

| File | Purpose |
|------|---------|
| `.claude/settings.json` | Registers both hooks for `Edit` and `Write` tool calls |
| `.claude/hooks/run-approval-test.sh` | Runs the approval test, bootstraps the golden master if missing |
| `src/test/java/com/adaptionsoft/games/trivia/ApprovalTest.java` | JUnit test that captures stdout and calls `Approvals.verify()` |
| `src/test/java/com/adaptionsoft/games/trivia/ApprovalTest.testApproval.approved.txt` | The golden snapshot |

## Trying it out

### Prerequisites

- Java 21 and Maven installed
- `jq` installed (used by the hook script to parse JSON)
- Claude Code CLI installed

### Step 1: Start a Claude Code session

No manual setup needed. Just open the project in Claude Code:

```bash
cd /mnt/c/dev/repos/trivia/java
claude
```

### Step 2: Ask the agent to refactor

Tell the agent something like:

> Refactor the `Game` class to extract the question-asking logic into a separate method.

**On the first Java file edit**, the shell hook bootstraps the golden master automatically:

```
APPROVAL TEST: No approved file found — bootstrapping golden master...
APPROVAL TEST: Golden master created. Future edits will be compared against this snapshot.
```

**On every subsequent edit**, if behavior is preserved:

```
APPROVAL TEST: PASSED
```

If behavior changed, the shell hook shows the raw diff and the agent hook explains what it means:

```
APPROVAL TEST: FAILED
--- approved
+++ received
-Chet was sent to the penalty box
+Chet's new location is 4

Hook analysis: Chet is no longer being sent to the penalty box after a wrong
answer. The wrongAnswer() method may have lost its penalty box assignment.
Check Game.java around the wrongAnswer() method.
```

### Testing the shell hook manually

You can invoke the shell script directly from the terminal without going through Claude Code:

```bash
# Simulate a Java file edit
printf '{"tool_input":{"file_path":"Game.java"}}' \
  | .claude/hooks/run-approval-test.sh

# Simulate a non-Java edit — should produce no output
printf '{"tool_input":{"file_path":"pom.xml"}}' \
  | .claude/hooks/run-approval-test.sh
```

## Why synchronous?

Both hooks run synchronously, blocking the agent's next step until they complete. This is intentional: the agent needs the diagnosis *before* it decides what to do next. An async hook would report results out of band, making it harder to correlate feedback with the edit that triggered it.

## Limitations

- **Maven startup overhead:** Each hook invocation starts a fresh Maven process (~2–4 seconds). This can be reduced with the Maven Daemon (`mvnd`) if available.
- **Agent hook latency:** The analysis agent adds an LLM call on top of Maven startup. For a fast edit loop, this is a trade-off for richer feedback.
- **Single test scope:** The hook only runs `ApprovalTest`. If you add other tests, adjust the `-Dtest=` argument or remove it to run the full suite.
