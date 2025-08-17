import java.util.Scanner;
import java.util.ArrayList;

public class Peppy {
    public static void print(String string) {
        String horizontalLine = "____________________________________________________________";
        System.out.println("\t" + horizontalLine);
        System.out.println("\t " + string);
        System.out.println("\t" + horizontalLine);
    }

    public static void printList(ArrayList<Task> list) {
        if (list.isEmpty()) {
            print("there's nothing in the list!");
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                result.append(String.format("%d. %s \n\t ",
                        i + 1,
                        list.get(i).toString()));
            }
            print(result.toString().stripTrailing());
        }
    }

    public static void markDone(ArrayList<Task> list, Integer index) {
        if (index > list.size()) {
            print("index provided is out of range!");
        } else {
            Task task = list.get(index - 1);
            task.markDone();
            print(String.format("Nice! I've marked this task as done:\n\t  %s",
                    task.toString()));
        }
    }

    public static void markUndone(ArrayList<Task> list, Integer index) {
        if (index > list.size()) {
            print("index provided is out of range!");
        } else {
            Task task = list.get(index - 1);
            task.markUndone();
            print(String.format("Nice! I've marked this task as not done yet:\n\t  %s",
                    task.toString()));
        }
    }

    public static void main(String[] args) {
        ArrayList<Task> list = new ArrayList<Task>();

        String welcomeString = "Hello! I'm Peppy\n\t What can I do for you?";
        print(welcomeString);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                printList(list);
            } else if (input.startsWith("mark")) {
                try {
                    Integer index = Integer.parseInt(input.substring(5));
                    markDone(list, index);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    print("error in marking tasks!");
                }
            } else if (input.startsWith("unmark")) {
                try {
                    Integer index = Integer.parseInt(input.substring(7));
                    markUndone(list, index);
                } catch (NumberFormatException | IndexOutOfBoundsException e) {
                    print("error in unmarking tasks!");
                }
            } else {
                Task task = new Task(input);
                list.add(task);
                print("added: " + input);
            }
        }

        print("Bye. Hope to see you again soon!");
    }
}
