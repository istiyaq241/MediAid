package mediAid.controller;

import javafx.fxml.FXML;
import mediAid.Main;

public class HomePageController {

    @FXML
    public void goToAccident() {
        Main.changeScene("/view/AccidentHelp.fxml");
    }

    @FXML
    public void goToDisease() {
        Main.changeScene("/view/DiseaseHelp.fxml");
    }

    @FXML
    public void goToReminder() {
        Main.changeScene("/view/ReminderPage.fxml");
    }
}
