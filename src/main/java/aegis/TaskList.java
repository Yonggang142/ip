package aegis;

import aegis.task.Task;

import java.util.ArrayList;

/**
 * Represents a list of tasks.
 * Wraps an internal ArrayList and provides operations for adding,
 * retrieving, deleting, marking and unmarking tasks.
 */

public class TaskList {

    private final ArrayList<Task> tasks;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList using the given list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Gets a task from the list given an index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the current number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Deletes the task at the particular index.
     */
    public void delete(int index) {
        tasks.remove(index);
    }

    /**
     * Marks the task at the particular index as complete.
     */
    public void mark(int index) {
        tasks.get(index).mark();
    }

    /**
     * Marks the task at the particular index as not complete.
     */
    public void unmark(int index) {
        tasks.get(index).unmark();
    }
}