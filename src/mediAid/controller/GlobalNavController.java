package mediAid.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import mediAid.Main;

public class GlobalNavController {
    private Stage stageFrom(ActionEvent e) {
        return (Stage) ((Node) e.getSource()).getScene().getWindow();
    }

    @FXML public void toggleMenu(ActionEvent e) { stageFrom(e).close(); }
    @FXML public void goBack(ActionEvent e) { Main.changeScene("/view/HomePage.fxml"); stageFrom(e).close(); }
    @FXML public void goToReminder(ActionEvent e) { Main.changeScene("/view/ReminderPage.fxml"); stageFrom(e).close(); }
    @FXML public void goToAddSituation(ActionEvent e) { Main.changeScene("/view/AddSituationForm.fxml"); stageFrom(e).close(); }
    @FXML public void goToVolunteer(ActionEvent e) { Main.changeScene("/view/VolunteerForm.fxml"); stageFrom(e).close(); }
    @FXML public void goToSeekHelp(ActionEvent e) { Main.changeScene("/view/SeekHelpPage.fxml"); stageFrom(e).close(); }
    @FXML public void goToAbout(ActionEvent e) { Main.changeScene("/view/About.fxml"); stageFrom(e).close(); }
    @FXML public void goToCredits(ActionEvent e) { Main.changeScene("/view/Credits.fxml"); stageFrom(e).close(); }
    @FXML public void goToHome(ActionEvent e) { Main.changeScene("/view/HomePage.fxml"); stageFrom(e).close(); }
}
