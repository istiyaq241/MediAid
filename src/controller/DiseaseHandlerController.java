package controller;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class DiseaseHandlerController {

    @FXML private GridPane gridPane;
    @FXML private ImageView fever, cold, cough, headache, stomachache, nausea, vomiting, bodyache, diarrhoea;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        setImage(fever, "fever.jpg");
        setImage(cold, "cold.jpg");
        setImage(cough, "cough.jpg");
        setImage(headache, "headache.jpg");
        setImage(stomachache, "stomachache.jpg");
        setImage(nausea, "nausea.jpg");
        setImage(vomiting, "vomiting.jpg");
        setImage(bodyache, "bodyache.jpg");
        setImage(diarrhoea, "diarrhoea.jpg");

        ImageView[] all = {fever, cold, cough, headache, stomachache, nausea, vomiting, bodyache, diarrhoea};
        for (int i = 0; i < all.length; i++) {
            ImageView img = all[i];
            img.setTranslateY(400);
            int index = i;
            TranslateTransition tt = new TranslateTransition(Duration.seconds(0.6 + (index * 0.05)), img);
            tt.setToY(0);
            tt.setDelay(Duration.seconds(index * 0.1));
            tt.play();

            String condition = img.getId();
            img.setOnMouseClicked(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DiseaseInput.fxml"));
                    AnchorPane root = loader.load();
                    DiseaseInputController controller = loader.getController();
                    controller.setStage(stage);
                    controller.setInitialSymptom(condition);
                    stage.setScene(new Scene(root));
                    stage.setTitle("MediAid – Enter Info");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    private void setImage(ImageView view, String filename) {
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/images/diseases/" + filename)));
        view.setImage(img);
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
