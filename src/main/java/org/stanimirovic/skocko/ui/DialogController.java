package org.stanimirovic.skocko.ui;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.stanimirovic.skocko.domain.Symbol;

public class DialogController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private VBox secretContainer;

    @FXML
    private Circle secretCircle0;

    @FXML
    private Circle secretCircle1;

    @FXML
    private Circle secretCircle2;

    @FXML
    private Circle secretCircle3;

    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnClose;

    private Stage dialogStage;
    private Runnable onNewGameCallback;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setOnNewGameCallback(Runnable callback) {
        this.onNewGameCallback = callback;
    }

    /**
     * Configure dialog for WIN scenario
     */
    public void showWin(int attempts) {
        titleLabel.setText("YOU WON!");
        titleLabel.setStyle("-fx-text-fill: #2ecc71;");

        String message = switch (attempts) {
            case 1 -> "Unbelievable! You guessed in first attempt!";
            case 2 -> "Excellent! You guessed in 2 attempts!";
            case 3 -> "Great! You guessed in 3 attempts!";
            default -> "Congratulations! You guessed in " + attempts + " attempts!";
        };

        messageLabel.setText(message);
        secretContainer.setVisible(false);
        secretContainer.setManaged(false);
    }

    /**
     * Configure dialog for LOSS scenario and show secret
     */
    public void showLoss(List<Symbol> secret) {
        titleLabel.setText("GAME OVER!");
        titleLabel.setStyle("-fx-text-fill: #e74c3c;");

        messageLabel.setText("Try Again!");

        secretContainer.setVisible(true);
        secretContainer.setManaged(true);

        // Render secret combination
        Circle[] circles = {secretCircle0, secretCircle1, secretCircle2, secretCircle3};
        for (int i = 0; i < 4 && i < secret.size(); i++) {
            circles[i].setFill(colorFor(secret.get(i)));
        }
    }

    @FXML
    private void onNewGame() {
        if (onNewGameCallback != null) {
            onNewGameCallback.run();
        }
        dialogStage.close();
    }

    @FXML
    private void onClose() {
        dialogStage.close();
    }

    private Color colorFor(Symbol s) {
        return switch (s) {
            case SKOCKO -> Color.DEEPSKYBLUE;
            case TREF -> Color.DARKGREEN;
            case PIK -> Color.DARKSLATEGRAY;
            case SRCE -> Color.CRIMSON;
            case KARO -> Color.ORANGE;
            case ZVEZDA -> Color.MEDIUMPURPLE;
        };
    }
}