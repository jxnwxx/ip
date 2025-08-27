package peppy.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import peppy.exception.PeppyException;
import peppy.exception.PeppyFileException;
import peppy.parser.Parser;
import peppy.task.Task;
import peppy.task.TaskList;
import peppy.ui.Ui;

/**
 * Provides persistent storage for Peppy application data.
 * This class keeps a handle to the data file and contains operations to create the file if missing, read the file's
 * content
 */
public class Storage {
    private final File file;

    /**
     * Constructs a Storage object with a file handle to the specified file path.
     *
     * @param filePath The file path to the data file.
     */
    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Creates the file and its parent directories.
     *
     * @throws PeppyFileException If the file could not be created.
     */
    private void createFile() throws PeppyFileException {
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            throw new PeppyFileException("Could not create the file... T^T");
        }
    }

    /**
     * Wipes the file's content.
     *
     * @throws PeppyFileException If the file could not be wiped.
     */
    private void wipeFile() throws PeppyFileException {
        try {
            FileWriter fw = new FileWriter(file, false);
            fw.close();
        } catch (IOException e) {
            throw new PeppyFileException("Could not wipe file... T^T");
        }
    }

    /**
     * Loads data from the file and populates TaskList.
     *
     * @param ui Ui object to print any errors.
     * @return TaskList object containing tasks from the file.
     * @throws PeppyException If data file is corrupted or not the correct format.
     */
    public TaskList loadData(Ui ui) throws PeppyException {
        TaskList tasks = new TaskList();
        try {
            if (!file.exists()) {
                createFile();
            }
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String[] lineSplit = scanner.nextLine().split("\\|");
                Task task = Parser.parseToTask(lineSplit);
                tasks.addTask(task, ui, false);
            }
            scanner.close();
            return tasks;
        } catch (FileNotFoundException e) {
            ui.printString("ERROR!!! The data file was not found...");
        } catch (PeppyException e) {
            ui.printString(e.getMessage());
        }
        wipeFile();
        return new TaskList();

    }

    /**
     * Writes all task in TaskList to the file.
     *
     * @param tasks TaskList object containing tasks to be written.
     * @throws PeppyFileException If the file could not be written to.
     */
    public void saveData(TaskList tasks) throws PeppyFileException {
        try {
            FileWriter fw = new FileWriter(file, false);
            for (int i = 0; i < tasks.getSize(); i++) {
                fw.write(tasks.getTask(i).toDataString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new PeppyFileException("Could not write to file... T^T");
        }
    }
}
