package mediAid;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DiseaseHandler {
    private final Stage stage;

    public DiseaseHandler(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Label title = new Label("Disease Help");
        title.setStyle("-fx-font-size: 18px; -fx-text-fill: #9b4dff; -fx-font-weight: bold;");

        Label prompt = new Label("Enter age:");
        prompt.setStyle("-fx-text-fill: #ffffff;");

        TextField ageField = new TextField();
        Button submit = new Button("Submit");
        Button back = new Button("← Back");
        Label result = new Label();
        result.setStyle("-fx-text-fill: #ffffff;");

        submit.setOnAction(e -> {
            String age = ageField.getText().trim();
            if (age.isEmpty()) {
                result.setText("Please enter age.");
            } else {
                int parsedAge = Integer.parseInt(age);
                if (parsedAge > 12) {
                    result.setText("If adult with fever: keep hydrated and rest.");
                } else {
                    result.setText("If child with fever: monitor and consult pediatrician.");
                }
            }
        });

        back.setOnAction(e -> new HomePage(stage).show());

        for (Button b : new Button[]{submit, back}) {
            b.setMaxWidth(Double.MAX_VALUE);
        }

        VBox root = new VBox(10, title, prompt, ageField, submit, result, back);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #1e1e1e;");
        root.setPrefSize(400, 300);

        Scene scene = new Scene(root);
        stage.setTitle("Disease Help");
        stage.setScene(scene);
        stage.show();
    }
}