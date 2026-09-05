package aegis;

import java.io.IOException;
import java.util.Scanner;

/**
 * Runs the Aegis chatbot, which manages tasks through command-line input.
 */
public class Aegis {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    /**
     * Constructs an Aegis chatbot with a file path specified to store chat logs.
     */
    public Aegis(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.load());
        } catch (AegisException e) {
            System.out.println(ui.getLoadingErrorMessage());
            tasks = new TaskList();
        } catch (IOException e) {
            System.out.println(ui.getErrorMessage(e.getMessage()));
            tasks = new TaskList();
        }
    }

    /**
     * Creates an Aegis object and calls run() on it.
     */
    public static void main(String[] args) {
        new Aegis("data/aegis.txt").run();
    }


    /**
     * Executes the chatbot interactions.
     */
    public void run() {
        System.out.println(ui.getStartMessage());
        Scanner scanner = new Scanner(System.in);
        boolean hasEnded = false;

        while (!hasEnded) {
            try {
                Command command = parser.parse(scanner.nextLine());
                System.out.println(command.execute(tasks, ui, storage));
                hasEnded = command.hasEnded();
            } catch (AegisException e) {
                System.out.println(ui.getErrorMessage(e.getMessage()));
            } catch (IOException e) {
                System.out.println(ui.getErrorMessage("Something went wrong: " + e.getMessage()));
            }
        }
    }

    /**
     * Processes one user input and returns the chatbot response for the GUI.
     */
    public String getResponse(String input) {
        try {
            Command command = parser.parse(input);
            return command.execute(tasks, ui, storage);
        } catch (AegisException e) {
            return ui.getErrorMessage(e.getMessage());
        } catch (IOException e) {
            return ui.getErrorMessage("Something went wrong: " + e.getMessage());
        }
    }
}
