# UI Test Plan

This file records console UI test cases for Aegis. Each test case has an aim,
the exact console input, and the expected output fragments that must appear in
order.

The `test-ui` skill reads this file and runs the test cases. Expected output is
checked as ordered fragments rather than one complete transcript, so tests stay
focused on behavior while allowing the banner to change.

## Test Case 1: Add todo tasks and list them

Aim: Check that todo commands add tasks and `list` shows them in order with
todo and done-status icons.

Input:

```text
todo read book
todo return book
list
bye
```

Expected output contains:

```text
[T][ ] read book
[T][ ] return book
Here are the tasks in your list:
1.[T][ ] read book
2.[T][ ] return book
Bye. Hope to see you again soon!
```

## Test Case 2: Mark and unmark task status

Aim: Check that `mark` and `unmark` update the selected task and that `list`
shows the current status.

Input:

```text
todo read book
todo return book
mark 2
list
unmark 2
list
bye
```

Expected output contains:

```text
Nice! I've marked this task as done:
[T][X] return book
Here are the tasks in your list:
1.[T][ ] read book
2.[T][X] return book
OK, I've marked this task as not done yet:
[T][ ] return book
Here are the tasks in your list:
1.[T][ ] read book
2.[T][ ] return book
Bye. Hope to see you again soon!
```

## Test Case 3: Add deadline and event tasks

Aim: Check that deadline and event commands preserve their date/time details in
the displayed task.

Input:

```text
deadline submit report /by 11/10/2019 5pm
event project meeting /from Mon 2pm /to 4pm
list
bye
```

Expected output contains:

```text
[D][ ] submit report (by: 11/10/2019 5pm)
[E][ ] project meeting (from: Mon 2pm to: 4pm)
Here are the tasks in your list:
1.[D][ ] submit report (by: 11/10/2019 5pm)
2.[E][ ] project meeting (from: Mon 2pm to: 4pm)
Bye. Hope to see you again soon!
```
