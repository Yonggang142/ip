import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
/**
 * Runs the Aegis chatbot, which manages tasks through command-line input.
 */
public class Aegis {

    private static final String startMessage = """
            Hi! This is Aegis!
            What can I do for you today?
            """;

    private static final String line = "_____________________________________________________________";

    private static final String endMessage = "Bye. See you soon!";

    private static final String filePath = "./data/Aegis.txt";

    private static final String banner = """
                _              _    \s
               / \\   ___  __ _(_)___\s
              / _ \\ / _ \\/ _` | / __|
             / ___ \\  __/ (_| | \\__ \\
            /_/   \\_\\___|\\__, |_|___/
                          |___/     \s
            """;


    private static final ArrayList<Task> storage = new ArrayList<>();

    /**
     * Returns void.
     * Initializes the chatbot and loads saved tasks from the data file before showing the greeting.
     */
    public static void initBot() {

        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            System.out.println("File already exists");
        } else {

            try {
                if (path.getParent() != null && !Files.exists(path.getParent())) {
                    Files.createDirectories(path.getParent());
                }
                Files.createFile(path);
                System.out.println("File has been created");
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
                return;
            }

        }

        try {
            Scanner s = new Scanner(path);
            while (s.hasNext()) {
                String currLine = s.nextLine();
                String[] parts = currLine.split(" \\| ");

                if (parts.length < 2) {
                    throw new AegisException("Incorrect format in file");
                }
                String identifier = parts[0];
                String status = parts[1];

                if (!status.equals("0") && !status.equals("1")) {
                    throw new AegisException("Incorrect task status in file");
                }

                boolean isDone = status.equals("1");

                switch (identifier) {
                    case "T": {
                        if (parts.length != 3) {
                            throw new AegisException("Incorrect todo format in file");
                        }
                        storage.add(new ToDo(parts[2], isDone));
                        break;
                    }
                    case "D": {
                        if (parts.length != 4) {
                            throw new AegisException("Incorrect deadline format in file");
                        }
                        LocalDate by = LocalDate.parse(parts[3]);
                        storage.add(new Deadline(parts[2], by, isDone));
                        break;
                    }

                    case "E": {
                        if (parts.length != 5) {
                            throw new AegisException("Incorrect event format in file");
                        }

                        LocalDate start = LocalDate.parse(parts[3]);
                        LocalDate end = LocalDate.parse(parts[4]);
                        storage.add(new Event(parts[2], start, end, isDone));
                        break;
                    }

                    default: {
                        throw new AegisException("Unknown information in file");

                    }

                }

            }
        } catch (IOException e) {
            System.out.println("Cannot find the missing file: " + e.getMessage());

        } catch (DateTimeParseException e) {
            System.out.println("Dates in file do not adhere to parsable YYYY-MM-DD format.");
        } catch (AegisException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(line);
        System.out.println(banner);
        System.out.println(startMessage);
        System.out.println(line);
    }


    /**
     * Returns void.
     * Saves tasks to the data file.
     */
    public static void saveToFile() {
        StringBuilder textToAdd = new StringBuilder();

        for (Task task : storage) {
            textToAdd.append(task.getFileSaveFormat()).append(System.lineSeparator());
        }

        try {
            FileWriter fw = new FileWriter(filePath);
            fw.write(textToAdd.toString());
            fw.close();
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }


    }

    /**
     * Returns void.
     * Processes user commands until the user exits.
     */
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
                        saveToFile();
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
                        saveToFile();
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
                        saveToFile();
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

                        saveToFile();
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

                        saveToFile();
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

                        saveToFile();
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
     * Returns a to-do task.
     * Method creates a to-do task and returns it
     *
     * @param details Description text entered after the to-do command.
     * @throws AegisException If the description is empty.
     */
    private static Task createTodoTask(String details) throws AegisException {
        if (details.trim().isEmpty()) {
            throw new AegisException("The description of a todo cannot be empty.");

        }
        return new ToDo(details, false);

    }

    /**
     * Returns a deadline task.
     * Method creates a deadline task and returns it
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

        try {
            LocalDate byDate = LocalDate.parse(by);
            return new Deadline(description, byDate, false);
        } catch (DateTimeParseException e) {
            throw new AegisException("Dates must be in YYYY-MM-DD format.");
        }
    }

    /**
     * Returns a deadline task.
     * Method creates an event task and returns it
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
            throw new AegisException("Starting time cannot be empty.");
        }

        if (to.trim().isEmpty()) {
            throw new AegisException("Ending time cannot be empty");
        }
        try {
            LocalDate fromDate = LocalDate.parse(from);
            LocalDate toDate = LocalDate.parse(to);
            return new Event(description, fromDate, toDate, false);

        } catch (DateTimeParseException e) {
            throw new AegisException("Dates must be in YYYY-MM-DD format.");
        }

    }


}
