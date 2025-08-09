package mediAid.controller;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import mediAid.Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class WelcomeController {

    @FXML private Label tipLabel;
    @FXML private Rectangle progressLight;

    @FXML
    public void initialize() {
        loadTip();
        animateBar();
        animateAndGo();
    }

    private void loadTip() {
        ArrayList<String> tips = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/tips.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    tips.add(line.trim());
                }
            }
        } catch (Exception e) {
            // Optional: Log the error or ignore
        }

        if (!tips.isEmpty()) {
            Random rand = new Random();
            tipLabel.setText("Tip: " + tips.get(rand.nextInt(tips.size())));
        } else {
            tipLabel.setText("Tip: Stay hydrated and safe!");
        }
    }

    private void animateBar() {
        TranslateTransition move = new TranslateTransition(Duration.seconds(2.5), progressLight);
        move.setFromX(-125);
        move.setToX(125);
        move.setCycleCount(1);
        move.setAutoReverse(true);
        move.play();
    }

    private void animateAndGo() {
        PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
        delay.setOnFinished(e -> Main.changeScene("/view/HomePage.fxml"));
        delay.play();
    }
}
