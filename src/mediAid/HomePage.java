package mediAid;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mediAid.model.DiseaseHandler;

public class HomePage {
    private final Stage stage;

    public HomePage(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Label title = new Label("MediAid Home");
        title.setStyle("-fx-font-size: 20px; -fx-text-fill: #ff4c4c; -fx-font-weight: bold;");

        Button accidentBtn = new Button("🚑 Accident Help");
        Button diseaseBtn = new Button("🤒 Disease Help");

        accidentBtn.setOnAction(e -> new mediAid.AccidentHandler(stage).show());
        diseaseBtn.setOnAction(e -> new DiseaseHandler(stage).show());

        accidentBtn.setMaxWidth(Double.MAX_VALUE);
        diseaseBtn.setMaxWidth(Double.MAX_VALUE);

        VBox root = new VBox(20, title, accidentBtn, diseaseBtn);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #1e1e1e;");
        root.setPrefSize(400, 250);

        Scene scene = new Scene(root);
        stage.setTitle("MediAid System – Home");
        stage.setScene(scene);
        stage.show();
    }
}