package aegis;

import aegis.task.Task;

import java.io.IOException;

/**
 * Performs the chatbot tasks depending on the command type.
 * Accepts multiple distinct user command types.
 */
public class Command {
    private final String commandName;
    private final Task task;
    private final int index;

    /**
     * Constructs a Command object identified by its action string,
     * optional task, and task index.
     */
    public Command(String commandName, Task task, int index) {
        this.commandName = commandName;
        this.task = task;
        this.index = index;
    }

    /**
     * Checks if the command is equal to "bye" to end the conversation.
     */
    public boolean hasEnded() {
        return commandName.equals("bye");
    }

    /**
     * Executes the task based on the corresponding command.
     *
     * @param tasks TaskList class for the current chat session.
     * @param ui UI class.
     * @param storage Storage class.
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AegisException, IOException {
        switch (commandName) {
            case "todo":
            case "deadline":
            case "event":
                tasks.add(task);
                ui.showTaskAdded(task, tasks.size());
                storage.saveToFile(tasks);
                break;
            case "delete":
                if (index < 0 || index >= tasks.size()) {
                    throw new AegisException("Sorry, that task number does not exist.");
                }
                Task deleted = tasks.get(index);
                tasks.delete(index);
                ui.showDeletedTask(deleted, tasks.size());
                storage.saveToFile(tasks);
                break;
            case "mark":
                if (index < 0 || index >= tasks.size()) {
                    throw new AegisException("Sorry, that task number does not exist.");
                }
                Task markTask = tasks.get(index);
                tasks.mark(index);
                ui.showTaskStatus(markTask, true);
                storage.saveToFile(tasks);
                break;
            case "unmark":
                if (index < 0 || index >= tasks.size()) {
                    throw new AegisException("Sorry, that task number does not exist.");
                }
                Task unmarkTask = tasks.get(index);
                tasks.unmark(index);
                ui.showTaskStatus(unmarkTask, false);
                storage.saveToFile(tasks);
                break;
            case "list":
                ui.showTaskList(tasks);
                break;
            case "bye":
                ui.showEndMessage();
                break;
            default:
                ui.showDefaultMessage();
                break;
        }
    }

    public String getCommandName() {
        return commandName;
    }

    public Task getTask() {
        return task;
    }

}