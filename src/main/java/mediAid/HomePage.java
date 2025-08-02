package main.java.mediAid;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage {
    public void show(Stage primaryStage) {
        Label label = new Label("Choose: Accident or Disease");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label);

        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MediAid - Home");
        primaryStage.show();
    }
}
