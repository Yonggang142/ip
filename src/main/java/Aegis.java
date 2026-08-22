
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
/**
 * Runs the Aegis chatbot, which manages tasks through command-line input.
 */
public class Aegis {

    private static final String startMessage = "Hi! This is Aegis!\n"
            + "What can I do for you today?\n";

    private static final String line = "_____________________________________________________________";

    private static final String endMessage = "Bye. See you soon!";

    private static final String filePath = "./data/Aegis.txt";

    private static final String banner = "    _              _     \n"
            + "   / \\   ___  __ _(_)___ \n"
            + "  / _ \\ / _ \\/ _` | / __|\n"
            + " / ___ \\  __/ (_| | \\__ \\\n"
            + "/_/   \\_\\___|\\__, |_|___/\n"
            + "              |___/      \n";


    private static final ArrayList<Task> storage = new ArrayList<>();

    public static void initBot() {

        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("File already exists");
        } else {

            try {
                boolean isCreated = file.createNewFile();
                if (isCreated) {
                    System.out.println("File has been created");
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
                return;
            }

        }

        try {
            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                String currLine = s.nextLine();
                String[] parts = currLine.split(" ");
                String identifier = parts[0];

                switch (identifier) {
                    case "todo": {
                        if (parts.length != 2) {
                            throw new AegisException("Incorrect todo format in file");
                        }
                        storage.add(new ToDo(parts[1]));

                    }
                    case "deadline": {
                        if (parts.length != 3) {
                            throw new AegisException("Incorrect deadline format in file");
                        }
                        storage.add(new Deadline(parts[1], parts[2]));
                    }

                    case "event": {
                        if (parts.length != 4) {
                            throw new AegisException("Incorrect event format in file");
                        }
                        storage.add(new Event(parts[1], parts[2], parts[3]));
                    }

                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find the missing file: " + e.getMessage());
        } catch (AegisException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(line);
        System.out.println(banner);
        System.out.println(startMessage);
        System.out.println(line);
    }

    public static void main(String[] args) {

        initBot();

        Scanner scanner = new Scanner(System.in);

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
                        for (int i = 0; i < storage.size(); i++) {

                            System.out.println((i + 1) + "." + storage.get(i));
                        }
                        System.out.println(line);
                        break;
                    }

                    case "delete": {
                        if (details.trim().isEmpty()) {
                            throw new AegisException("Please give me a task number to delete.");
                        }

                        int deleteIndex = Integer.parseInt(details.trim()) - 1;

                        if (deleteIndex < 0 || deleteIndex >= storage.size()) {
                            throw new AegisException("Sorry, that task number does not exist.");
                        }

                        Task deletedTask = storage.get(deleteIndex);

                        storage.remove(deleteIndex);
                        System.out.println(line);
                        System.out.println("I've deleted this task for you");
                        System.out.println(deletedTask);
                        System.out.println("Now you have " + storage.size() + " tasks in the list.");
                        System.out.println(line);
                        break;
                    }

                    case "mark": {
                        if (details.trim().isEmpty()) {
                            throw new AegisException("Please give me a task number to mark.");

                        }

                        int markIndex = Integer.parseInt(details.trim()) - 1;

                        if (markIndex < 0 || markIndex >= storage.size()) {
                            throw new AegisException("Sorry, that task number does not exist.");
                        }

                        Task currentTask = storage.get(markIndex);
                        currentTask.mark();
                        System.out.println(line);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(storage.get(markIndex));
                        System.out.println(line);
                        break;

                    }

                    case "unmark": {
                        if (details.trim().isEmpty()) {
                            throw new AegisException("Please give me a task number to unmark.");

                        }

                        int unmarkIndex = Integer.parseInt(details.trim()) - 1;

                        if (unmarkIndex < 0 || unmarkIndex >= storage.size()) {
                            throw new AegisException("Sorry, that task number does not exist.");
                        }

                        Task currentTask = storage.get(unmarkIndex);
                        currentTask.unmark();

                        System.out.println(line);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println(storage.get(unmarkIndex));
                        System.out.println(line);
                        break;

                    }

                    case "todo": {

                        Task newTask = createTodoTask(details);
                        storage.add(newTask);

                        System.out.println(line);
                        System.out.println("OK, I've added a new task: ");
                        System.out.println(newTask);
                        System.out.println("Now you have " + storage.size() + " tasks in the list");
                        System.out.println(line);
                        break;
                    }

                    case "deadline": {

                        Task newTask = createDeadlineTask(details);
                        storage.add(newTask);

                        System.out.println(line);
                        System.out.println("Got it. I've added this task:");
                        System.out.println(newTask);
                        System.out.println("Now you have " + storage.size() + " tasks in the list.");
                        System.out.println(line);
                        break;
                    }

                    case "event": {

                        Task newTask = createEventTask(details);
                        storage.add(newTask);

                        System.out.println(line);
                        System.out.println("OK, I've added a new task: ");
                        System.out.println(newTask);
                        System.out.println("Now you have " + storage.size() + " tasks in the list");
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
    /**
     * Creates a to-do task from the command details.
     *
     * @param details Description text entered after the todo command.
     * @throws AegisException If the description is empty.
     */
    private static Task createTodoTask(String details) throws AegisException {
        if (details.trim().isEmpty()) {
            throw new AegisException("The description of a todo cannot be empty.");

        }
        return new ToDo(details);

    }


    /**
     * Creates a deadline task from command details containing a description and deadline time.
     *
     * @param details Description and deadline time entered after the deadline command.
     * @throws AegisException If the /by separator is missing, the description is empty, or the deadline time is empty.
     */
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

    /**
     * Creates an event task from command details containing a description, start time, and end time.
     *
     * @param details Description, start time, and end time entered after the event command.
     * @throws AegisException If /from or /to is missing, the description is empty, the start time is empty,
     *         or the end time is empty.
     */
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
