package peppy.task;

import java.util.ArrayList;

import peppy.exception.PeppyEditException;
import peppy.ui.Ui;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public void addTask(Task task, Ui ui, boolean shouldPrint) {
        tasks.add(task);
        if (shouldPrint) {
            ui.printString("Got it. I've added this task:",
                    String.format("  %s", task),
                    String.format("Now you have %d task%s in the list.",
                            tasks.size(),
                            tasks.size() > 1 ? "s" : ""));
        }
    }

    public void deleteTask(int index, Ui ui) throws PeppyEditException {
        if (index <= tasks.size() && index > 0) {
            Task task = getTask(index - 1);
            tasks.remove(index - 1);
            ui.printString("Noted. I've removed this task:",
                    String.format("  %s", task),
                    String.format("Now you have %d task%s in the list.",
                            tasks.size(),
                            tasks.size() > 1 ? "s" : ""));
        } else {
            throw new PeppyEditException("Error: Index out of range!");
        }
    }

    public void findTask(String input, Ui ui) {
        StringBuilder result = new StringBuilder();
        int found = 0;

        for (int i = 0; i < getSize(); i++) {
            Task task = getTask(i);
            if (task.contains(input)) {
                found++;
                result.append(String.format("%d. %s \n\t ",
                        found,
                        tasks.get(i).toString()));
            }
        }
        if (found > 0) {
            ui.printString("Here are the matching tasks in your list:\n\t "
                    + result.toString().stripTrailing());
        } else {
            ui.printString("No tasks found with the given keyword.");
        }
    }

    public int getSize() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "There's nothing in the list! Try adding some tasks!";
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < tasks.size(); i++) {
                result.append(String.format("%d. %s \n\t ",
                        i + 1,
                        tasks.get(i).toString()));
            }
            return "Here are the tasks in your list:\n\t "
                    + result.toString().stripTrailing();
        }
    }
}
