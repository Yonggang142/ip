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
    private final String keyword;

    /**
     * Constructs a Command object identified by its action string,
     * optional task, task index, and optional command.
     */
    public Command(String commandName, Task task, int index, String keyword) {
        this.commandName = commandName;
        this.task = task;
        this.index = index;
        this.keyword = keyword;
    }

    /**
     * Checks if the command is equal to "bye" to end the conversation.
     */
    public boolean hasEnded() {
        return commandName.equals("bye");
    }

    /**
     * Checks whether this command's task index points to an existing task.
     */
    private void checkIndexValidity(TaskList tasks) throws AegisException {
        if (index < 0 || index >= tasks.size()) {
            throw new AegisException("Sorry, that task number does not exist.");
        }
    }

    /**
     * Adds this command's parsed task and saves the updated task list.
     */
    private String executeAdd(TaskList tasks, Ui ui, Storage storage) throws IOException {
        tasks.add(task);
        storage.saveToFile(tasks);
        return ui.getTaskAddedMessage(task, tasks.size());
    }

    /**
     * Deletes this command's indexed task and saves the updated task list.
     */
    private String executeDelete(TaskList tasks, Ui ui, Storage storage) throws AegisException, IOException {
        checkIndexValidity(tasks);
        Task taskToDelete = tasks.get(index);
        tasks.delete(index);
        storage.saveToFile(tasks);
        return ui.getDeletedTaskMessage(taskToDelete, tasks.size());
    }

    /**
     * Marks this command's indexed task as done and saves the updated task list.
     */
    private String executeMark(TaskList tasks, Ui ui, Storage storage) throws AegisException, IOException {
        checkIndexValidity(tasks);
        Task taskToMark = tasks.get(index);
        tasks.mark(index);
        storage.saveToFile(tasks);
        return ui.getTaskStatusMessage(taskToMark, true);
    }

    /**
     * Marks this command's indexed task as not done and saves the updated task list.
     */
    private String executeUnmark(TaskList tasks, Ui ui, Storage storage) throws AegisException, IOException {
        checkIndexValidity(tasks);
        Task taskToUnmark = tasks.get(index);
        tasks.unmark(index);
        storage.saveToFile(tasks);
        return ui.getTaskStatusMessage(taskToUnmark, false);
    }

    /**
     * Executes the task based on the corresponding command.
     *
     * @param tasks TaskList class for the current chat session.
     * @param ui UI class.
     * @param storage Storage class.
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) throws AegisException, IOException {
        return switch (commandName) {
            case "todo", "deadline", "event" -> executeAdd(tasks, ui, storage);
            case "delete" -> executeDelete(tasks, ui, storage);
            case "mark" -> executeMark(tasks, ui, storage);
            case "unmark" -> executeUnmark(tasks, ui, storage);
            case "list" -> ui.getTaskListMessage(tasks);
            case "bye" -> ui.getEndMessage();
            case "find" -> ui.getFindMessage(tasks, keyword);
            default -> ui.getDefaultMessage();
        };
    }

    public String getCommandName() {
        return commandName;
    }

    public Task getTask() {
        return task;
    }

}
