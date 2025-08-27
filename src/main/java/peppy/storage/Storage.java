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

public class Storage {
    private final File file;

    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    private void createFile() throws PeppyFileException {
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            throw new PeppyFileException("Could not create the file... T^T");
        }
    }

    private void wipeFile() throws PeppyFileException {
        try {
            FileWriter fw = new FileWriter(file, false);
            fw.close();
        } catch (IOException e) {
            throw new PeppyFileException("Could not write to file... T^T");
        }
    }

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
