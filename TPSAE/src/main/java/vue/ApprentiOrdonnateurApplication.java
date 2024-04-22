package vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class ApprentiOrdonnateurApplication extends Application {
    public void start(Stage stage) {
        VBox root = new VBoxRoot();
        Scene scene = new Scene(root, 980, 800);
        stage.setScene(scene);
        stage.setTitle("SAE");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
