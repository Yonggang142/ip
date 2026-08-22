package aegis;

import aegis.task.Deadline;
import aegis.task.Event;
import aegis.task.Task;
import aegis.task.ToDo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    public Command parse(String command) throws AegisException {
        String[] parts = command.split(" ", 2);
        String action = parts[0];
        String details = parts.length > 1 ? parts[1] : "";

        switch (action) {
            case "bye":
                return new Command("bye", null, 0);
            case "list":
                return new Command("list", null, 0);
            case "todo":
                return new Command("todo", createTodoTask(details), 0);
            case "deadline":
                return new Command("deadline", createDeadlineTask(details), 0);
            case "event":
                return new Command("event", createEventTask(details), 0);
            case "delete":
                return new Command("delete", null, parseIndex(details, "delete"));
            case "mark":
                return new Command("mark", null, parseIndex(details, "mark"));
            case "unmark":
                return new Command("unmark", null, parseIndex(details, "unmark"));
            default:
                throw new AegisException("Sorry, I have no idea what it means!");
        }
    }

    private int parseIndex(String details, String commandName) throws AegisException {
        if (details.trim().isEmpty()) {
            throw new AegisException("Please give me a task number to " + commandName + ".");
        }
        try {
            return Integer.parseInt(details.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new AegisException("Please give me a valid task number.");
        }
    }

    private Task createTodoTask(String details) throws AegisException {
        if (details.trim().isEmpty()) {
            throw new AegisException("The description of a todo cannot be empty.");
        }
        return new ToDo(details, false);
    }

    private Task createDeadlineTask(String details) throws AegisException {
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
            return new Deadline(description, LocalDate.parse(by), false);
        } catch (DateTimeParseException e) {
            throw new AegisException("Dates must be in YYYY-MM-DD format.");
        }
    }

    private Task createEventTask(String details) throws AegisException {
        if (!details.contains(" /from ")) {
            throw new AegisException("Please include /from for events.");
        }
        String[] eventParts = details.split(" /from ", 2);
        String[] timeParts = eventParts[1].split(" /to ", 2);
        if (timeParts.length < 2) {
            throw new AegisException("Please include /to for events.");
        }
        String description = eventParts[0];
        String from = timeParts[0];
        String to = timeParts[1];
        if (description.trim().isEmpty()) {
            throw new AegisException("The description of an event cannot be empty.");
        }
        if (from.trim().isEmpty()) {
            throw new AegisException("Starting time cannot be empty.");
        }
        if (to.trim().isEmpty()) {
            throw new AegisException("Ending time cannot be empty.");
        }
        try {
            return new Event(description, LocalDate.parse(from), LocalDate.parse(to), false);
        } catch (DateTimeParseException e) {
            throw new AegisException("Dates must be in YYYY-MM-DD format.");
        }
    }
}