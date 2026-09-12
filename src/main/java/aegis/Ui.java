package aegis;

import java.io.IOException;
import java.util.ArrayList;

import aegis.task.Task;

/**
 * Handles the user interface for the Aegis application.
 * Provides methods to display messages, task lists and errors.
 */
public class Ui {

    private static final String START_MESSAGE = """
            Hey there! Aegis is awake and ready to help.
            What quest are we tackling today?
            """;

    private static final String END_MESSAGE = "Bye for now! Your tasks and I will behave.";

    private static final String BANNER = """
                _              _    \s
               / \\   ___  __ _(_)___\s
              / _ \\ / _ \\/ _` | / __|
             / ___ \\  __/ (_| | \\__ \\
            /_/   \\_\\___|\\__, |_|___/
                          |___/     \s
            """;

    /**
     * Returns the error message.
     */
    public String getErrorMessage(String message) {
        return message;
    }

    /**
     * Returns the starting message.
     */
    public String getStartMessage() {
        return BANNER + "\n" + START_MESSAGE;
    }

    /**
     * Returns a loading error message.
     */
    public String getLoadingErrorMessage() {
        return "Oops, I tripped while loading your saved tasks.";
    }

    /**
     * Returns the ending message after user says "bye".
     */
    public String getEndMessage() {
        return END_MESSAGE;
    }

    /**
     * Returns a message when the command is invalid.
     */
    public String getDefaultMessage() {
        return "Hmm, that command flew over my helmet. Try another one?";
    }

    /**
     * Returns a message when a task is being added.
     */
    public String getTaskAddedMessage(Task task, int totalTasks) {
        return "Ta-da! I added this task:\n"
                + task + "\n"
                + "You now have " + totalTasks + " " + getTaskWord(totalTasks) + " in your quest log.";
    }

    /**
     * Returns the numbered list of all tasks.
     */
    public String getTaskListMessage(TaskList tasks) {
        StringBuilder message = new StringBuilder();
        message.append("Here is your current quest log:").append("\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            message.append(i + 1).append(".").append(task).append("\n");
        }
        return removeTrailingNewline(message);
    }

    /**
     * Returns a confirmation that a task has been deleted.
     */
    public String getDeletedTaskMessage(Task task, int totalTasks) {
        return "Poof! I removed this task:\n"
                + task + "\n"
                + "You now have " + totalTasks + " " + getTaskWord(totalTasks) + " in your quest log.";
    }

    /**
     * Returns a confirmation that a task has been marked or unmarked.
     */
    public String getTaskStatusMessage(Task task, boolean isDone) {
        StringBuilder message = new StringBuilder();
        if (isDone) {
            message.append("Victory! I marked this task as done:").append("\n");
        } else {
            message.append("No worries, I put this task back in play:").append("\n");
        }
        message.append(task);
        return message.toString();
    }

    /**
     * Returns all the matching tasks given a matching keyword.
     */
    public String getFindMessage(TaskList tasks, String tag) {
        StringBuilder message = new StringBuilder();
        message.append("I found these matching quests:").append("\n");

        ArrayList<Task> matchingTasks = tasks.getMatchingTasks(tag);
        for (Task matchingTask : matchingTasks) {
            message.append(matchingTask).append("\n");
        }

        return removeTrailingNewline(message);
    }

    /**
     * Removes one trailing newline from a built message, if present.
     */
    private String removeTrailingNewline(StringBuilder message) {
        if (message.charAt(message.length() - 1) != '\n') {
            return message.toString();
        }
        message.deleteCharAt(message.length() - 1);
        return message.toString();
    }

    /**
     * Returns the correctly pluralised word for a task count.
     */
    private String getTaskWord(int totalTasks) {
        return totalTasks == 1 ? "task" : "tasks";
    }

    /**
     * Sorts the TaskList, stores the sorted list in files, and returns the sorted list message.
     */
    public String getSortedListMessage(TaskList tasks, Storage storage) throws IOException {
        tasks.sortByDate();
        storage.saveToFile(tasks);
        return "I shuffled your quests into date order:\n" + getTaskListMessage(tasks);
    }

}
