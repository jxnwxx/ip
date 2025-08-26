package peppy.ui;

import java.util.Scanner;

import peppy.exception.PeppyUnknownCommandException;
import peppy.task.TaskList;

public class Ui {
    private static final String LINE_SEPARATOR = "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void printLine() {
        System.out.println("\t" + LINE_SEPARATOR);
    }

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

    public String readCommand() throws PeppyUnknownCommandException {
        return this.scanner.nextLine()
                .replaceAll("\\|", "");
    }
}
