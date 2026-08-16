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

        System.out.println(banner + "\n" + startMessage);

        Scanner scanner = new Scanner(System.in);


        while (true) {

            String command = scanner.nextLine();

            if (command.equals("bye")) {
                System.out.println(endMessage);

            } else {
                System.out.println(command);
                break;
            }
        }

    }
}
