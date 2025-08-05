package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomePage {

    @FXML private Button accidentBtn, diseaseBtn, aboutBtn, creditsBtn, reminderBtn;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        accidentBtn.setOnAction(e -> {
            System.out.println("Accident page here.");
        });

        diseaseBtn.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiseaseHandler.fxml"));
                AnchorPane root = loader.load();
                DiseaseHandlerController controller = loader.getController();
                controller.setStage(stage);
                stage.setScene(new Scene(root));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        aboutBtn.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AboutPage.fxml"));
                AnchorPane root = loader.load();
                AboutPageController controller = loader.getController();
                controller.setStage(stage);
                stage.setScene(new Scene(root));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        creditsBtn.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreditsPage.fxml"));
                AnchorPane root = loader.load();
                CreditsPageController controller = loader.getController();
                controller.setStage(stage);
                stage.setScene(new Scene(root));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        reminderBtn.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Reminder.fxml"));
                AnchorPane root = loader.load();
                ReminderController controller = loader.getController();
                controller.setStage(stage);
                stage.setScene(new Scene(root));
                stage.setTitle("MediAid – Reminders");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
