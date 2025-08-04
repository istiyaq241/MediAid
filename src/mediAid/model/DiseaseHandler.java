package mediAid.model;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        Label title = new Label("Emergency Symptom Checker");
        title.setStyle("-fx-font-size: 18px; -fx-text-fill: #ff5e5e; -fx-font-weight: bold;");

        Label ageLabel = new Label("Age:");
        ageLabel.setStyle("-fx-text-fill: #f2f2f2;");
        TextField ageField = new TextField();

        Label genderLabel = new Label("Gender:");
        genderLabel.setStyle("-fx-text-fill: #f2f2f2;");
        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        RadioButton other = new RadioButton("Other");
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        other.setToggleGroup(genderGroup);
        male.setStyle("-fx-text-fill: #f2f2f2;");
        female.setStyle("-fx-text-fill: #f2f2f2;");
        other.setStyle("-fx-text-fill: #f2f2f2;");
        HBox genderBox = new HBox(10, male, female, other);

        Label locationLabel = new Label("Location:");
        locationLabel.setStyle("-fx-text-fill: #f2f2f2;");
        ChoiceBox<String> locationBox = new ChoiceBox<>();
        locationBox.getItems().addAll("Urban", "Rural", "Flooded", "Forest", "Coastal", "Disaster zone");

        Label symptomsLabel = new Label("Select Symptoms:");
        symptomsLabel.setStyle("-fx-text-fill: #f2f2f2;");
        CheckBox fever = new CheckBox("Fever");
        CheckBox cold = new CheckBox("Cold");
        CheckBox headache = new CheckBox("Headache");
        CheckBox stomachache = new CheckBox("Stomachache");
        CheckBox bodyache = new CheckBox("Body ache");
        CheckBox nausea = new CheckBox("Nausea");
        CheckBox vomiting = new CheckBox("Vomiting");
        CheckBox rash = new CheckBox("Skin rash");
        CheckBox itching = new CheckBox("Itching");
        CheckBox redpatch = new CheckBox("Red patches");
        CheckBox crying = new CheckBox("Crying/Shaking");
        CheckBox breath = new CheckBox("Short breath");
        CheckBox feet = new CheckBox("Swollen feet");
        CheckBox vision = new CheckBox("Blurred vision");

        List<CheckBox> symptomChecks = List.of(fever, cold, headache, stomachache, bodyache, nausea, vomiting,
                rash, itching, redpatch, crying, breath, feet, vision);
        symptomChecks.forEach(cb -> cb.setStyle("-fx-text-fill: #f2f2f2;"));

        GridPane symptomGrid = new GridPane();
        symptomGrid.setHgap(10);
        symptomGrid.setVgap(5);
        for (int i = 0; i < symptomChecks.size(); i++) {
            symptomGrid.add(symptomChecks.get(i), i % 2, i / 2);
        }

        Label result = new Label();
        result.setWrapText(true);
        result.setStyle("-fx-text-fill: #dddddd;");
        Button submit = new Button("Get Advice");
        Button back = new Button("← Back");

        submit.setOnAction(e -> {
            try {
                int age = Integer.parseInt(ageField.getText().trim());
                String gender = ((RadioButton) genderGroup.getSelectedToggle()).getText();
                String location = locationBox.getValue();

                List<String> selectedSymptoms = new ArrayList<>();
                for (CheckBox cb : symptomChecks) {
                    if (cb.isSelected()) selectedSymptoms.add(cb.getText());
                }

                if (selectedSymptoms.isEmpty()) {
                    result.setText("⚠️ Select at least one symptom.");
                    return;
                }

                String advice = ConditionAdvisor.getAdvice(selectedSymptoms, age, gender, location);
                result.setText(advice);

            } catch (Exception ex) {
                result.setText("⚠️ Please fill in all required fields.");
            }
        });

        back.setOnAction(e -> new mediAid.HomePage(stage).show());

        VBox root = new VBox(12,
                title,
                ageLabel, ageField,
                genderLabel, genderBox,
                locationLabel, locationBox,
                symptomsLabel, symptomGrid,
                submit, result, back
        );
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #2a2a2a;");
        root.setPrefSize(520, 620);

        Scene scene = new Scene(root);
        stage.setTitle("Disease Help");
        stage.setScene(scene);
        stage.show();
    }
}
