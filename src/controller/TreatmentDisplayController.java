package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Treatment;
import model.TreatmentLoader;

import java.util.Objects;

public class TreatmentDisplayController {

    @FXML private Label conditionLabel;
    @FXML private Label treatmentText;
    @FXML private ImageView conditionImage;

    private static Stage mainStage;
    private static String selectedCondition;

    public static void show(Stage stage, String condition) {
        try {
            mainStage = stage;
            selectedCondition = condition;

            FXMLLoader loader = new FXMLLoader(TreatmentDisplayController.class.getResource("/view/TreatmentDisplay.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("MediAid – Treatment");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        conditionLabel.setText(selectedCondition.substring(0, 1).toUpperCase() + selectedCondition.substring(1));
        Treatment treatment = TreatmentLoader.getTreatment(selectedCondition);

        if (treatment != null) {
            treatmentText.setText(treatment.getAdvice());
            String imagePath = "/assets/images/diseases/" + treatment.getImage();
            conditionImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
        } else {
            treatmentText.setText("No treatment information found.");
        }
    }

    @FXML
    public void goBack() {
        new DiseaseHandlerController().setStage(mainStage);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiseaseHandler.fxml"));
            Scene scene = new Scene(loader.load());
            DiseaseHandlerController controller = loader.getController();
            controller.setStage(mainStage);
            mainStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
