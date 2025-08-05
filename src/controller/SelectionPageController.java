package controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.AnchorPane;

public class SelectionPageController {

    @FXML
    private Button accidentBtn, diseaseBtn;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        // Initial positions (off-screen right)
        accidentBtn.setTranslateX(800);
        diseaseBtn.setTranslateX(800);

        // Animate in from right
        animateButton(accidentBtn, 0.3);
        animateButton(diseaseBtn, 0.6);

        accidentBtn.setOnAction(e -> {
            System.out.println("🚑 Accident screen will open...");
            // Hook up to AccidentHandler.fxml later
        });

        diseaseBtn.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiseaseHandler.fxml"));
                AnchorPane root = loader.load();
                controller.DiseaseHandlerController controller = loader.getController();
                controller.setStage(stage);
                stage.setScene(new Scene(root));
                stage.setTitle("MediAid – Disease Help");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void animateButton(Button btn, double delaySec) {
        TranslateTransition tt = new TranslateTransition(Duration.seconds(1), btn);
        tt.setToX(0);
        tt.setDelay(Duration.seconds(delaySec));
        tt.play();
    }
}
