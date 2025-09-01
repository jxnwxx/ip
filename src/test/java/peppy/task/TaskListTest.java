package peppy.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskListTest {
    private TaskList tasks;

    @BeforeEach
    public void setup() {
        this.tasks = new TaskList();
    }

    @Test
    public void getSize_emptyList() {
        assertEquals(0, tasks.getSize());
    }

    @Test
    public void getSize_addTaskToList() throws Exception {
        tasks.addTask(new Todo("1"), false);
        assertEquals(1, tasks.getSize());
        tasks.addTask(new Deadline("2", "19-09-2025 1330"), false);
        assertEquals(2, tasks.getSize());
        tasks.addTask(new Event("3", "01-01-2025 1330", "01-01-2025 1930"), false);
        assertEquals(3, tasks.getSize());
    }

    @Test
    public void testStringConversion() throws Exception {
        // empty list string test
        assertEquals("There's nothing in the list! Try adding some tasks!",
                tasks.toString());

        // populated list string test
        tasks.addTask(new Todo("1"), false);
        tasks.addTask(new Deadline("2", "19-09-2025 1330"), false);
        tasks.addTask(new Event("3", "01-01-2025 1330", "01-01-2025 1930"), false);

        assertEquals("Here are the tasks in your list:\n"
                        + "1. [T][ ] 1 \n"
                        + "2. [D][ ] 2 (by: 19/Sep/2025 01:30pm) \n"
                        + "3. [E][ ] 3 (from: 01/Jan/2025 01:30pm to: 01/Jan/2025 07:30pm)",
                tasks.toString());
    }
}
