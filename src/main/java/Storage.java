import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

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
            throw new PeppyFileException("PeppyFileException: Could not create the file... T^T");
        }
    }

    private void wipeFile() throws PeppyFileException {
        try {
            FileWriter fw = new FileWriter(file, false);
            fw.close();
        } catch (IOException e) {
            throw new PeppyFileException("PeppyFileException: Could not write to file... T^T");
        }
    }

    public ArrayList<Task> loadData() throws PeppyException {
        ArrayList<Task> list = new ArrayList<>();
        try {
            if (!file.exists())
                createFile();
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String[] lineSplit = scanner.nextLine().split("\\|");
                Task task = getTask(lineSplit);
                list.add(task);
            }
            return list;
        } catch (FileNotFoundException e) {
            Peppy.print("ERROR!!! The data file was not found...");
        } catch (PeppyException e) {
            Peppy.print(e.getMessage());
        }
        wipeFile();
        return new ArrayList<>();

    }

    private static Task getTask(String[] lineSplit) throws PeppyFileException, PeppyInvalidCommandException {
        try {
            Task task = switch (lineSplit[0].trim()) {
                case "T" -> new Todo(lineSplit[2].trim());
                case "D" -> new Deadline(lineSplit[2].trim(), lineSplit[3].trim());
                case "E" -> new Event(lineSplit[2].trim(), lineSplit[3].trim(), lineSplit[4].trim());
                default -> throw new PeppyFileException("PeppyFileException: Unknown task in data file...");
            };

            if (lineSplit[1].trim().equals("1"))
                task.markDone();
            return task;
        } catch (IndexOutOfBoundsException e) {
            throw new PeppyFileException("PeppyFileException: The data file was corrupted...");
        }
    }

    public void saveData(ArrayList<Task> list) throws PeppyFileException {
        try {
            FileWriter fw = new FileWriter(file, false);
            for (Task task : list)
                fw.write(task.toDataString() + "\n");
            fw.close();
        } catch (IOException e) {
            throw new PeppyFileException("PeppyFileException: Could not write to file... T^T");
        }
    }
}
