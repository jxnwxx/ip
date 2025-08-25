package peppy.parser;

import peppy.exception.PeppyInvalidCommandException;
import peppy.exception.PeppyUnknownCommandException;

public class Parser {
    public static Command parseInput(String input) throws PeppyUnknownCommandException {
        try {
            String[] inputSplit = input.split(" ", 2);
            Action action = Action.valueOf(inputSplit[0].toUpperCase());
            String[] argsList = {};
            if (inputSplit.length == 2)
                argsList = inputSplit[1].split("/");
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
}
