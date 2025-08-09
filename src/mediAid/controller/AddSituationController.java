package mediAid.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mediAid.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AddSituationController {
    @FXML private TextField nameField, dateField, locationField;
    @FXML private TextArea situationField;

    @FXML
    public void submitSituation() {
        String name = nameField.getText();
        String date = dateField.getText();
        String location = locationField.getText();
        String situation = situationField.getText();

        if (name.isEmpty() || date.isEmpty() || location.isEmpty() || situation.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill in all fields.");
            return;
        }

        try {
            Files.createDirectories(Paths.get("data"));
            FileWriter writer = new FileWriter("data/situations.txt", true);
            writer.write(name + " | " + date + " | " + location + " | " + situation + "\n");
            writer.close();

            nameField.clear(); dateField.clear(); locationField.clear(); situationField.clear();
            showAlert(Alert.AlertType.INFORMATION, "Situation submitted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to save situation.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Add Situation");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goBack() {
        Main.changeScene("/view/HomePage.fxml");
    }
}
