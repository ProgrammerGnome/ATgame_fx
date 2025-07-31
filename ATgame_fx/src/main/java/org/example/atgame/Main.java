package org.example.atgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.atgame.GUI.FSMEditor;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("FSM Editor");
            primaryStage.setScene(scene);

            // Itt állíthatjuk be a vezérlő osztályt, ha szükséges
            //FSMEditor controller = loader.getController();

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
