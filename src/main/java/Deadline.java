/**
 * Represents a task that should be completed by a specified time.
 */
public class Deadline extends Task {

    protected String end;

    /**
     * Returns void
     * Constructor to create a deadline task.
     *
     * @param description Description of the task.
     * @param end Deadline time by which the task should be completed.
     * @param isDone Whether the task has been marked as done.
     */
    public Deadline(String description, String end, boolean isDone) {
        super(description, isDone);
        this.end = end;

    }


    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description + " (by: " + end + ")";
    }

    @Override
    public String getFileSaveFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + end;
    }

}
