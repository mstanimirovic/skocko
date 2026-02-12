package org.stanimirovic.skocko.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.stanimirovic.skocko.domain.*;

public class GameController {
    // ====== CONTROLS ======
    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnClear;

    // ====== BOARD CELLS (Guesses) ======
    @FXML
    private StackPane g00;

    @FXML
    private StackPane g01;

    @FXML
    private StackPane g02;

    @FXML
    private StackPane g03;

    @FXML
    private StackPane g10;

    @FXML
    private StackPane g11;

    @FXML
    private StackPane g12;

    @FXML
    private StackPane g13;

    @FXML
    private StackPane g20;

    @FXML
    private StackPane g21;

    @FXML
    private StackPane g22;

    @FXML
    private StackPane g23;

    @FXML
    private StackPane g30;

    @FXML
    private StackPane g31;

    @FXML
    private StackPane g32;

    @FXML
    private StackPane g33;

    @FXML
    private StackPane g40;

    @FXML
    private StackPane g41;

    @FXML
    private StackPane g42;

    @FXML
    private StackPane g43;

    @FXML
    private StackPane g50;

    @FXML
    private StackPane g51;

    @FXML
    private StackPane g52;

    @FXML
    private StackPane g53;

    // ====== FEEDBACK DOTS ======
    @FXML
    private Circle f00;

    @FXML
    private Circle f01;

    @FXML
    private Circle f02;

    @FXML
    private Circle f03;

    @FXML
    private Circle f10;

    @FXML
    private Circle f11;

    @FXML
    private Circle f12;

    @FXML
    private Circle f13;

    @FXML
    private Circle f20;

    @FXML
    private Circle f21;

    @FXML
    private Circle f22;

    @FXML
    private Circle f23;

    @FXML
    private Circle f30;

    @FXML
    private Circle f31;

    @FXML
    private Circle f32;

    @FXML
    private Circle f33;

    @FXML
    private Circle f40;

    @FXML
    private Circle f41;

    @FXML
    private Circle f42;

    @FXML
    private Circle f43;

    @FXML
    private Circle f50;

    @FXML
    private Circle f51;

    @FXML
    private Circle f52;

    @FXML
    private Circle f53;

    // ====== INTERNAL UI MAPS ======
    private StackPane[][] guessCells; // [row][col]
    private Circle[][] feedbackDots; // [row][col]

    // ====== GAME STATE ======
    private SkockoGame game;
    private final List<Symbol> current = new ArrayList<>(4);

    // If you want deterministic games for tests, set a seed.
    private final Random rng = new Random();

    @FXML
    public void initialize() {
        wireCellMatrices();
        newGame();
    }

    // ---------- Palette actions (from FXML) ----------
    @FXML
    private void onPickSkocko() {
        pick(Symbol.SKOCKO);
    }

    @FXML
    private void onPickTref() {
        pick(Symbol.TREF);
    }

    @FXML
    private void onPickPik() {
        pick(Symbol.PIK);
    }

    @FXML
    private void onPickSrce() {
        pick(Symbol.SRCE);
    }

    @FXML
    private void onPickKaro() {
        pick(Symbol.KARO);
    }

    @FXML
    private void onPickZvezda() {
        pick(Symbol.ZVEZDA);
    }

    // ---------- Controls ----------
    @FXML
    private void onBackspace() {
        if (isFinished()) return;
        if (!current.isEmpty()) {
            int idx = current.size() - 1;
            current.remove(idx);
            renderCurrentRow();
            updateControls();
        }
    }

    @FXML
    private void onClear() {
        if (isFinished()) return;
        if (!current.isEmpty()) {
            current.clear();
            renderCurrentRow();
            updateControls();
        }
    }

    @FXML
    private void onSubmit() {
        if (isFinished()) return;

        if (current.size() != 4) {
            return;
        }

        try {
            int row = game.turns().size(); // current row index
            Guess guess = new Guess(current);
            game.submitGuess(guess);

            // Render submitted row (guess already shown; ensure it is final)
            renderRow(row, guess.symbols());

            // Render feedback
            Turn turn = game.turns().get(row);
            renderFeedbackRow(row, turn.feedback());

            // Prepare next row
            current.clear();

            // End game?
            if (game.phase() == GamePhase.WON) {
                showResultDialog(true);
            } else if (game.phase() == GamePhase.LOST) {
                showResultDialog(false);
            }

            updateControls();
        } catch (RuntimeException ex) {
            // TODO: ERROR POPUP
        }
    }

