package org.example.atgame.GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.atgame.GlueComponents.RandomStringGenerator;

public class ArrayDisplay extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Példa tömb létrehozása
        //String[] arrayData = {"Elem 1", "Elem 2", "Elem 3", "Elem 4", "Elem 5"};
        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();

        // Tömb tartalmának ObservableList-be helyezése (JavaFX adatstruktúra)
        ObservableList<String> observableList = FXCollections.observableArrayList(randomStringGenerator.main());

        // ListView létrehozása és az ObservableList hozzáadása
        ListView<String> listView = new ListView<>(observableList);

        // JavaFX felület összeállítása
        StackPane root = new StackPane();
        root.getChildren().add(listView);

        // JavaFX ablak konfigurálása
        Scene scene = new Scene(root, 800, 800);

        primaryStage.setTitle("Array Display");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
