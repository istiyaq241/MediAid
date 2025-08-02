package mediAid;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccidentHandler {
    private final Stage stage;

    public AccidentHandler(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Label info = new Label("Choose an accident type:");
        info.setStyle("-fx-text-fill: #ff4c4c; -fx-font-size: 16px; -fx-font-weight: bold;");

        Button burn = new Button("🔥 Burn");
        Button snake = new Button("🐍 Snake Bite");
        Button emergency = new Button("🚨 Emergency Case");
        Button back = new Button("← Back");

        burn.setOnAction(e -> info.setText("🔥 Burn: Cool area under running water for 10 minutes."));
        snake.setOnAction(e -> info.setText("🐍 Snake Bite: Keep victim calm, immobilize limb."));
        emergency.setOnAction(e -> new EmergencyCase(stage).show());
        back.setOnAction(e -> new HomePage(stage).show());

        for (Button b : new Button[]{burn, snake, emergency, back}) {
            b.setMaxWidth(Double.MAX_VALUE);
        }

        VBox root = new VBox(12, info, burn, snake, emergency, back);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #1e1e1e;");

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Accident Help");
        stage.setScene(scene);
        stage.show();
    }
}