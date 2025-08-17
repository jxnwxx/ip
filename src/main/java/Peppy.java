import java.util.Scanner;

public class Peppy {
    public static void print(String string) {
        String horizontalLine = "____________________________________________________________";
        System.out.println("\t" + horizontalLine);
        System.out.println("\t " + string);
        System.out.println("\t" + horizontalLine);
    }

    public static void main(String[] args) {
        String welcomeString = "Hello! I'm Peppy\n\t What can I do for you?";
        print(welcomeString);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye"))
                break;
            print(input);
        }

        print("Bye. Hope to see you again soon!");
    }
}
