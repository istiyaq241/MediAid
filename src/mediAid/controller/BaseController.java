package mediAid.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import mediAid.Main;

public class BaseController {

    @FXML
    private VBox fadeContainer;

    public void goHome() {
        Main.changeScene("/view/HomePage.fxml");
    }

    @FXML
    public void initialize() {
        if (fadeContainer != null) {
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.2), fadeContainer);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        }
    }
}