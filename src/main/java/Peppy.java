import java.util.Scanner;
import java.util.ArrayList;

public class Peppy {
    public static void print(String string) {
        String horizontalLine = "____________________________________________________________";
        System.out.println("\t" + horizontalLine);
        System.out.println("\t " + string);
        System.out.println("\t" + horizontalLine);
    }

    public static void printList(ArrayList<String> list) {
        if (list.isEmpty()) {
            print("there's nothing in the list!");
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                result.append(String.format("%d. %s \n\t ", i + 1, list.get(i)));
            }
            print(result.toString().stripTrailing());
        }
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        String welcomeString = "Hello! I'm Peppy\n\t What can I do for you?";
        print(welcomeString);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                printList(list);
            } else {
                list.add(input);
                print("added: " + input);
            }
        }

        print("Bye. Hope to see you again soon!");
    }
}
