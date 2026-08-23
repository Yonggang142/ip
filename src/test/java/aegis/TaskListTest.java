package aegis;

import org.junit.jupiter.api.Test;
import aegis.task.Task;
import aegis.task.ToDo;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {

    @Test
    public void add_multipleTasks_sizeIncreases() {
        TaskList list = new TaskList();
        list.add(new ToDo("a", false));
        list.add(new ToDo("b", false));
        list.add(new ToDo("c", false));
        assertEquals(3, list.size());
    }

    @Test
    public void mark_changesStatus() {
        TaskList list = new TaskList();
        list.add(new ToDo("a", false));
        list.mark(0);
        assertEquals("X", list.get(0).getStatusIcon());
    }

    @Test
    public void unmark_revertsStatus() {
        TaskList list = new TaskList();
        list.add(new ToDo("a", true));
        list.unmark(0);
        assertEquals(" ", list.get(0).getStatusIcon());
    }
}