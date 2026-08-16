---
name: test-ui
description: Run this Java chatbot project's console UI tests from test/ui-test-plan.md. Use after code updates that affect chatbot commands, printed output, task storage, mark/unmark behavior, deadlines, events, or any other text UI behavior; also use when asked to test, verify, or review console output against expected command sessions.
---

# Test UI

## Overview

Run repeatable console UI checks for the Aegis chatbot using test cases stored
in `test/ui-test-plan.md`. Each test case supplies commands and expected output
fragments; the runner compiles the Java source, runs the program, records the
console input/output, and stops immediately on the first failure.

## Workflow

1. Read `test/ui-test-plan.md`.
2. Confirm the plan covers any behavior changed by the latest code update. If
   behavior changed, update the plan before running tests.
3. Run the bundled script from the project root:

```powershell
powershell -ExecutionPolicy Bypass -File .codex/skills/test-ui/scripts/run-ui-tests.ps1
```

4. Report the recorded console input and output. If a test fails, report the
   failed test name, expected fragments, and actual output.

## Test Plan Format

Add test cases to `test/ui-test-plan.md` using this structure:

~~~markdown
## Test Case 1: Short behavior name

Aim: Explain what behavior this checks.

Input:

```text
command one
command two
bye
```

Expected output contains:

```text
important output fragment
another fragment that should appear later
```
~~~

Expected output is checked as non-empty fragments in order. Use fragments that
are specific enough to catch regressions without copying the entire banner unless
the banner itself is under test.
