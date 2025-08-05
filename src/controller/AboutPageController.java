package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AboutPageController {

    @FXML private TextArea aboutText;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        aboutText.setText("Write about MediAid here...");
    }

    @FXML
    public void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Disclaimer.fxml"));
            AnchorPane root = loader.load();
            DisclaimerController controller = loader.getController();
            controller.setStage(stage);
            stage.setScene(new Scene(root));
            stage.setTitle("MediAid – Disclaimer");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
