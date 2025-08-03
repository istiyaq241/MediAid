package mediAid;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmergencyCase {
    private final Stage stage;

    public EmergencyCase(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Label title = new Label("Emergency Guidelines");
        title.setStyle("-fx-font-size: 18px; -fx-text-fill: #ff4c4c; -fx-font-weight: bold;");

        Label cpr = new Label("• CPR: Call help, compress chest rhythmically.");
        Label choking = new Label("• Choking: Perform abdominal thrusts.");
        Label bleed = new Label("• Heavy bleeding: Apply pressure, elevate limb.");
        for (Label l : new Label[]{cpr, choking, bleed}) {
            l.setStyle("-fx-text-fill: #ffffff;");
        }

        Button back = new Button("← Back");
        back.setOnAction(e -> new mediAid.AccidentHandler(stage).show());

        VBox root = new VBox(15, title, cpr, choking, bleed, back);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #1e1e1e;");
        root.setPrefSize(400, 300);

        Scene scene = new Scene(root);
        stage.setTitle("Emergency Case");
        stage.setScene(scene);
        stage.show();
    }
}