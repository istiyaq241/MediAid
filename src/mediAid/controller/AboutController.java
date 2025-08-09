package mediAid.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;

public class AboutController {

    @FXML private Text aboutText;
    @FXML private TextFlow aboutFlow;
    @FXML private VBox fadeContainer;

    @FXML
    public void initialize() {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.2), fadeContainer);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        try (BufferedReader reader = new BufferedReader(new FileReader("data/about.txt"))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n\n");
            }
            aboutText.setText(content.toString().trim());
        } catch (Exception e) {
            aboutText.setText("Unable to load About information.");
        }
    }

    public void goHome() {
        mediAid.Main.changeScene("/view/HomePage.fxml");
    }
}
