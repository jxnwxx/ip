package peppy.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import peppy.exception.PeppyException;
import peppy.exception.PeppyFileException;
import peppy.exception.PeppyInvalidCommandException;
import peppy.task.Deadline;
import peppy.task.Event;
import peppy.task.Task;
import peppy.task.TaskList;
import peppy.task.Todo;
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
                Task task = getTask(lineSplit);
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

    private static Task getTask(String[] lineSplit) throws PeppyFileException, PeppyInvalidCommandException {
        try {
            Task task = switch (lineSplit[0].trim()) {
            case "T" -> new Todo(lineSplit[2].trim());
            case "D" -> new Deadline(lineSplit[2].trim(), lineSplit[3].trim());
            case "E" -> new Event(lineSplit[2].trim(), lineSplit[3].trim(), lineSplit[4].trim());
            default -> throw new PeppyFileException("Unknown task in data file...");
            };

            if (lineSplit[1].trim().equals("1")) {
                task.markDone();
            }
            return task;
        } catch (IndexOutOfBoundsException e) {
            throw new PeppyFileException("The data file was corrupted...");
        }
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
