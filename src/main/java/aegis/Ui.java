package aegis;

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

    private static final String LINE = "_____________________________________________________________";

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
     * Displays the error message.
     */
    public void showError(String message) {
        System.out.println(LINE);
        System.out.println(message);
        System.out.println(LINE);
    }

    /**
     * Displays the starting message.
     */
    public void showStartMessage() {
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println(START_MESSAGE);
        System.out.println(LINE);
    }

    /**
     * Displays a loading error message.
     */
    public void showLoadingError() {
        System.out.println("Error with loading tasks from storage");
    }

    /**
     * Displays the ending message after user says "bye"
     */
    public void showEndMessage() {
        System.out.println(LINE);
        System.out.println(END_MESSAGE);
        System.out.println(LINE);
    }

    /**
     * Displays a message when the command is invalid.
     */
    public void showDefaultMessage() {
        System.out.println(LINE);
        System.out.println("Sorry, I have no idea what it means!");
        System.out.println(LINE);
    }

    /**
     * Displays a message when a task is being added.
     */
    public void showTaskAdded(Task task, int size) {
        System.out.println(LINE);
        System.out.println("OK, I've added a new task: ");
        System.out.println(task);
        System.out.println("Now you have " + size + " tasks in the list");
        System.out.println(LINE);
    }

    /**
     * Displays the numbered list of all tasks.
     */
    public void showTaskList(TaskList taskList) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in the list:");
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println((i + 1) + "." + taskList.get(i));
        }
        System.out.println(LINE);
    }

    /**
     * Displays a confirmation that a task has been deleted
     */
    public void showDeletedTask(Task task, int size) {
        System.out.println(LINE);
        System.out.println("I've deleted this task for you");
        System.out.println(task);
        System.out.println("Now you have " + size + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Displays a confirmation that a task has been marked or unmarked
     */
    public void showTaskStatus(Task task, boolean isDone) {
        System.out.println(LINE);
        if (isDone) {
            System.out.println("Nice! I've marked this task as done:");
        } else {
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println(task);
        System.out.println(LINE);
    }


}
