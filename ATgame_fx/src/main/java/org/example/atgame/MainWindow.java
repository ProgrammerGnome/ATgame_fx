package org.example.atgame;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.atgame.GUI.RandomStringWindow;


import javafx.scene.control.Label;
import org.example.atgame.GlueComponents.RandomStringGenerator;

import java.io.IOException;

import static javafx.application.Application.launch;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Létrehozunk három gombot
        Button button1 = new Button("Random szöveg generáló");
        Button button2 = new Button("FSM szerkesztő");
        Button button3 = new Button("Megoldás ellenőrző");

        // Létrehozunk egy VBox elrendezést, amely elrendez minden elemet egymás alatt
        VBox vbox = new VBox(button1, button2, button3);
        int prefHeight = 50;
        int prefWidth = 250;
        button1.setPrefHeight(prefHeight);
        button1.setPrefWidth(prefWidth);
        button2.setPrefHeight(prefHeight);
        button2.setPrefWidth(prefWidth);
        button3.setPrefHeight(prefHeight);
        button3.setPrefWidth(prefWidth);

        // Létrehozunk egy Scene-t a VBox-szal
        Scene scene = new Scene(vbox, 300, 200);

        // Beállítjuk az ablak címét
        primaryStage.setTitle("ATgame_fx főablak");

        // Beállítjuk az ablak Scene-jét
        primaryStage.setScene(scene);

        // Megjelenítjük az ablakot
        primaryStage.show();

        Main main = new Main();
        Stage secondStage = new Stage();
        RandomStringWindow randomStringWindow = new RandomStringWindow();
        Stage thirdStage = new Stage();
        // Gombnyomások
        button1.setOnAction(actionEvent ->
                {
                    System.out.println("első gomb");
                    randomStringWindow.start(thirdStage);
                }
        );
        button2.setOnAction(actionEvent ->
                {
                    System.out.println("második gomb");
                    main.start(secondStage);
                }
        );
        button3.setOnAction(actionEvent ->
                System.out.println("harmadik gomb")
                // TODO: megvalósításra vár...
        );




        Scene scene1 = createScene1();
        Scene scene2 = createScene2();

        // Hozz létre egy fő VBox-ot a két Scene kombinálásához
        HBox root = new HBox();
        root.getChildren().addAll(scene1.getRoot(), scene2.getRoot());

        // Állítsd be az új Scene-t a fő Stage-ben
        primaryStage.setScene(new Scene(root, 1050, 650));
        primaryStage.setTitle("Combined Scenes");
        primaryStage.show();
    }

    private Scene createScene1() {

        HBox content = new HBox();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
//            throw new RuntimeException(e);
        }
        content.getChildren().add(root);

        return new Scene(content, 300, 200);
    }

    private Scene createScene2() {
        HBox content = new HBox();

        RandomStringGenerator randomStringGenerator = new RandomStringGenerator();

        // Tömb tartalmának ObservableList-be helyezése (JavaFX adatstruktúra)
        ObservableList<String> observableList = FXCollections.observableArrayList(randomStringGenerator.main());

        // ListView létrehozása és az ObservableList hozzáadása
        ListView<String> listView = new ListView<>(observableList);

        // JavaFX felület összeállítása
        StackPane root = new StackPane();
        root.getChildren().add(listView);

        // JavaFX ablak konfigurálása
        content.getChildren().add(root);
        return new Scene(content, 300, 200);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
