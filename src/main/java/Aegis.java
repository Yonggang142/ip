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

        Task[] storage = new Task[100];

        int count = 0;

        while (true) {

            String command = scanner.nextLine();
            String[] parts = command.split(" ", 2);
            String action = parts[0];
            String details = parts.length > 1 ? parts[1] : "";

            switch (action) {
                case "bye": {
                    System.out.println(line);
                    System.out.println(endMessage);
                    System.out.println(line);
                    return;
                }

                case "list": {
                    System.out.println(line);
                    System.out.println("Here are the tasks in the list:");
                    for (int i = 0; i < count; i++) {
                        Task currentTask = storage[i];

                        System.out.println((i + 1) + "." + storage[i]);
                    }
                    System.out.println(line);
                    break;
                }

                case "mark": {

                    int markIndex = Integer.parseInt(parts[1]) - 1;
                    Task currentTask = storage[markIndex];
                    currentTask.mark();
                    System.out.println(line);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(storage[markIndex]);
                    System.out.println(line);
                    break;
                }

                case "unmark": {
                    int unmarkIndex = Integer.parseInt(parts[1]) - 1;
                    Task currentTask = storage[unmarkIndex];
                    currentTask.unmark();

                    System.out.println(line);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(storage[unmarkIndex]);
                    System.out.println(line);
                    break;
                }

                case "todo": {

                    Task newTask = new ToDo(details);
                    storage[count] = newTask;
                    count += 1;
                    System.out.println(line);
                    System.out.println("OK, I've added a new task: ");
                    System.out.println(newTask);
                    System.out.println("Now you have " + count + " tasks in the list");
                    System.out.println(line);
                    break;
                }

                case "deadline": {


                    String[] eventParts = details.split(" /by ", 2);
                    String description = eventParts[0];
                    String timeDetails = eventParts[1];

                    Task newTask = new Deadline(description, timeDetails);
                    storage[count] = newTask;
                    count += 1;
                    System.out.println(line);
                    System.out.println("OK, I've added a new task: ");
                    System.out.println(newTask);
                    System.out.println("Now you have " + count + " tasks in the list");
                    System.out.println(line);
                    break;
                }

                case "event": {

                    String[] eventParts = details.split(" /from ", 2);
                    String description = eventParts[0];
                    String timeDetails = eventParts[1];

                    String[] timeParts = timeDetails.split(" /to ", 2);
                    String from = timeParts[0];
                    String to = timeParts[1];


                    Task newTask = new Event(description, from, to);
                    storage[count] = newTask;
                    count += 1;
                    System.out.println(line);
                    System.out.println("OK, I've added a new task: ");
                    System.out.println(newTask);
                    System.out.println("Now you have " + count + " tasks in the list");
                    System.out.println(line);

                    break;
                }

                 default: {
                    System.out.println(line);
                    System.out.println("added: " + command);
                    System.out.println(line);
                    storage[count] = new ToDo(command);
                    count += 1;
                    break;
                }
            }
        }
    }


}
