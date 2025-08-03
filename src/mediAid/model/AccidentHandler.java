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
        Label title = new Label("Accident Help");
        title.setStyle("-fx-font-size: 18px; -fx-text-fill: #ff4c4c; -fx-font-weight: bold;");

        Label info = new Label("Choose an accident type:");
        info.setStyle("-fx-text-fill: white;");

        Button burn = new Button("🔥 Burn");
        Button snake = new Button("🐍 Snake Bite");
        Button emergency = new Button("🚨 Emergency Case");
        Button back = new Button("← Back");

        burn.setOnAction(e -> info.setText("🔥 Burn: Cool area under running water for 10 minutes."));
        snake.setOnAction(e -> info.setText("🐍 Snake Bite: Keep victim calm, immobilize limb."));
        emergency.setOnAction(e -> new mediAid.EmergencyCase(stage).show());
        back.setOnAction(e -> new mediAid.HomePage(stage).show());

        for (Button b : new Button[]{burn, snake, emergency, back}) {
            b.setMaxWidth(Double.MAX_VALUE);
        }

        VBox root = new VBox(15, title, info, burn, snake, emergency, back);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #1e1e1e;");
        root.setPrefSize(400, 300);

        Scene scene = new Scene(root);
        stage.setTitle("Accident Help");
        stage.setScene(scene);
        stage.show();
    }
}