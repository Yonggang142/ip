/**
 * Represents a ToDo type task in the Aegis task list.
 */

public class ToDo extends Task {

    /**
     * Creates a todo task with the given description.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns the type icon for todo tasks.
     */
    @Override
    public String getTypeIcon() {
        return "T";
    }

}
