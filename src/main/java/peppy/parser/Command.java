package peppy.parser;

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
import peppy.ui.Ui;

public class Command {
    private final Action action;
    private final String[] args;
    private boolean isExit;

    public Command(Action action, String... args) {
        this.action = action;
        this.args = args;
        this.isExit = false;
    }

    private Action getAction() {
        return action;
    }

    private String[] getArgs() {
        return args;
    }

    public boolean isExit() {
        return isExit;
    }

    public void markTask(TaskList tasks, Integer index, Ui ui, Action action) throws PeppyEditException {
        if (index <= tasks.getSize() && index > 0) {
            Task task = tasks.getTask(index - 1);
            if (action == Action.MARK) {
                if (task.markDone()) {
                    ui.printString("Nice! I've marked this task as done:",
                            String.format("   %s", task));
                } else {
                    throw new PeppyEditException("Task already marked as done!");
                }
            } else {
                if (task.markUndone()) {
                    ui.printString("Nice! I've marked this task as not done yet:",
                            String.format("  %s", task));
                } else {
                    throw new PeppyEditException("Task already marked as undone!");
                }
            }
        } else {
            throw new PeppyEditException("Index out of range!");
        }
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            String[] argsList = getArgs();

            switch (getAction()) {
            case BYE:
                isExit = true;
                ui.printBye();
                break;
            case LIST:
                ui.printTasks(tasks);
                break;
            case MARK:
            case UNMARK:
                if (argsList.length == 1) {
                    Integer index = Parser.parseToInt(argsList[0]);
                    markTask(tasks, index, ui, getAction());
                } else {
                    throw new PeppyInvalidCommandException("mark arguments incorrect!\n"
                            + "\t Usage: mark <index>");
                }
                break;
            case TODO:
                if (argsList.length == 1) {
                    Todo todo = new Todo(argsList[0]);
                    tasks.addTask(todo, ui, true);
                } else {
                    throw new PeppyInvalidCommandException("todo arguments incorrect!\n"
                            + "\t Usage: todo <description>");
                }
                break;
            case DEADLINE:
                if (argsList.length == 2 && argsList[1].startsWith("by ")) {
                    String description = argsList[0].trim();
                    String by = argsList[1].replace("by ", "").trim();

                    if (!description.isEmpty() && !by.isEmpty()) {
                        Deadline deadline = new Deadline(description, by);
                        tasks.addTask(deadline, ui, true);
                    } else {
                        throw new PeppyInvalidCommandException("deadline arguments incorrect!\n"
                                + "\t Usage: deadline <description> /by <due>");
                    }
                } else {
                    throw new PeppyInvalidCommandException("deadline arguments incorrect!\n"
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
                        tasks.addTask(event, ui, true);
                    } else {
                        throw new PeppyInvalidCommandException("event arguments incorrect!\n"
                                + "\t Usage: event <description> /from <start_date> /to <end_date>");
                    }
                } else {
                    throw new PeppyInvalidCommandException("event arguments incorrect!\n"
                            + "\t Usage: event <description> /from <start_date> /to <end_date>");
                }
                break;
            case DELETE:
                if (argsList.length == 1) {
                    Integer index = Parser.parseToInt(argsList[0]);
                    tasks.deleteTask(index, ui);
                } else {
                    throw new PeppyInvalidCommandException("delete arguments incorrect!\n"
                            + "\t Usage: delete <index>");
                }
                break;
            default:
                throw new PeppyUnknownCommandException("I do not know this command... T^T");
            }
            storage.saveData(tasks);
        } catch (PeppyException e) {
            ui.printError(e.getMessage());
        }
    }
}
