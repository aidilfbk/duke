import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class Duke {
    public static void main(String[] args) {
        new Duke("./data/duke.txt").run();
    }

    private final TaskSerializer storage;
    private TaskList tasks;
    private final UserInterface ui;

    public Duke(String filePath) {
        ui = new UserInterface();
        storage = new TaskSerializer(Path.of(filePath));

        try {
            tasks = new TaskList(storage.load());
        } catch (FileIOException | TokenParseError exc) {
            if (!(exc.getCause() instanceof NoSuchFileException)) {
                ui.displayLoadingError(exc);
            }
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.displayWelcome();

        boolean isDone = false;
        while (!isDone) {
            final String input = ui.nextLine();
            try {
                Command c = Parser.parse(input);
                c.execute(tasks, ui, storage);
                isDone = c.isExit();
            } catch (DukeException exc) {
                ui.displayError(exc);
            }
        }
    }
}
