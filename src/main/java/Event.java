/**
 * Represents a task that takes place from a start time to an end time.
 */
public class Event extends Task {

    protected String start;

    protected String end;

    /**
     * Returns void
     * Creates an event task.
     *
     * @param description Description of the task.
     * @param start Start time of the event.
     * @param end End time of the event.
     * @param isDone Whether the task has been marked as done.
     */
    public Event(String description, String start, String end, boolean isDone) {
        super(description, isDone);
        this.start = start;
        this.end = end;

    }

    @Override
    public String getTypeIcon() {
        return "E";
    }


    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description + " (from: " + start + " to: " + end + ")";
    }


    @Override
    public String getFileSaveFormat() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + start + " | " + end;
    }


}
