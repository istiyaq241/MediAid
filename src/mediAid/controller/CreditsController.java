package mediAid.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import mediAid.Main;

import java.io.BufferedReader;
import java.io.FileReader;

public class CreditsController {

    @FXML
    private VBox fadeContainer;

    @FXML
    private TextArea creditsText;

    @FXML
    public void initialize() {
        applyFade();
        loadCreditsFromFile();
    }

    private void applyFade() {
        if (fadeContainer != null) {
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.2), fadeContainer);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        }
    }

    private void loadCreditsFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/credits.txt"));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            reader.close();
            creditsText.setText(builder.toString());
        } catch (Exception e) {
            creditsText.setText("Unable to load credits.");
        }
    }

    @FXML
    public void goHome() {
        Main.changeScene("/view/HomePage.fxml");
    }
}
