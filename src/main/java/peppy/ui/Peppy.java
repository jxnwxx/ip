package peppy.ui;

import peppy.exception.PeppyException;
import peppy.parser.Command;
import peppy.parser.Parser;
import peppy.storage.Storage;
import peppy.task.TaskList;

public class Peppy {
    private static final String DEFAULT_FILE_PATH = "data/peppy.txt";
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

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

    public static void main(String[] args) {
        new Peppy(DEFAULT_FILE_PATH).run();
    }
}
