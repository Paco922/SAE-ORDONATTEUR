module org.example.tpsae {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.tpsae to javafx.fxml;

    exports vue;
}