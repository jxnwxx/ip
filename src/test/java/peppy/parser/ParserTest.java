package peppy.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    public void parseInput_validInput_success() throws Exception {
        assertEquals(new Command(Action.BYE), Parser.parseInput("bye"));
        assertEquals(new Command(Action.MARK, "1"), Parser.parseInput("mark 1"));
        assertEquals(new Command(Action.UNMARK, "2"), Parser.parseInput("unmark 2"));
        assertEquals(new Command(Action.TODO, "read book"), Parser.parseInput("todo read book"));
        assertEquals(new Command(Action.EVENT, "a ", "from 19-09-2025 1930 ", "to 19-10-2025 1930"),
                Parser.parseInput("event a /from 19-09-2025 1930 /to 19-10-2025 1930"));
        assertEquals(new Command(Action.DELETE, "1"), Parser.parseInput("delete 1"));
    }

    @Test
    public void parseInput_invalidInput_exception() {
        try {
            assertEquals(0, Parser.parseInput("invalidCommand"));
            fail();
        } catch (Exception e) {
            assertEquals("UnknownCommand: I do not know this command... T^T", e.getMessage());
        }
    }

    @Test
    public void parseToInt_validInput_success() throws Exception {
        assertEquals(1, Parser.parseToInt("1"));
        assertEquals(100, Parser.parseToInt("100"));
    }

    @Test
    public void parseToInt_invalidInput_exception() {
        try {
            assertEquals(0, Parser.parseToInt("a"));
            fail();
        } catch (Exception e) {
            assertEquals("InvalidCommand: Index provided is not an integer!", e.getMessage());
        }
    }
}