    private void showResultDialog(boolean won) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog.fxml"));
            Scene scene = new Scene(loader.load());

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.setTitle(won ? "Win" : "Game Over");
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);

            DialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOnNewGameCallback(this::newGame);

            if (won) {
                controller.showWin(game.turns().size());
            } else {
                controller.showLoss(game.secret().symbols());
            }

            dialogStage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            // Fallback: just print to console
            if (won) {
                System.out.println("YOU WON!");
            } else {
                System.out.println("YOU LOST! Secret: " + game.secret().symbols());
            }
        }
    }

    @FXML
    private void onNewGame() {
        newGame();
    }

    // ---------- Core helpers ----------
    private void pick(Symbol symbol) {
        if (isFinished()) return;

        if (current.size() >= 4) {
            return;
        }

        current.add(symbol);
        renderCurrentRow();
        updateControls();
    }

    private void newGame() {
        this.game = new SkockoGame(randomSecret());
        this.current.clear();

        clearBoardUI();
        clearFeedbackUI();

        updateControls();

        // Optionally highlight active row via CSS class; keeping it minimal here.
    }

    private Guess randomSecret() {
        Symbol[] pool = Symbol.values();
        List<Symbol> secret = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            secret.add(pool[rng.nextInt(pool.length)]);
        }
        return new Guess(secret);
    }

    private boolean isFinished() {
        return game != null && game.phase() != GamePhase.BUILDING_GUESS;
    }

    private void updateControls() {
        boolean finished = isFinished();
        btnBack.setDisable(finished || current.isEmpty());
        btnClear.setDisable(finished || current.isEmpty());
        btnSubmit.setDisable(finished || current.size() != 4);
    }

    // ---------- Rendering ----------
    private void renderCurrentRow() {
        int row = game.turns().size(); // active row is next empty row
        renderRow(row, current);
        // Clear remaining cells in that row (when deleting)
        for (int col = current.size(); col < 4; col++) {
            guessCells[row][col].getChildren().clear();
        }
    }

    private void renderRow(int row, List<Symbol> symbols) {
        for (int col = 0; col < 4; col++) {
            StackPane cell = guessCells[row][col];
            cell.getChildren().clear();

            if (col < symbols.size()) {
                // Minimal rendering: colored circle + label could be used, but keeping it purely visual.
                Circle c = new Circle(22);
                c.setFill(colorFor(symbols.get(col)));
                cell.getChildren().add(c);
            }
        }
    }

    private void renderFeedbackRow(int row, Feedback fb) {
        // Convention: fill exact (red) first, then present (yellow), remaining empty/gray
        int exact = fb.exact();
        int present = fb.present();

        for (int i = 0; i < 4; i++) {
            Circle dot = feedbackDots[row][i];
            if (i < exact) {
                dot.setFill(Color.RED);
                dot.setOpacity(1.0);
            } else if (i < exact + present) {
                dot.setFill(Color.GOLD);
                dot.setOpacity(1.0);
            } else {
                dot.setFill(Color.GRAY);
                dot.setOpacity(0.25);
            }
        }
    }

    private void clearBoardUI() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 4; c++) {
                guessCells[r][c].getChildren().clear();
            }
        }
    }

    private void clearFeedbackUI() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 4; c++) {
                feedbackDots[r][c].setFill(Color.GRAY);
                feedbackDots[r][c].setOpacity(0.15);
            }
        }
    }

    private Color colorFor(Symbol s) {
        // Placeholder mapping. Replace with CSS / images later.
        return switch (s) {
            case SKOCKO -> Color.DEEPSKYBLUE;
            case TREF -> Color.DARKGREEN;
            case PIK -> Color.DARKSLATEGRAY;
            case SRCE -> Color.CRIMSON;
            case KARO -> Color.ORANGE;
            case ZVEZDA -> Color.MEDIUMPURPLE;
        };
    }

    // ---------- Wiring UI node matrices ----------
    private void wireCellMatrices() {
        guessCells = new StackPane[][] {
            { g00, g01, g02, g03 },
            { g10, g11, g12, g13 },
            { g20, g21, g22, g23 },
            { g30, g31, g32, g33 },
            { g40, g41, g42, g43 },
            { g50, g51, g52, g53 },
        };

        feedbackDots = new Circle[][] {
            { f00, f01, f02, f03 },
            { f10, f11, f12, f13 },
            { f20, f21, f22, f23 },
            { f30, f31, f32, f33 },
            { f40, f41, f42, f43 },
            { f50, f51, f52, f53 },
        };
    }
}
