/**
 * Represents a task that takes place from a start time to an end time.
 */
public class Event extends Task {

    /** Start time of the event. */
    protected String start;

    /** End time of the event. */
    protected String end;

    /**
     * Creates an event task with a description, start time, and end time.
     *
     * @param description Description of the task.
     * @param start Start time of the event.
     * @param end End time of the event.
     */
    public Event(String description, String start, String end, boolean isDone) {
        super(description, isDone);
        this.start = start;
        this.end = end;

    }

    /**
     * Returns the type icon for event tasks.
     */
    @Override
    public String getTypeIcon() {
        return "E";
    }


    /**
     * Returns the display form of this event task.
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description + " (from: " + start + " to: " + end + ")";
    }
}
