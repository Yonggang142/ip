package aegis.task;

/**
 * Represents a ToDo type task in the Aegis task list.
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo task.
     *
     * @param description Description of the task.
     * @param isDone Whether the task has been marked as done.
     */
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    @Override
    public String getTypeIcon() {
        return "T";
    }


    @Override
    public String getFileSaveFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

}
