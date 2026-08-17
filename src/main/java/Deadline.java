/**
 * Represents a task that should be completed by a specified time.
 */
public class Deadline extends Task {

    protected String end;

    /**
     * Creates a deadline task with a description and deadline time.
     */
    public Deadline(String description, String end) {
        super(description);
        this.end = end;

    }

    /**
     * Returns the type icon for deadline tasks.
     */
    @Override
    public String getTypeIcon() {
        return "D";
    }

    /**
     * Returns the display form of this deadline task.
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description + " (by: " + end + ")";
    }

}
