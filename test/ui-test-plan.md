# UI Test Plan

This file records command-line UI test cases for Aegis. Each test case is run as one chatbot session: the commands are sent to `Aegis` in order, and the full console output is compared with the expected output.

## TC01 - Add Todo Then List

Aim: Verify that a todo task can be added and shown in the task list.

```commands
todo borrow book
list
bye
```

```expected
____________________________________________________________
    _              _     
   / \   ___  __ _(_)___ 
  / _ \ / _ \/ _` | / __|
 / ___ \  __/ (_| | \__ \
/_/   \_\___|\__, |_|___/
              |___/      

Hi! This is Aegis!
What can I do for you today?

____________________________________________________________
____________________________________________________________
OK, I've added a new task: 
[T][ ] borrow book
Now you have 1 tasks in the list
____________________________________________________________
____________________________________________________________
Here are the tasks in the list:
1.[T][ ] borrow book
____________________________________________________________
____________________________________________________________
Bye. See you soon!
____________________________________________________________
```

## TC02 - Invalid Mark Then Continue

Aim: Verify that an invalid `mark` command shows an error and the chatbot continues accepting commands.

```commands
mark
todo ok
list
bye
```

```expected
____________________________________________________________
    _              _     
   / \   ___  __ _(_)___ 
  / _ \ / _ \/ _` | / __|
 / ___ \  __/ (_| | \__ \
/_/   \_\___|\__, |_|___/
              |___/      

Hi! This is Aegis!
What can I do for you today?

____________________________________________________________
____________________________________________________________
Please give me a task number to mark.
____________________________________________________________
____________________________________________________________
OK, I've added a new task: 
[T][ ] ok
Now you have 1 tasks in the list
____________________________________________________________
____________________________________________________________
Here are the tasks in the list:
1.[T][ ] ok
____________________________________________________________
____________________________________________________________
Bye. See you soon!
____________________________________________________________
```

## TC03 - Malformed Deadline Then Continue

Aim: Verify that a deadline without `/by` shows an error and does not stop the chatbot.

```commands
deadline return book
todo after
list
bye
```

```expected
____________________________________________________________
    _              _     
   / \   ___  __ _(_)___ 
  / _ \ / _ \/ _` | / __|
 / ___ \  __/ (_| | \__ \
/_/   \_\___|\__, |_|___/
              |___/      

Hi! This is Aegis!
What can I do for you today?

____________________________________________________________
____________________________________________________________
Please include /by for deadlines.
____________________________________________________________
____________________________________________________________
OK, I've added a new task: 
[T][ ] after
Now you have 1 tasks in the list
____________________________________________________________
____________________________________________________________
Here are the tasks in the list:
1.[T][ ] after
____________________________________________________________
____________________________________________________________
Bye. See you soon!
____________________________________________________________
```

## TC04 - Malformed Event Then Continue

Aim: Verify that an event without `/to` shows an error and does not stop the chatbot.

```commands
event meeting /from 2pm
todo after
list
bye
```

```expected
____________________________________________________________
    _              _     
   / \   ___  __ _(_)___ 
  / _ \ / _ \/ _` | / __|
 / ___ \  __/ (_| | \__ \
/_/   \_\___|\__, |_|___/
              |___/      

Hi! This is Aegis!
What can I do for you today?

____________________________________________________________
____________________________________________________________
Please include /to for events.
____________________________________________________________
____________________________________________________________
OK, I've added a new task: 
[T][ ] after
Now you have 1 tasks in the list
____________________________________________________________
____________________________________________________________
Here are the tasks in the list:
1.[T][ ] after
____________________________________________________________
____________________________________________________________
Bye. See you soon!
____________________________________________________________
```

## TC05 - Delete Task Then List

Aim: Verify that deleting a task removes it from the list and shifts the remaining tasks forward.

```commands
todo first
todo second
delete 1
list
bye
```

```expected
____________________________________________________________
    _              _     
   / \   ___  __ _(_)___ 
  / _ \ / _ \/ _` | / __|
 / ___ \  __/ (_| | \__ \
/_/   \_\___|\__, |_|___/
              |___/      

Hi! This is Aegis!
What can I do for you today?

____________________________________________________________
____________________________________________________________
OK, I've added a new task: 
[T][ ] first
Now you have 1 tasks in the list
____________________________________________________________
____________________________________________________________
OK, I've added a new task: 
[T][ ] second
Now you have 2 tasks in the list
____________________________________________________________
____________________________________________________________
I've deleted this task for you
[T][ ] first
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in the list:
1.[T][ ] second
____________________________________________________________
____________________________________________________________
Bye. See you soon!
____________________________________________________________
```
