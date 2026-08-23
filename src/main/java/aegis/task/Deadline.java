package aegis.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that should be completed by a specified time.
 */
public class Deadline extends Task {

    protected LocalDate end;

    /**
     * Constructs a deadline task.
     *
     * @param description Description of the task.
     * @param end Time by which the task should be completed.
     * @param isDone Whether the task has been marked as done.
     */
    public Deadline(String description, LocalDate end, boolean isDone) {
        super(description, isDone);
        this.end = end;

    }


    @Override
    public String getTypeIcon() {
        return "D";
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description
                + " (by: " + end.format(formatter) + ")";
    }

    @Override
    public String getFileSaveFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + end;
    }

}
