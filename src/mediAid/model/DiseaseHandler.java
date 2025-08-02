package mediAid;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class DiseaseHandler {
    private final Stage stage;

    public DiseaseHandler(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Label title = new Label("Disease Symptom Checker");
        title.setStyle("-fx-font-size: 18px; -fx-text-fill: #9b4dff; -fx-font-weight: bold;");

        Label ageLabel = new Label("Enter Age:");
        ageLabel.setStyle("-fx-text-fill: white;");
        TextField ageField = new TextField();

        Label genderLabel = new Label("Select Gender:");
        genderLabel.setStyle("-fx-text-fill: white;");
        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        male.setStyle("-fx-text-fill: white;");
        female.setStyle("-fx-text-fill: white;");

        Label symptomsLabel = new Label("Select symptoms:");
        symptomsLabel.setStyle("-fx-text-fill: white;");
        CheckBox fever = new CheckBox("Fever");
        CheckBox cold = new CheckBox("Cold");
        CheckBox headache = new CheckBox("Headache");
        CheckBox stomach = new CheckBox("Stomachache");
        for (CheckBox cb : new CheckBox[]{fever, cold, headache, stomach}) {
            cb.setStyle("-fx-text-fill: white;");
        }

        Button submit = new Button("Get Advice");
        Button back = new Button("← Back");
        Label result = new Label();
        result.setStyle("-fx-text-fill: white;");

        submit.setOnAction(e -> {
            String ageText = ageField.getText().trim();
            Toggle selectedGender = genderGroup.getSelectedToggle();

            if (ageText.isEmpty() || selectedGender == null) {
                result.setText("Please enter age and select gender.");
                return;
            }

            int age = Integer.parseInt(ageText);
            List<String> symptoms = new ArrayList<>();
            if (fever.isSelected()) symptoms.add("Fever");
            if (cold.isSelected()) symptoms.add("Cold");
            if (headache.isSelected()) symptoms.add("Headache");
            if (stomach.isSelected()) symptoms.add("Stomachache");

            if (symptoms.isEmpty()) {
                result.setText("Select at least one symptom.");
                return;
            }

            String advice = "";
            if (symptoms.contains("Fever") && symptoms.contains("Cold")) {
                advice = "Stay warm, drink fluids, and use paracetamol if needed.";
            } else if (symptoms.contains("Headache") && symptoms.contains("Stomachache")) {
                advice = "Rest, drink water, avoid solid food, consider paracetamol.";
            } else if (symptoms.contains("Stomachache")) {
                advice = "Avoid dairy, drink ORS, and rest.";
            } else if (symptoms.contains("Fever")) {
                advice = "Monitor temperature, use cold compress, stay hydrated.";
            } else {
                advice = "Rest and monitor symptoms. Seek help if worsens.";
            }

            result.setText("Advice: " + advice);
        });

        back.setOnAction(e -> new mediAid.HomePage(stage).show());

        for (Button b : new Button[]{submit, back}) {
            b.setMaxWidth(Double.MAX_VALUE);
        }

        VBox root = new VBox(10,
                title,
                ageLabel, ageField,
                genderLabel, male, female,
                symptomsLabel, fever, cold, headache, stomach,
                submit, result, back
        );

        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #1e1e1e;");
        root.setPrefSize(420, 480);

        Scene scene = new Scene(root);
        stage.setTitle("Disease Help");
        stage.setScene(scene);
        stage.show();
    }
}