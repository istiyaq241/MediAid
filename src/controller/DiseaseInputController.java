package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ConditionAdvisor;
import model.ReminderManager;

import java.util.ArrayList;
import java.util.List;

public class DiseaseInputController {

    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private ComboBox<String> locationBox;
    @FXML private VBox symptomList;
    @FXML private Label nameError;
    @FXML private Label ageError;

    private String initialSymptom;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setInitialSymptom(String symptom) {
        this.initialSymptom = symptom;
    }

    @FXML
    public void initialize() {
        locationBox.getItems().addAll("Urban", "Rural", "Coastal");

        String[] options = {"fever", "cold", "cough", "headache", "stomachache", "nausea", "vomiting", "bodyache", "diarrhoea"};
        for (String s : options) {
            CheckBox cb = new CheckBox(s);
            cb.setStyle("-fx-text-fill: white;");
            symptomList.getChildren().add(cb);
        }
    }

    @FXML
    public void handleContinue() {
        boolean isValid = true;
        nameError.setText("");
        ageError.setText("");

        String name = nameField.getText().trim();
        if (!name.matches("[a-zA-Z\\s]+")) {
            nameError.setText("Only letters allowed in name.");
            isValid = false;
        }

        int age = -1;
        try {
            age = Integer.parseInt(ageField.getText().trim());
            if (age < 0 || age > 120) {
                ageError.setText("Age must be 0–120.");
                isValid = false;
            }
        } catch (Exception e) {
            ageError.setText("Enter a valid number.");
            isValid = false;
        }

        if (!isValid || locationBox.getValue() == null) return;

        List<String> symptoms = new ArrayList<>();
        symptoms.add(initialSymptom);

        for (javafx.scene.Node node : symptomList.getChildren()) {
            CheckBox cb = (CheckBox) node;
            if (cb.isSelected()) {
                symptoms.add(cb.getText());
            }
        }

        ReminderManager.addReminder(name, initialSymptom);
        String resultCondition = ConditionAdvisor.getAdvice(symptoms, age, "", locationBox.getValue());
        TreatmentDisplayController.show(stage, resultCondition);
    }
}
