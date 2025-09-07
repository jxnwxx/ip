package peppy.ui;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Peppy peppy;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private final Image peppyImage = new Image(this.getClass().getResourceAsStream("/images/Peppy.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Duke instance
     */
    public void setPeppy(Peppy p) {
        peppy = p;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws InterruptedException {
        String input = userInput.getText();
        String response = peppy.getResponse(input);

        if (response.equals("__SHOW_HELP__")) {
            showHelpDialog();
        } else {
            displayResponse(input, response);
        }
    }

    private void displayResponse(String input, String response) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getPeppyDialog(response, peppyImage)
        );

        if (input.equalsIgnoreCase("bye")) {
            exitAfterDelay();
        }
        userInput.clear();
    }

    private void showHelpDialog() {
        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("Peppy Help");
        helpAlert.setHeaderText("Command Usage Guide");
        helpAlert.setContentText(
                """
                Here are the commands you can use:
    
                1. todo <description>
                2. deadline <description> /by <due> (date format: dd-MM-yyyy HHmm)
                3. event <description> /from <start> /to <end> (date format: dd-MM-yyyy HHmm)
                4. mark <index>
                5. unmark <index>
                6. delete <index>
                7. find <keyword>
                8. list
                9. bye
                """
        );

        helpAlert.setResizable(true);
        helpAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        helpAlert.showAndWait();

        userInput.clear();
    }

    private static void exitAfterDelay() {
        new Timer().schedule(new TimerTask() {
            public void run() {
                Platform.exit();
                System.exit(0);
            }
        }, 500);
    }
}

