package aegis;

import org.junit.jupiter.api.Test;
import aegis.task.Deadline;
import aegis.task.Event;
import aegis.task.Task;
import aegis.task.ToDo;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void parseTodo_validDescription_returnsToDo() throws AegisException {
        Command command = new Parser().parse("todo borrow book");
        assertEquals("todo", command.getCommandName()); // need getter
        Task task = command.getTask();
        assertInstanceOf(ToDo.class, task);
        assertEquals("borrow book", task.toString());
    }

    @Test
    public void parseTodo_emptyDescription_throwsAegisException() {
        AegisException e = assertThrows(AegisException.class, () -> new Parser().parse("todo   "));
        assertEquals("The description of a todo cannot be empty.", e.getMessage());
    }

    @Test
    public void parseDeadline_validInput_returnsDeadline() throws AegisException {
        Command command = new Parser().parse("deadline return book /by 2026-08-22");
        Task task = command.getTask();
        assertInstanceOf(Deadline.class, task);
        assertTrue(task.toString().contains("return book"));
    }

    @Test
    public void parseDeadline_invalidDate_throwsAegisException() {
        AegisException e = assertThrows(AegisException.class,
                () -> new Parser().parse("deadline return book /by 22-08-2026"));
        assertEquals("Dates must be in YYYY-MM-DD format.", e.getMessage());
    }

    @Test
    public void parseEvent_validInput_returnsEvent() throws AegisException {
        Command command = new Parser().parse("event meeting /from 2026-08-22 /to 2026-08-23");
        Task task = command.getTask();
        assertInstanceOf(Event.class, task);
        assertTrue(task.toString().contains("meeting"));
    }

    @Test
    public void parseEvent_invalidDate_throwsAegisException() {
        AegisException e = assertThrows(AegisException.class,
                () -> new Parser().parse("event meeting /from 22-08-2026 /to 23-08-2026"));
        assertEquals("Dates must be in YYYY-MM-DD format.", e.getMessage());
    }

}