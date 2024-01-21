module org.example.atgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires rgxgen;


    opens org.example.atgame to javafx.fxml;
    exports org.example.atgame;
    exports org.example.atgame.GUI;
    opens org.example.atgame.GUI to javafx.fxml;
    exports org.example.atgame.GlueComponents;
    opens org.example.atgame.GlueComponents to javafx.fxml;
    exports org.example.atgame.RegexToDFA;
    opens org.example.atgame.RegexToDFA to javafx.fxml;
}