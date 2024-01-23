package org.example.atgame.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.atgame.Main;

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
        ArrayDisplay arrayDisplay = new ArrayDisplay();
        Stage thirdStage = new Stage();
        // Gombnyomások
        button1.setOnAction(actionEvent ->
                {
                    System.out.println("első gomb");
                    arrayDisplay.start(thirdStage);
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
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
