package controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DisclaimerController {

    @FXML private Label disclaimerLabel;
    @FXML private Button accidentBtn, diseaseBtn, aboutBtn, creditsBtn;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        if (disclaimerLabel != null) {
            TranslateTransition move = new TranslateTransition(Duration.seconds(12), disclaimerLabel);
            move.setFromX(800);
            move.setToX(-800);
            move.setCycleCount(TranslateTransition.INDEFINITE);
            move.setInterpolator(javafx.animation.Interpolator.LINEAR);
            move.play();
        }

        accidentBtn.setOnAction(e -> loadAccidentScreen());
        diseaseBtn.setOnAction(e -> loadDiseaseScreen());
        aboutBtn.setOnAction(e -> loadAbout());
        creditsBtn.setOnAction(e -> loadCredits());
    }

    private void loadDiseaseScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiseaseHandler.fxml"));
            AnchorPane root = loader.load();
            DiseaseHandlerController controller = loader.getController();
            controller.setStage(stage);
            stage.setScene(new Scene(root));
            stage.setTitle("MediAid – Disease Help");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAccidentScreen() {
        System.out.println("🚑 Accident screen will go here.");
    }

    private void loadAbout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AboutPage.fxml"));
            AnchorPane root = loader.load();
            AboutPageController controller = loader.getController();
            controller.setStage(stage);
            stage.setScene(new Scene(root));
            stage.setTitle("MediAid – About");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCredits() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreditsPage.fxml"));
            AnchorPane root = loader.load();
            CreditsPageController controller = loader.getController();
            controller.setStage(stage);
            stage.setScene(new Scene(root));
            stage.setTitle("MediAid – Credits");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
