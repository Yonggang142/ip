# UI Test Plan

This file records command-line UI test cases for aegis.Aegis. Each test case is run as one chatbot session: the commands are sent to `aegis.Aegis` in order, and the full console output is compared with the expected output.

## TC01 - Add Todo Then List

Aim: Verify that a todo task can be added and shown in the task list.

```commands
todo borrow book
list
bye
```

```expected
____________________________________________________________
 ____        _         
|  _ \ _   _| | _____  
| | | | | | | |/ / _ \
| |_| | |_| |   <  __/
|____/ \__,_|_|\_\___|

Hey there! Aegis is awake and ready to help.
What quest are we tackling today?

____________________________________________________________
____________________________________________________________
Ta-da! I added this task:
[T][ ] borrow book
You now have 1 task in your quest log.
____________________________________________________________
____________________________________________________________
Here is your current quest log:
1.[T][ ] borrow book
____________________________________________________________
____________________________________________________________
Bye for now! Your tasks and I will behave.
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
 ____        _         
|  _ \ _   _| | _____  
| | | | | | | |/ / _ \
| |_| | |_| |   <  __/
|____/ \__,_|_|\_\___|

Hey there! Aegis is awake and ready to help.
What quest are we tackling today?

____________________________________________________________
____________________________________________________________
Give me a task number so I know which quest to poke.
____________________________________________________________
____________________________________________________________
Ta-da! I added this task:
[T][ ] ok
You now have 1 task in your quest log.
____________________________________________________________
____________________________________________________________
Here is your current quest log:
1.[T][ ] ok
____________________________________________________________
____________________________________________________________
Bye for now! Your tasks and I will behave.
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
 ____        _         
|  _ \ _   _| | _____  
| | | | | | | |/ / _ \
| |_| | |_| |   <  __/
|____/ \__,_|_|\_\___|

Hey there! Aegis is awake and ready to help.
What quest are we tackling today?

____________________________________________________________
____________________________________________________________
Deadline quests need a /by date.
____________________________________________________________
____________________________________________________________
Ta-da! I added this task:
[T][ ] after
You now have 1 task in your quest log.
____________________________________________________________
____________________________________________________________
Here is your current quest log:
1.[T][ ] after
____________________________________________________________
____________________________________________________________
Bye for now! Your tasks and I will behave.
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
 ____        _         
|  _ \ _   _| | _____  
| | | | | | | |/ / _ \
| |_| | |_| |   <  __/
|____/ \__,_|_|\_\___|

Hey there! Aegis is awake and ready to help.
What quest are we tackling today?

____________________________________________________________
____________________________________________________________
Event quests need a /to date.
____________________________________________________________
____________________________________________________________
Ta-da! I added this task:
[T][ ] after
You now have 1 task in your quest log.
____________________________________________________________
____________________________________________________________
Here is your current quest log:
1.[T][ ] after
____________________________________________________________
____________________________________________________________
Bye for now! Your tasks and I will behave.
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
 ____        _         
|  _ \ _   _| | _____  
| | | | | | | |/ / _ \
| |_| | |_| |   <  __/
|____/ \__,_|_|\_\___|

Hey there! Aegis is awake and ready to help.
What quest are we tackling today?

____________________________________________________________
____________________________________________________________
Ta-da! I added this task:
[T][ ] first
You now have 1 task in your quest log.
____________________________________________________________
____________________________________________________________
Ta-da! I added this task:
[T][ ] second
You now have 2 tasks in your quest log.
____________________________________________________________
____________________________________________________________
Poof! I removed this task:
[T][ ] first
You now have 1 task in your quest log.
____________________________________________________________
____________________________________________________________
Here is your current quest log:
1.[T][ ] second
____________________________________________________________
____________________________________________________________
Bye for now! Your tasks and I will behave.
____________________________________________________________
```
