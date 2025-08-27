package peppy.ui;

import peppy.exception.PeppyException;
import peppy.parser.Command;
import peppy.parser.Parser;
import peppy.storage.Storage;
import peppy.task.TaskList;

/**
 * Serves as the entry point for Peppy application.
 */
public class Peppy {
    private static final String DEFAULT_FILE_PATH = "data/peppy.txt";
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Constructs a new Peppy object containing Ui, Storage and Tasklist.
     *
     * @param filePath The file path to the Storage data file.
     */
    public Peppy(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = storage.loadData(ui);
        } catch (PeppyException e) {
            ui.printError(e.getMessage());
            this.tasks = new TaskList();
        }
    }

    /**
     * Runs the Peppy application.
     */
    public void run() {
        ui.printWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String input = ui.readCommand();
                Command cmd = Parser.parseInput(input);
                cmd.execute(tasks, ui, storage);
                isExit = cmd.isExit();
            } catch (PeppyException e) {
                ui.printError(e.getMessage());
            }
        }
    }

    /**
     * The main entry point for the application.
     *
     * @param args Command-line arguments passed to the program.
     *             These arguments are ignored in this program.
     */
    public static void main(String[] args) {
        new Peppy(DEFAULT_FILE_PATH).run();
    }
}
