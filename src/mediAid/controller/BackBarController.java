package mediAid.controller;

import javafx.fxml.FXML;
import mediAid.Main;

public class BackBarController {
    @FXML public void goBack() { Main.changeScene("/view/HomePage.fxml"); }
}
