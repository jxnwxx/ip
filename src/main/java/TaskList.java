import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(int index) {
        tasks.remove(index);
    }

    public void markTask(int index) {
        this.getTask(index).markDone();
    }

    public void unmarkTask(int index) {
        this.getTask(index).markUndone();
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
