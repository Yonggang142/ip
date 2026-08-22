import java.util.Scanner;
import java.io.IOException;

/**
 * Runs the Aegis chatbot, which manages tasks through command-line input.
 */
public class Aegis {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Aegis(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (AegisException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        } catch (IOException e) {
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }




    /**
     * Returns void.
     * Creates an Aegis object and calls run() on it
     */
    public static void main(String[] args) {
        new Aegis("data/Aegis.txt").run();
    }


    /**
     * Returns void.
     * Executes the chatbot interactions
     */
    public void run() {
        Parser parser = new Parser();
        Scanner scanner = new Scanner(System.in);
        boolean isEnd = false;

        while (!isEnd) {
            try {
                Command command = parser.parse(scanner.nextLine());
                command.execute(tasks, ui, storage);
                isEnd = command.hasEnded();
            } catch (AegisException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("Something went wrong: " + e.getMessage());
            }
        }
    }
}
