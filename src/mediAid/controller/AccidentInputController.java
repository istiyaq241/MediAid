package mediAid.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import mediAid.Main;
import mediAid.TreatmentLoader;
import mediAid.model.TreatmentContext;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class AccidentInputController {

    @FXML private TextField nameField, ageField;
    @FXML private ComboBox<String> genderBox, locationBox;
    @FXML private ComboBox<String> detailBox;
    @FXML private Label validationLabel;

    @FXML
    public void initialize() {
        if (genderBox != null) genderBox.getItems().addAll("Male", "Female", "Other");
        if (locationBox != null) locationBox.getItems().addAll("Rural", "Urban", "Coastal", "Forest", "Disaster Shelter");

        String acc = TreatmentContext.getCondition();
        if (detailBox != null && acc != null) {
            if (acc.equalsIgnoreCase("burn")) {
                detailBox.setItems(FXCollections.observableArrayList("1st degree", "2nd degree", "3rd degree"));
                detailBox.getSelectionModel().selectFirst();
            } else if (acc.equalsIgnoreCase("bleeding")) {
                detailBox.setItems(FXCollections.observableArrayList("mild", "moderate", "severe"));
                detailBox.getSelectionModel().selectFirst();
            } else if (acc.equalsIgnoreCase("snakebite")) {
                detailBox.setItems(FXCollections.observableArrayList("venomous", "non-venomous", "unknown"));
                detailBox.getSelectionModel().selectFirst();
            } else {
                detailBox.setDisable(true);
                detailBox.setVisible(false);
            }
        }
    }

    @FXML
    public void submit() {
        String name = nameField.getText() != null ? nameField.getText().trim() : "";
        String ageStr = ageField.getText() != null ? ageField.getText().trim() : "";
        String gender = genderBox.getValue();
        String location = locationBox.getValue();

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

        String acc = TreatmentContext.getCondition();
        String finalCondition = acc;
        if (detailBox != null && detailBox.isVisible() && detailBox.getValue() != null) {
            String d = detailBox.getValue();
            if (acc.equalsIgnoreCase("burn")) finalCondition = "burn - " + d;
            else if (acc.equalsIgnoreCase("bleeding")) finalCondition = "bleeding - " + d;
            else if (acc.equalsIgnoreCase("snakebite")) finalCondition = "snakebite - " + d;
        }

        TreatmentContext.setType("accident");
        TreatmentContext.setAge(String.valueOf(age));
        TreatmentContext.setGender(gender);
        TreatmentContext.setLocation(location);
        TreatmentContext.setMinorSymptoms(null);
        TreatmentContext.setCondition(finalCondition);

        long now = System.currentTimeMillis();
        String line = String.join("|", name, String.valueOf(age), gender, location, finalCondition, String.valueOf(now));
        try (FileWriter writer = new FileWriter("data/reminders.txt", true)) {
            writer.write(line);
            writer.write("\n");
        } catch (Exception ignored) {}

        Main.changeScene("/view/TreatmentPage.fxml");
    }

    @FXML
    public void goBack() {
        Main.changeScene("/view/AccidentHelp.fxml");
    }
}
