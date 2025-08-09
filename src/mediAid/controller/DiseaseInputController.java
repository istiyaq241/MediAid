package mediAid.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import mediAid.Main;
import mediAid.TreatmentLoader;
import mediAid.model.TreatmentContext;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class DiseaseInputController {

    @FXML private TextField nameField, ageField;
    @FXML private ComboBox<String> genderBox, locationBox;
    @FXML private ComboBox<String> diseaseBox;
    @FXML private FlowPane symptomPane;
    @FXML private Label validationLabel;

    private final List<CheckBox> symptomChecks = new ArrayList<>();

    @FXML
    public void initialize() {
        if (genderBox != null) genderBox.getItems().addAll("Male", "Female", "Other");
        if (locationBox != null) locationBox.getItems().addAll("Rural", "Urban", "Coastal", "Forest", "Disaster Shelter");

        String[] minorSymptoms = {"headache","cold","cough","bodyache","nausea","vomiting","fever","diarrhoea","stomachache"};
        for (String symptom : minorSymptoms) {
            CheckBox cb = new CheckBox(symptom);
            symptomChecks.add(cb);
            if (symptomPane != null) symptomPane.getChildren().add(cb);
        }

        String majorSymptom = TreatmentContext.getCondition();
        if (diseaseBox != null && majorSymptom != null && !majorSymptom.isEmpty()) {
            List<String> matches = TreatmentLoader.getDiseasesByMinorSymptom(majorSymptom);
            if (matches.isEmpty()) {
                diseaseBox.getItems().add(majorSymptom);
            } else {
                diseaseBox.getItems().addAll(matches);
            }
            if (!diseaseBox.getItems().isEmpty()) diseaseBox.getSelectionModel().selectFirst();
            for (CheckBox cb : symptomChecks) {
                if (cb.getText().equalsIgnoreCase(majorSymptom)) {
                    cb.setSelected(true);
                    break;
                }
            }
        }
    }

    @FXML
    public void submitForm() {
        String name = nameField.getText() != null ? nameField.getText().trim() : "";
        String ageStr = ageField.getText() != null ? ageField.getText().trim() : "";
        String gender = genderBox.getValue();
        String location = locationBox.getValue();
        String chosenDisease = diseaseBox != null ? diseaseBox.getValue() : null;

        if (name.isEmpty() || ageStr.isEmpty() || gender == null || location == null) {
            if (validationLabel != null) validationLabel.setText("⚠ All fields are required.");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age < 0 || age > 120) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            if (validationLabel != null) validationLabel.setText("⚠ Enter a valid age.");
            return;
        }

        List<String> selectedMinorSymptoms = new ArrayList<>();
        for (CheckBox cb : symptomChecks) if (cb.isSelected()) selectedMinorSymptoms.add(cb.getText());

        if (chosenDisease == null || chosenDisease.isEmpty()) {
            if (validationLabel != null) validationLabel.setText("⚠ Select a disease.");
            return;
        }

        TreatmentContext.setAge(String.valueOf(age));
        TreatmentContext.setGender(gender);
        TreatmentContext.setLocation(location);
        TreatmentContext.setMinorSymptoms(selectedMinorSymptoms);
        TreatmentContext.setType("disease");
        TreatmentContext.setCondition(chosenDisease);

        long now = System.currentTimeMillis();
        String line = String.join("|", name, String.valueOf(age), gender, location, chosenDisease, String.valueOf(now));
        try (FileWriter writer = new FileWriter("data/reminders.txt", true)) {
            writer.write(line);
            writer.write("\n");
        } catch (Exception ignored) {}

        Main.changeScene("/view/TreatmentPage.fxml");
    }

    @FXML
    public void goBack() {
        Main.changeScene("/view/DiseaseHelp.fxml");
    }

    @FXML
    public void goToReminder() {
        Main.setPreviousScene("/view/DiseaseInput.fxml");
        Main.changeScene("/view/ReminderPage.fxml");
    }

    @FXML
    public void goToTreatment(MouseEvent event) {
        ImageView clicked = (ImageView) event.getSource();
        String condition = clicked.getId();
        TreatmentContext.setType("disease");
        TreatmentContext.setCondition(condition);
        Main.changeScene("/view/DiseaseInput.fxml");
    }
}
