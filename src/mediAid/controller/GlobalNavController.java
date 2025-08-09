package mediAid.controller;

import javafx.fxml.FXML;
import mediAid.Main;

public class GlobalNavController {

    @FXML
    public void goBack() {
        // Return to whatever page set as previous via Main.setPreviousScene(...)
        Main.changeScene(Main.getPreviousScene());
    }
}
