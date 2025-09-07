package peppy.parser;

import peppy.exception.PeppyFileException;
import peppy.exception.PeppyInvalidCommandException;
import peppy.exception.PeppyUnknownCommandException;
import peppy.task.Deadline;
import peppy.task.Event;
import peppy.task.Task;
import peppy.task.Todo;

/**
 * Parses user inputs into standard format for other methods.
 */
public class Parser {
    /**
     * Returns a Command object based on the user input.
     * If user input is not a valid action, throw PeppyUnknownCommandException
     *
     * @param input User input of action and the arguments.
     * @return Command object containing the user input action and arguments.
     * @throws PeppyUnknownCommandException If user input is not valid.
     */
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

    /**
     * Returns an Integer from a user input String.
     *
     * @param input User input string.
     * @return Integer of the user input.
     * @throws PeppyInvalidCommandException If the user input is not a number.
     */
    public static Integer parseToInt(String input) throws PeppyInvalidCommandException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new PeppyInvalidCommandException("Index provided is not an integer!");
        }
    }

    /**
     * Returns a task based on the provided String array.
     *
     * @param lineSplit String array to be used to create Task object.
     * @return Task object based on lineSplit contents.
     * @throws PeppyFileException           If lineSplit contains invalid case to create Task.
     * @throws PeppyInvalidCommandException If lineSplit is shorter than the Task it is trying to construct.
     */
    public static Task parseToTask(String[] lineSplit) throws PeppyFileException, PeppyInvalidCommandException {
        assert lineSplit != null;
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
