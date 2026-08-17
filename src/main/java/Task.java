/**
 * Represents a task in the Aegis task list.
 */

public abstract class Task {

    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with the given description and marks it as not done.
     *
     * @param description the task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the display icon for the task completion status.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks this task as not completed.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Marks this task as completed.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Returns the one-letter icon used to identify this task's type.
     */
    public abstract String getTypeIcon(); // overriden by subclasses


    /**
     * Returns the display form of this task.
     */
    @Override
    public String toString() {
        return "[" + getTypeIcon() + "][" + getStatusIcon() + "] " + description;
    }

}
