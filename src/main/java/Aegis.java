import java.util.Scanner;
public class Aegis {
    public static void main(String[] args) {
        String banner = "    _              _     \n"
                + "   / \\   ___  __ _(_)___ \n"
                + "  / _ \\ / _ \\/ _` | / __|\n"
                + " / ___ \\  __/ (_| | \\__ \\\n"
                + "/_/   \\_\\___|\\__, |_|___/\n"
                + "              |___/      \n";

        String startMessage = "Hi! This is Aegis!\n"
                + "What can I do for you today?\n";
        String endMessage = "Bye. See you soon!";


        String line = "____________________________________________________________";
        System.out.println(line);
        System.out.println(banner);
        System.out.println(startMessage);
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);

        Task[] storage = new Task[100];

        int count = 0;

        while (true) {
            try {
                String command = scanner.nextLine();
                String[] parts = command.split(" ", 2);
                String action = parts[0];
                String details = parts.length > 1 ? parts[1] : "";

                switch (action) {
                    case "bye": {
                        System.out.println(line);
                        System.out.println(endMessage);
                        System.out.println(line);
                        return;
                    }

                    case "list": {

                        System.out.println(line);
                        System.out.println("Here are the tasks in the list:");
                        for (int i = 0; i < count; i++) {
                            Task currentTask = storage[i];

                            System.out.println((i + 1) + "." + storage[i]);
                        }
                        System.out.println(line);
                        break;
                    }

                    case "mark": {
                        if (details.trim().isEmpty()) {
                            throw new AegisException("Please give me a task number to mark.");

                        }

                        int markIndex = Integer.parseInt(details.trim()) - 1;

                        if (markIndex < 0 || markIndex >= count) {
                            throw new AegisException("Sorry, that task number does not exist.");
                        }

                        Task currentTask = storage[markIndex];
                        currentTask.mark();
                        System.out.println(line);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(storage[markIndex]);
                        System.out.println(line);
                        break;

                    }

                    case "unmark": {
                        if (details.trim().isEmpty()) {
                            throw new AegisException("Please give me a task number to mark.");

                        }

                        int unmarkIndex = Integer.parseInt(details.trim()) - 1;

                        if (unmarkIndex < 0 || unmarkIndex >= count) {
                            throw new AegisException("Sorry, that task number does not exist.");
                        }

                        Task currentTask = storage[unmarkIndex];
                        currentTask.unmark();

                        System.out.println(line);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println(storage[unmarkIndex]);
                        System.out.println(line);
                        break;

                    }

                    case "todo": {

                        Task newTask = createTodoTask(details);
                        storage[count] = newTask;
                        count += 1;
                        System.out.println(line);
                        System.out.println("OK, I've added a new task: ");
                        System.out.println(newTask);
                        System.out.println("Now you have " + count + " tasks in the list");
                        System.out.println(line);
                        break;
                    }

                    case "deadline": {

                        Task newTask = createDeadlineTask(details);
                        storage[count] = newTask;
                        count += 1;

                        System.out.println(line);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(newTask);
                        System.out.println("Now you have " + count + " tasks in the list.");
                        System.out.println(line);
                        break;
                    }

                    case "event": {

                        Task newTask = createEventTask(details);
                        storage[count] = newTask;
                        count += 1;
                        System.out.println(line);
                        System.out.println("OK, I've added a new task: ");
                        System.out.println(newTask);
                        System.out.println("Now you have " + count + " tasks in the list");
                        System.out.println(line);

                        break;
                    }

                    default: {
                        System.out.println(line);
                        System.out.println("Sorry, I have no idea what it means!");
                        System.out.println(line);
                        break;
                    }
                }
            } catch (AegisException e) {
                System.out.println(line);
                System.out.println(e.getMessage());
                System.out.println(line);

            } catch (NumberFormatException e) {
                System.out.println(line);
                System.out.println("Please give me a valid task number.");
                System.out.println(line);

            }

        }
    }
    private static Task createTodoTask(String details) throws AegisException {
        if (details.trim().isEmpty()) {
            throw new AegisException("The description of a todo cannot be empty.");

        }
        return new ToDo(details);

    }


    private static Task createDeadlineTask(String details) throws AegisException {
        if (!details.contains(" /by ")) {
            throw new AegisException("Please include /by for deadlines.");
        }

        String[] deadlineParts = details.split(" /by ", 2);
        String description = deadlineParts[0];
        String by = deadlineParts[1];

        if (description.trim().isEmpty()) {
            throw new AegisException("The description of a deadline cannot be empty.");
        }

        if (by.trim().isEmpty()) {
            throw new AegisException("The deadline time cannot be empty.");
        }

        return new Deadline(description, by);
    }

    private static Task createEventTask(String details) throws AegisException {
        if (!details.contains(" /from ")) {
            throw new AegisException("Please include /from for events.");
        }

        String[] eventParts = details.split(" /from ", 2);
        String description = eventParts[0];
        String timeDetails = eventParts[1];

        String[] timeParts = timeDetails.split(" /to ", 2);
        if (timeParts.length < 2) {
            throw new AegisException("Please include /to for events.");
        }
        String from = timeParts[0];
        String to = timeParts[1];

        if (description.trim().isEmpty()) {
            throw new AegisException("The description of an event cannot be empty.");
        }

        if (from.trim().isEmpty()) {
            throw new AegisException("starting time cannot be empty.");
        }

        if (to.trim().isEmpty()) {
            throw new AegisException("ending time cannot be empty");
        }


        return new Event(description, from, to);
    }


}
