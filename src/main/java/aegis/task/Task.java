package aegis.task;

/**
 * Represents a task in the Aegis task list.
 */
public abstract class Task {

    protected String description;

    protected boolean isDone;

    /**
     * Constructs a task.
     *
     * @param description Description of the task.
     * @param isDone Whether the task has been marked as done.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the display icon for the task completion status.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void unmark() {
        this.isDone = false;
    }


    public void mark() {
        this.isDone = true;
    }

    /**
     * Returns the letter icon used to identify this task's type.
     */
    public abstract String getTypeIcon();


    /**
     * Returns the display form of this task.
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description;
    }


    /**
     * Returns the file storage format of this task.
     */
    public abstract String getFileSaveFormat();

}
