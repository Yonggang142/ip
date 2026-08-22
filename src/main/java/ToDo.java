/**
 * Represents a to-do type task in the Aegis task list.
 */

public class ToDo extends Task {

    /**
     * Creates a to-do task with the given description.
     *
     * @param description Description of the task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns the type icon for to-do tasks.
     *
     * @return Type icon for to-do tasks.
     */
    @Override
    public String getTypeIcon() {
        return "T";
    }

}
