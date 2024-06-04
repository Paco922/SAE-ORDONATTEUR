module org.example.tpsae {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.junit.jupiter.api;

    opens org.example.tpsae to javafx.fxml;

    exports vue;
}