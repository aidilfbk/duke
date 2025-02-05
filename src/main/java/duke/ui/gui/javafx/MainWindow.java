package duke.ui.gui.javafx;

import duke.Duke;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends VBox {
    private static Image USER_IMAGE = new Image(MainWindow.class.getResourceAsStream("/duke/images/DaUser.png"));
    private static Image DUKE_IMAGE = new Image(MainWindow.class.getResourceAsStream("/duke/images/DaDuke.png"));
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    private Duke duke;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, USER_IMAGE));
        userInput.clear();
        boolean shouldExit = duke.consumeUserInput(input);
        if (shouldExit) {
            Platform.exit();
        }
    }

    public void addDukeMessage(String dukeOutput) {
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(dukeOutput, DUKE_IMAGE));
    }
}
