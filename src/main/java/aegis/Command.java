package aegis;

import aegis.task.Task;

import java.io.IOException;

public class Command {
    private final String commandName;
    private final Task task;
    private final int index;

    public Command(String commandName, Task task, int index) {
        this.commandName = commandName;
        this.task = task;
        this.index = index;
    }

    public boolean hasEnded() {
        return commandName.equals("bye");
    }

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

    public int getIndex() {
        return index;
    }
}