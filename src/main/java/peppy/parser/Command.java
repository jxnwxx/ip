package peppy.parser;

import java.util.Arrays;

import peppy.exception.PeppyEditException;
import peppy.exception.PeppyException;
import peppy.exception.PeppyInvalidCommandException;
import peppy.exception.PeppyUnknownCommandException;
import peppy.storage.Storage;
import peppy.task.Deadline;
import peppy.task.Event;
import peppy.task.Task;
import peppy.task.TaskList;
import peppy.task.Todo;

/**
 * Represents the commands that Peppy can execute.
 */
public class Command {
    private static final String SHOW_HELP = "__SHOW_HELP__";
    private final Action action;
    private final String[] args;

    /**
     * Constructs a Command object containing the action to be executed
     * and the arguments of the action.
     *
     * @param action The action to be executed.
     * @param args   The arguments of the action.
     */
    public Command(Action action, String... args) {
        this.action = action;
        this.args = args;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Command)) {
            return false;
        }

        Command other = (Command) obj;
        return this.action == other.action
                && Arrays.equals(this.args, other.args);
    }

    private Action getAction() {
        return action;
    }

    private String[] getArgs() {
        return args;
    }

    /**
     * Marks or unmarks a task in the TaskList based on the index provided.
     *
     * @param tasks  TaskList object containing all the tasks.
     * @param index  Index of task in TaskList to be marked.
     * @param action Action enum to determine to mark or unmark the task.
     * @throws PeppyEditException If task to be mark or unmark is already marked or unmarked respectively.
     */
    public String markTask(TaskList tasks, Integer index, Action action) throws PeppyEditException {
        if (index > tasks.getSize() || index <= 0) {
            throw new PeppyEditException("Index out of range!");
        }

        Task task = tasks.getTask(index - 1);
        if (action == Action.MARK) {
            return markDone(task);
        } else {
            return markUndone(task);
        }
    }

    private static String markUndone(Task task) throws PeppyEditException {
        if (!task.markUndone()) {
            throw new PeppyEditException("Task already marked as undone!");
        }

        return "Nice! I've marked this task as not done yet:\n"
                + String.format("  %s", task);
    }

    private static String markDone(Task task) throws PeppyEditException {
        if (!task.markDone()) {
            throw new PeppyEditException("Task already marked as done!");
        }

        return "Nice! I've marked this task as done:\n"
                + String.format("   %s", task);
    }

    /**
     * Executes the command based on the action and arguments provided.
     *
     * @param tasks   TaskList object to fetch task from.
     * @param storage Storage object to update any changes.
     * @return String to print on GUI.
     */
    public String execute(TaskList tasks, Storage storage) {
        String result;
        try {
            String[] argsList = getArgs();

            switch (getAction()) {
            case BYE:
                result = "Bye bye! See you next time!";
                break;
            case LIST:
                result = tasks.toString();
                break;
            case MARK:
            case UNMARK:
                result = handleMark(tasks, argsList);
                break;
            case TODO:
                result = handleTodo(tasks, argsList);
                break;
            case DEADLINE:
                result = handleDeadline(tasks, argsList);
                break;
            case EVENT:
                result = handleEvent(tasks, argsList);
                break;
            case DELETE:
                result = handleDelete(tasks, argsList);
                break;
            case FIND:
                result = handleFind(tasks, argsList);
                break;
            case HELP:
                result = SHOW_HELP;
                break;
            default:
                throw new PeppyUnknownCommandException("I do not know this command... T^T");
            }
            storage.saveData(tasks);
        } catch (PeppyException e) {
            return e.getMessage();
        }
        return result;
    }

    private static String handleFind(TaskList tasks, String[] argsList) throws PeppyInvalidCommandException {
        if (argsList.length != 1) {
            throw new PeppyInvalidCommandException("find arguments incorrect!\n"
                    + "\t Usage: find <keyword>");
        }

        return tasks.findTask(argsList[0]);
    }

    private static String handleDelete(TaskList tasks, String[] argsList)
            throws PeppyInvalidCommandException, PeppyEditException {
        if (argsList.length != 1) {
            throw new PeppyInvalidCommandException("delete arguments incorrect!\n"
                    + "\t Usage: delete <index>");
        }

        Integer index = Parser.parseToInt(argsList[0]);
        return tasks.deleteTask(index);
    }

    private static String handleEvent(TaskList tasks, String[] argsList) throws PeppyInvalidCommandException {
        if (argsList.length != 3
                || !argsList[1].startsWith("from ")
                || !argsList[2].startsWith("to ")) {
            throw new PeppyInvalidCommandException("event arguments incorrect!\n"
                    + "\t Usage: event <description> /from <start_date> /to <end_date>");
        }

        String description = argsList[0].trim();
        String from = argsList[1].replace("from ", "").trim();
        String to = argsList[2].replace("to ", "").trim();

        if (description.isEmpty() && from.isBlank() && to.isBlank()) {
            throw new PeppyInvalidCommandException("event arguments incorrect!\n"
                    + "\t Usage: event <description> /from <start_date> /to <end_date>");
        }

        Event event = new Event(description, from, to);
        return tasks.addTask(event, true);
    }

    private static String handleDeadline(TaskList tasks, String[] argsList) throws PeppyInvalidCommandException {
        if (argsList.length != 2 || !argsList[1].startsWith("by ")) {
            throw new PeppyInvalidCommandException("deadline arguments incorrect!\n"
                    + "\t Usage: deadline <description> /by <due>");

        }

        String description = argsList[0].trim();
        String by = argsList[1].replace("by ", "").trim();

        if (description.isEmpty() && by.isEmpty()) {
            throw new PeppyInvalidCommandException("deadline arguments incorrect!\n"
                    + "\t Usage: deadline <description> /by <due>");

        }

        Deadline deadline = new Deadline(description, by);
        return tasks.addTask(deadline, true);
    }

    private static String handleTodo(TaskList tasks, String[] argsList) throws PeppyInvalidCommandException {
        if (argsList.length != 1) {
            throw new PeppyInvalidCommandException("todo arguments incorrect!\n"
                    + "\t Usage: todo <description>");
        }

        Todo todo = new Todo(argsList[0]);
        return tasks.addTask(todo, true);
    }

    private String handleMark(TaskList tasks, String[] argsList)
            throws PeppyInvalidCommandException, PeppyEditException {
        if (argsList.length != 1) {
            throw new PeppyInvalidCommandException("mark arguments incorrect!\n"
                    + "\t Usage: mark <index>");

        }

        Integer index = Parser.parseToInt(argsList[0]);
        return markTask(tasks, index, getAction());

    }
}
