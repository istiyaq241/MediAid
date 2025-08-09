package mediAid.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import mediAid.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VolunteerFormController {
    @FXML private TextField nameField, ageField, locationField, professionField;
    @FXML private ComboBox<String> genderBox, bloodGroupBox;

    @FXML
    public void initialize() {
        genderBox.getItems().addAll("Male", "Female", "Other");
        bloodGroupBox.getItems().addAll("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");
    }

    @FXML
    public void submitVolunteer() {
        String name = nameField.getText();
        String age = ageField.getText();
        String gender = genderBox.getValue();
        String bloodGroup = bloodGroupBox.getValue();
        String profession = professionField.getText();
        String location = locationField.getText();

        if (name.isEmpty() || age.isEmpty() || gender == null || bloodGroup == null || profession.isEmpty() || location.isEmpty()) {
            showAlert(AlertType.ERROR, "Please fill in all fields.");
            return;
        }

        try {
            Files.createDirectories(Paths.get("data"));
            FileWriter writer = new FileWriter("data/volunteers.txt", true);
            writer.write(name + " | " + age + " | " + gender + " | " + bloodGroup + " | " + profession + " | " + location + "\n");
            writer.close();

            nameField.clear(); ageField.clear(); locationField.clear(); professionField.clear();
            genderBox.setValue(null); bloodGroupBox.setValue(null);

            showAlert(AlertType.INFORMATION, "Volunteer submitted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Failed to save volunteer information.");
        }
    }

    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Volunteer Form");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goBack() {
        Main.changeScene("/view/HomePage.fxml");
    }
}
