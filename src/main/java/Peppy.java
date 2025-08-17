import java.util.Scanner;
import java.util.ArrayList;

public class Peppy {


    public static void print(String... string) {
        String horizontalLine = "____________________________________________________________";
        System.out.println("\t" + horizontalLine);
        for (String str : string) {
            System.out.println("\t " + str);
        }
        System.out.println("\t" + horizontalLine);
    }

    public static void addTask(ArrayList<Task> list, Task task) {
        list.add(task);
        print("Got it. I've added this task:",
                String.format("  %s", task),
                String.format("Now you have %d task%s in the list.",
                        list.size(),
                        (list.size() > 1) ? "s" : ""));
    }

    public static void printList(ArrayList<Task> list) {
        if (list.isEmpty()) {
            print("There's nothing in the list!");
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                result.append(String.format("%d. %s \n\t ",
                        i + 1,
                        list.get(i).toString()));
            }
            print("Here are the tasks in your list:",
                    result.toString().stripTrailing());
        }
    }

    public static void markDone(ArrayList<Task> list, Integer index) {
        if (index > list.size()) {
            print("Index provided is out of range!");
        } else {
            Task task = list.get(index - 1);
            if (task.markDone())
                print("Nice! I've marked this task as done:",
                        String.format("   %s", task));
            else
                print("Task already marked as done!");
        }
    }

    public static void markUndone(ArrayList<Task> list, Integer index) {
        if (index > list.size()) {
            print("Index provided is out of range!");
        } else {
            Task task = list.get(index - 1);
            if (task.markUndone())
                print("Nice! I've marked this task as not done yet:",
                        String.format("  %s", task));
            else
                print("Task already marked as undone!");
        }
    }

    public static void main(String[] args) {
        ArrayList<Task> list = new ArrayList<>();

        print("Hello! I'm Peppy",
                "What can I do for you?");

        Scanner scanner = new Scanner(System.in);

        boolean flag = true;
        while (flag) {
            String input = scanner.nextLine();
            String cmd = input.split("\\s+")[0];

            switch (cmd) {
                case "bye":
                    flag = false;
                    break;
                case "list":
                    printList(list);
                    break;
                case "mark":
                case "unmark":
                    try {
                        Integer index = Integer.parseInt(input.substring(cmd.length() + 1));
                        if (cmd.equals("mark"))
                            markDone(list, index);
                        else
                            markUndone(list, index);
                    } catch (NumberFormatException e) {
                        print("Index provided is not a number!");
                    } catch (IndexOutOfBoundsException e) {
                        print("Missing index argument!");
                    }
                    break;
                case "todo":
                    Todo todo = new Todo(input.substring(cmd.length() + 1));
                    addTask(list, todo);
                    break;
                case "deadline":
                    try {
                        String[] split = input.substring(cmd.length() + 1)
                                .split("/by ");
                        Deadline deadline = new Deadline(split[0].trim(),
                                split[1].trim());
                        addTask(list, deadline);
                    } catch (IndexOutOfBoundsException e) {
                        print("Invalid \"/by\" argument!");
                    }
                    break;
                case "event":
                    try {
                        String[] split = input.substring(cmd.length() + 1)
                                .split("/from ");
                        String[] secondSplit = split[1].split("/to ");
                        Event event = new Event(split[0].trim(),
                                secondSplit[0].trim(),
                                secondSplit[1].trim());
                        addTask(list, event);
                    } catch (IndexOutOfBoundsException e) {
                        print("Invalid \"/from\" or \"/to\" argument!");
                    }
                    break;
                default:
                    print(input);
            }
        }

        print("Bye. Hope to see you again soon!");
    }
}
