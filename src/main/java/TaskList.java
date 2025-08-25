import java.util.ArrayList;

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
                            (tasks.size() > 1) ? "s" : ""));
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
                            (tasks.size() > 1) ? "s" : ""));
        } else {
            throw new PeppyEditException("Error: Index out of range!");
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
