package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreditsPageController {

    @FXML private Label member1, member2, member3, member4, member5;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        member1.setText("Member 1: ");
        member2.setText("Member 2: ");
        member3.setText("Member 3: ");
        member4.setText("Member 4: ");
        member5.setText("Member 5: ");
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
