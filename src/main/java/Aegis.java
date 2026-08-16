import java.util.Scanner;
public class Aegis {
    public static void main(String[] args) {
        String banner = "    _              _     \n"
                + "   / \\   ___  __ _(_)___ \n"
                + "  / _ \\ / _ \\/ _` | / __|\n"
                + " / ___ \\  __/ (_| | \\__ \\\n"
                + "/_/   \\_\\___|\\__, |_|___/\n"
                + "              |___/      \n";

        String startMessage = "Hi! This is Aegis!\n"
                + "What can I do for you today?\n";
        String endMessage = "Bye. See you soon!";


        String line = "____________________________________________________________";
        System.out.println(line);
        System.out.println(banner);
        System.out.println(startMessage);
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);

        String[] storage = new String[100];
        int count = 0;

        while (true) {

            String command = scanner.nextLine();

            if (command.equals("bye")) {
                System.out.println(line);
                System.out.println(endMessage);
                System.out.println(line);
                break;
            } else if (command.equals("list")) {
                System.out.println(line);
                for (int i = 0; i < count; i++) {
                    System.out.println((i + 1) + ". " + storage[i]);
                }
                System.out.println(line);
            } else {
                System.out.println(line);
                System.out.println("added: " + command);
                System.out.println(line);
                storage[count] = command;
                count += 1;
            }
        }

    }
}
