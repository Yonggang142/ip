/**
 * Represents a to-do type task in the Aegis task list.
 */
public class ToDo extends Task {

    /**
     * Creates a to-do task with the given description.
     *
     * @param description Description of the task.
     */
    public ToDo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns the type icon for to-do tasks.
     */
    @Override
    public String getTypeIcon() {
        return "T";
    }

}
