package aegis;

import aegis.task.Deadline;
import aegis.task.Event;
import aegis.task.Task;
import aegis.task.ToDo;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving tasks to a local storage file specified by the filePath.
 */
public class Storage {

    private final String filePath;

    /**
     * Creates a Storage object that reads and writes to the given file path.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads all tasks from the storage file.
     * If the file does not exist, it will be created (along with any parent directories).
     *
     * @return The list of tasks loaded from the file.
     * @throws AegisException If a line in the file is malformed, contains an unknown
     *                        task type, or has an invalid status or date.
     * @throws IOException If the file cannot be read or created.
     */
    public ArrayList<Task> load() throws AegisException, IOException {
        Path path = Paths.get(filePath);

        ArrayList<Task> taskList = new ArrayList<>();
        if (!Files.exists(path)) {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.createFile(path);
        }

        Scanner s = new Scanner(path);
        while (s.hasNext()) {
            String currLine = s.nextLine();
            String[] parts = currLine.split(" \\| ");
            if (parts.length < 2) {
                throw new AegisException("Incorrect format in file");
            }
            String identifier = parts[0];
            String status = parts[1];
            if (!status.equals("0") && !status.equals("1")) {
                throw new AegisException("Incorrect task status in file");
            }
            boolean isDone = status.equals("1");
            switch (identifier) {
                case "T": {
                    if (parts.length != 3) {
                        throw new AegisException("Incorrect todo format in file");
                    }
                    taskList.add(new ToDo(parts[2], isDone));
                    break;
                }
                case "D": {
                    if (parts.length != 4) {
                        throw new AegisException("Incorrect deadline format in file");
                    }
                    taskList.add(new Deadline(parts[2], LocalDate.parse(parts[3]), isDone));
                    break;
                }
                case "E": {
                    if (parts.length != 5) {
                        throw new AegisException("Incorrect event format in file");
                    }
                    taskList.add(new Event(parts[2],
                            LocalDate.parse(parts[3]), LocalDate.parse(parts[4]), isDone));
                    break;
                }
                default:
                    throw new AegisException("Unknown information in file");
            }
        }
        return taskList;
    }

    /**
     * Saves all tasks in the provided list to the storage file.
     * Each task is written on its own line using its file save format.
     *
     * @param taskList The list of tasks to save to the file.
     * @throws IOException If the file cannot be written to.
     */
    public void saveToFile(TaskList taskList) throws IOException {
        StringBuilder textToAdd = new StringBuilder();
        for (int i = 0; i < taskList.size(); i++) {
            textToAdd.append(taskList.get(i).getFileSaveFormat())
                    .append(System.lineSeparator());
        }
        FileWriter fw = new FileWriter(filePath);
        fw.write(textToAdd.toString());
        fw.close();
    }
}
