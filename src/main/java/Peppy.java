import java.util.Scanner;
import java.util.ArrayList;

public class Peppy {
    private static final String FILE_PATH = "data/peppy.txt";

    public static void print(String... string) {
        String horizontalLine = "____________________________________________________________";
        System.out.println("\t" + horizontalLine);
        for (String str : string) {
            System.out.println("\t " + str);
        }
        System.out.println("\t" + horizontalLine);
    }

    public static Command parseInput(String input) throws PeppyUnknownCommandException {
        try {
            String[] inputSplit = input.split(" ", 2);
            Action action = Action.valueOf(inputSplit[0].toUpperCase());
            String[] argsList = {};
            if (inputSplit.length == 2)
                argsList = inputSplit[1].split("/");
            return new Command(action, argsList);
        } catch (IllegalArgumentException e) {
            throw new PeppyUnknownCommandException("Error: I do not know this command... T^T");
        }
    }

    public static void addTask(TaskList tasks, Task task) throws PeppyFileException {
        tasks.addTask(task);
        print("Got it. I've added this task:",
                String.format("  %s", task),
                String.format("Now you have %d task%s in the list.",
                        tasks.getSize(),
                        (tasks.getSize() > 1) ? "s" : ""));
    }

    public static void deleteTask(TaskList tasks, Integer index) throws PeppyEditException {
        if (index <= tasks.getSize() && index > 0) {
            Task task = tasks.getTask(index - 1);
            tasks.deleteTask(index - 1);
            print("Noted. I've removed this task:",
                    String.format("  %s", task),
                    String.format("Now you have %d task%s in the list.",
                            tasks.getSize(),
                            (tasks.getSize() > 1) ? "s" : ""));
        } else {
            throw new PeppyEditException("Error: Index out of range!");
        }
    }

    public static void markDone(TaskList tasks, Integer index) throws PeppyEditException {
        if (index <= tasks.getSize() && index > 0) {
            Task task = tasks.getTask(index - 1);
            if (task.markDone())
                print("Nice! I've marked this task as done:",
                        String.format("   %s", task));
            else
                throw new PeppyEditException("Error: Task already marked as done!");
        } else {
            throw new PeppyEditException("Error: Index out of range!");
        }
    }

    public static void markUndone(TaskList tasks, Integer index) throws PeppyEditException {
        if (index <= tasks.getSize() && index > 0) {
            Task task = tasks.getTask(index - 1);
            if (task.markUndone())
                print("Nice! I've marked this task as not done yet:",
                        String.format("  %s", task));
            else
                throw new PeppyEditException("Error: Task already marked as undone!");
        } else {
            throw new PeppyEditException("Error: Index out of range!");
        }
    }

    public static void main(String[] args) throws PeppyException {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage(FILE_PATH);
        TaskList tasks = storage.loadData();

        print("Hello! I'm Peppy", "What can I do for you?");

        while (flag) {
            try {
                String input = scanner.nextLine()
                        .replaceAll("\\|", "");
                if (input.isBlank())
                    continue;
                Command cmd = parseInput(input);
                String[] argsList = cmd.getArgs();

                switch (cmd.getAction()) {
                case BYE:
                    flag = false;
                    break;
                case LIST:
                    print(tasks.toString());
                    break;
                case MARK:
                case UNMARK:
                    try {
                        if (argsList.length == 1) {
                            Integer index = Integer.parseInt(argsList[0]);
                            if (cmd.getAction() == Action.MARK)
                                markDone(tasks, index);
                            else
                                markUndone(tasks, index);
                        } else {
                            throw new PeppyInvalidCommandException("Error: mark arguments incorrect!\n"
                                    + "\t Usage: mark <index>");
                        }
                    } catch (NumberFormatException e) {
                        throw new PeppyInvalidCommandException("Error: Index provided is not valid!\n"
                                + "\t Usage: mark <index>");
                    }
                    break;
                case TODO:
                    try {
                        Todo todo = new Todo(argsList[0]);
                        addTask(tasks, todo);
                    } catch (IndexOutOfBoundsException e) {
                        throw new PeppyInvalidCommandException("Error: todo arguments incorrect!\n"
                                + "\t Usage: todo <description>");
                    }
                    break;
                case DEADLINE:
                    if (argsList.length == 2 && argsList[1].startsWith("by ")) {
                        String description = argsList[0].trim();
                        String by = argsList[1].replace("by ", "").trim();

                        if (!description.isEmpty() && !by.isEmpty()) {
                            Deadline deadline = new Deadline(description, by);
                            addTask(tasks, deadline);
                        } else {
                            throw new PeppyInvalidCommandException("Error: deadline arguments incorrect!\n"
                                    + "\t Usage: deadline <description> /by <due>");
                        }
                    } else {
                        throw new PeppyInvalidCommandException("Error: deadline arguments incorrect!\n"
                                + "\t Usage: deadline <description> /by <due>");
                    }
                    break;
                case EVENT:
                    if (argsList.length == 3
                            && argsList[1].startsWith("from ")
                            && argsList[2].startsWith("to ")) {
                        String description = argsList[0].trim();
                        String from = argsList[1].replace("from ", "").trim();
                        String to = argsList[2].replace("to ", "").trim();

                        if (!description.isEmpty() && !from.isEmpty() && !to.isEmpty()) {
                            Event event = new Event(description, from, to);
                            addTask(tasks, event);
                        } else {
                            throw new PeppyInvalidCommandException("Error: event arguments incorrect!\n"
                                    + "\t Usage: event <description> /from <start_date> /to <end_date>");
                        }
                    } else {
                        throw new PeppyInvalidCommandException("Error: event arguments incorrect!\n"
                                + "\t Usage: event <description> /from <start_date> /to <end_date>");
                    }
                    break;
                case DELETE:
                    try {
                        if (argsList.length == 1) {
                            Integer index = Integer.parseInt(argsList[0]);
                            deleteTask(tasks, index);
                        } else {
                            throw new PeppyInvalidCommandException("Error: delete arguments incorrect!\n"
                                    + "\t Usage: delete <index>");
                        }
                    } catch (NumberFormatException e) {
                        throw new PeppyInvalidCommandException("Error: Index provided is not valid!\n"
                                + "\t Usage: delete <index>");
                    }
                    break;
                }
                storage.saveData(tasks);
            } catch (PeppyException e) {
                print(e.getMessage());
            }
        }
        print("Bye. Hope to see you again soon!");
    }
}
