package peppy.ui;

import peppy.exception.PeppyException;
import peppy.parser.Command;
import peppy.parser.Parser;
import peppy.storage.Storage;
import peppy.task.TaskList;

/**
 * Peppy class containing logic for GUI.
 */
public class Peppy {
    private static final String DEFAULT_FILE_PATH = "data/peppy.txt";
    private final Storage storage;
    private TaskList tasks;

    /**
     * Constructs a new Peppy object
     */
    public Peppy() {
        this.storage = new Storage(DEFAULT_FILE_PATH);
        try {
            this.tasks = storage.loadData();
        } catch (PeppyException e) {
            this.tasks = new TaskList();
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        assert input != null;
        try {
            Command cmd = Parser.parseInput(input);
            return cmd.execute(tasks, storage);
        } catch (PeppyException e) {
            return e.getMessage();
        }
    }
}
