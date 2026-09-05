package aegis;

import java.io.IOException;

import aegis.task.Task;

/**
 * Performs the chatbot tasks depending on the command type.
 * Accepts multiple distinct user command types.
 */
public class Command {
    private final String commandName;
    private final Task task;
    private final int index;
    private final String detail;

    /**
     * Constructs a Command object identified by its action string,
     * optional task, task index, and optional command.
     */
    public Command(String commandName, Task task, int index, String detail) {
        assert commandName != null : "Command name should be assigned by Parser";
        this.commandName = commandName;
        this.task = task;
        this.index = index;
        this.detail = detail;
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
    public String execute(TaskList tasks, Ui ui, Storage storage) throws AegisException, IOException {
        assert tasks != null : "Command execution requires a task list";
        assert ui != null : "Command execution requires a UI message builder";
        assert storage != null : "Command execution requires storage";
        return switch (commandName) {
            case "todo":
            case "deadline":
            case "event":
                assert task != null : "Task creation commands should carry a parsed task";
                tasks.add(task);
                storage.saveToFile(tasks);
                yield ui.getTaskAddedMessage(task, tasks.size());
            case "delete":
                if (index < 0 || index >= tasks.size()) {
                    throw new AegisException("Sorry, that task number does not exist.");
                }
                Task deleted = tasks.get(index);
                tasks.delete(index);
                storage.saveToFile(tasks);
                yield ui.getDeletedTaskMessage(deleted, tasks.size());
            case "mark":
                if (index < 0 || index >= tasks.size()) {
                    throw new AegisException("Sorry, that task number does not exist.");
                }
                Task markTask = tasks.get(index);
                tasks.mark(index);
                storage.saveToFile(tasks);
                yield ui.getTaskStatusMessage(markTask, true);
            case "unmark":
                if (index < 0 || index >= tasks.size()) {
                    throw new AegisException("Sorry, that task number does not exist.");
                }
                Task unmarkTask = tasks.get(index);
                tasks.unmark(index);
                storage.saveToFile(tasks);
                yield ui.getTaskStatusMessage(unmarkTask, false);
            case "list":
                yield ui.getTaskListMessage(tasks);
            case "bye":
                yield ui.getEndMessage();
            case "find":
                yield ui.getFindMessage(tasks, detail);
            default:
                yield ui.getDefaultMessage();
        };
    }

    public String getCommandName() {
        return commandName;
    }

    public Task getTask() {
        return task;
    }

}
