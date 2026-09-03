package aegis;

import java.util.ArrayList;

import aegis.task.Task;

/**
 * Handles the user interface for the Aegis application.
 * Provides methods to display messages, task lists and errors.
 */
public class Ui {

    private static final String START_MESSAGE = """
            Hi! This is Aegis!
            What can I do for you today?
            """;

    private static final String LINE = "____________________________________________________________";

    private static final String END_MESSAGE = "Bye. See you soon!";

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
        return LINE + "\n" + message + "\n" + LINE;
    }

    /**
     * Returns the starting message.
     */
    public String getStartMessage() {
        return LINE + "\n" + BANNER + "\n" + START_MESSAGE + "\n" + LINE;
    }

    /**
     * Returns a loading error message.
     */
    public String getLoadingErrorMessage() {
        return "Error with loading tasks from storage";
    }

    /**
     * Returns the ending message after user says "bye".
     */
    public String getEndMessage() {
        return LINE + "\n" + END_MESSAGE + "\n" + LINE;
    }

    /**
     * Returns a message when the command is invalid.
     */
    public String getDefaultMessage() {
        return LINE + "\nSorry, I have no idea what it means!\n" + LINE;
    }

    /**
     * Returns a message when a task is being added.
     */
    public String getTaskAddedMessage(Task task, int size) {
        return LINE + "\n"
                + "OK, I've added a new task: \n"
                + task + "\n"
                + "Now you have " + size + " tasks in the list\n"
                + LINE;
    }

    /**
     * Returns the numbered list of all tasks.
     */
    public String getTaskListMessage(TaskList taskList) {
        StringBuilder message = new StringBuilder();
        message.append(LINE).append("\n");
        message.append("Here are the tasks in the list:").append("\n");
        for (int i = 0; i < taskList.size(); i++) {
            message.append(i + 1).append(".").append(taskList.get(i)).append("\n");
        }
        message.append(LINE);
        return message.toString();
    }

    /**
     * Returns a confirmation that a task has been deleted.
     */
    public String getDeletedTaskMessage(Task task, int size) {
        return LINE + "\n"
                + "I've deleted this task for you\n"
                + task + "\n"
                + "Now you have " + size + " tasks in the list.\n"
                + LINE;
    }

    /**
     * Returns a confirmation that a task has been marked or unmarked.
     */
    public String getTaskStatusMessage(Task task, boolean isDone) {
        StringBuilder message = new StringBuilder();
        message.append(LINE).append("\n");
        if (isDone) {
            message.append("Nice! I've marked this task as done:").append("\n");
        } else {
            message.append("OK, I've marked this task as not done yet:").append("\n");
        }
        message.append(task).append("\n");
        message.append(LINE);
        return message.toString();
    }

    /**
     * Returns all the matching tasks given a matching keyword.
     */
    public String getFindMessage(TaskList tasks, String tag) {
        StringBuilder message = new StringBuilder();
        message.append(LINE).append("\n");
        message.append("Here are the matching tasks from the list: ").append("\n");

        ArrayList<Task> matchingTasks = tasks.getMatchingTasks(tag);
        for (Task matchingTask : matchingTasks) {
            message.append(matchingTask).append("\n");
        }

        message.append(LINE);
        return message.toString();
    }


}
