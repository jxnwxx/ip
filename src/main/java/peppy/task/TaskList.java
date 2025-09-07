package peppy.task;

import java.util.ArrayList;

import peppy.exception.PeppyEditException;

/**
 * Stores Task objects within an ArrayList and has operations to add, delete and retrieve.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs a TaskList to store Task objects.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Adds the specified Task object into the list.
     *
     * @param task        Task object to be added to the list.
     * @param shouldPrint Should the Ui print that it added the task.
     * @return String to print.
     */
    public String addTask(Task task, boolean shouldPrint) {
        tasks.add(task);

        if (!shouldPrint) {
            return "";
        }

        return "Got it. I've added this task:\n"
                + String.format("%s\n", task)
                + String.format("Now you have %d task%s in the list.\n",
                tasks.size(),
                tasks.size() > 1 ? "s" : "");
    }

    /**
     * Deletes the specified index Task object from the list
     *
     * @param index Index of Task object in the list to be deleted.
     * @return String to print.
     * @throws PeppyEditException If index is out of bounds.
     */
    public String deleteTask(int index) throws PeppyEditException {
        if (index > tasks.size() || index <= 0) {
            throw new PeppyEditException("Error: Index out of range!");
        }

        Task task = getTask(index - 1);
        tasks.remove(index - 1);
        return "Got it. I've deleted this task:\n"
                + String.format("%s\n", task)
                + String.format("Now you have %d task%s in the list.\n",
                tasks.size(),
                tasks.size() > 1 ? "s" : "");
    }

    /**
     * Finds tasks that contains the keywords within the TaskList.
     *
     * @param input Keyword to search for.
     * @return String to print.
     */
    public String findTask(String input) {
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
            return "Here are the matching tasks in your list:\n\t "
                    + result.toString().stripTrailing();
        } else {
            return "No tasks found with the given keyword.";
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
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < tasks.size(); i++) {
            result.append(String.format("%d. %s \n",
                    i + 1,
                    tasks.get(i).toString()));
        }

        return "Here are the tasks in your list:\n"
                + result.toString().stripTrailing();
    }
}
