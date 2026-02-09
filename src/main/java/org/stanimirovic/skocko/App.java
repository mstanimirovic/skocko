package org.stanimirovic.skocko;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        var url = Objects.requireNonNull(
            App.class.getResource("/org/stanimirovic/skocko/ui/game.fxml"),
            "FXML Not Foudn"
        );
        FXMLLoader loader = new FXMLLoader(url);

        Parent root = loader.load();

        Scene scene = new Scene(root);

        // Optional: if you also want to load CSS here instead of in FXML
        // scene.getStylesheets().add(
        //         Objects.requireNonNull(App.class.getResource("/com/foo/berba/skocko/ui/game.css")).toExternalForm()
        // );

        stage.setTitle("Skocko");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(520);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
