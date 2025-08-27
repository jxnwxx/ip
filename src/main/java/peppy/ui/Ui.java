package peppy.ui;

import java.util.Scanner;

import peppy.task.TaskList;

/**
 * Handles printing the user interface.
 */
public class Ui {
    private static final String LINE_SEPARATOR = "____________________________________________________________";
    private final Scanner scanner;

    /**
     * Constructs an Ui object that is responsible for printing the user interface.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints a line separator
     */
    public void printLine() {
        System.out.println("\t" + LINE_SEPARATOR);
    }

    /**
     * Prints the provided strings in a specified format.
     *
     * @param strings Strings to be printed.
     */
    public void printString(String... strings) {
        printLine();
        for (String str : strings) {
            System.out.println("\t " + str);
        }
        printLine();
    }

    public void printWelcome() {
        printString("Hello! I'm Peppy", "What can I do for you?");
    }

    public void printBye() {
        printString("Bye bye! See you next time!");
    }

    public void printTasks(TaskList tasks) {
        printString(tasks.toString());
    }

    public void printError(String errorMessage) {
        printString(errorMessage);
    }

    /**
     * Reads for the next user input.
     *
     * @return User input string.
     */
    public String readCommand() {
        return this.scanner.nextLine()
                .replaceAll("\\|", "");
    }
}
