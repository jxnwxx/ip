package peppy.parser;

import peppy.exception.PeppyFileException;
import peppy.exception.PeppyInvalidCommandException;
import peppy.exception.PeppyUnknownCommandException;
import peppy.task.Deadline;
import peppy.task.Event;
import peppy.task.Task;
import peppy.task.Todo;

public class Parser {
    public static Command parseInput(String input) throws PeppyUnknownCommandException {
        try {
            String[] inputSplit = input.split(" ", 2);
            Action action = Action.valueOf(inputSplit[0].toUpperCase());
            String[] argsList = {};
            if (inputSplit.length == 2) {
                argsList = inputSplit[1].split("/");
            }
            return new Command(action, argsList);
        } catch (IllegalArgumentException e) {
            throw new PeppyUnknownCommandException("I do not know this command... T^T");
        }
    }

    public static Integer parseToInt(String input) throws PeppyInvalidCommandException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new PeppyInvalidCommandException("Index provided is not an integer!");
        }
    }

    public static Task parseToTask(String[] lineSplit) throws PeppyFileException, PeppyInvalidCommandException {
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
}
