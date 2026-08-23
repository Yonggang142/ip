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

    /**
     * Constructs an Aegis chatbot with a filePath specified to store chatlogs.
     */
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
     * Creates an Aegis object and calls run() on it.
     */
    public static void main(String[] args) {
        new Aegis("data/Aegis.txt").run();
    }


    /**
     * Executes the chatbot interactions.
     */
    public void run() {
        ui.showStartMessage();
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
